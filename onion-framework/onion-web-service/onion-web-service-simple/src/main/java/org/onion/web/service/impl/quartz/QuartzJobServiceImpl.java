/*
 * Copyright 2015-2016 http://onion.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onion.web.service.impl.quartz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.onion.commons.StringUtils;
import org.onion.web.bean.common.DeleteParam;
import org.onion.web.bean.common.UpdateMapParam;
import org.onion.web.bean.common.UpdateParam;
import org.onion.web.bean.po.quartz.QuartzJob;
import org.onion.web.core.exception.BusinessException;
import org.onion.web.dao.quartz.QuartzJobHistoryMapper;
import org.onion.web.dao.quartz.QuartzJobMapper;
import org.onion.web.service.impl.AbstractServiceImpl;
import org.onion.web.service.quartz.QuartzJobHistoryService;
import org.onion.web.service.quartz.QuartzJobService;
import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.Calendar;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.spi.MutableTrigger;
import org.quartz.spi.OperableTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时调度任务服务类
 * Created by generator
 */
@Service("quartzJobService")
public class QuartzJobServiceImpl extends AbstractServiceImpl<QuartzJob, String> implements QuartzJobService {

    private static final String CACHE_KEY = "quartz-job";

    @Resource
    protected QuartzJobMapper quartzJobMapper;

    @Autowired
    protected Scheduler scheduler;

    @Resource
    protected QuartzJobHistoryService quartzJobHistoryService;

    @Resource
    protected QuartzJobHistoryMapper quartzJobHistoryMapper;

    @Override
    protected QuartzJobMapper getMapper() {
        return this.quartzJobMapper;
    }

    @Override
    @Cacheable(value = CACHE_KEY, key = "'id:'+#id")
    public QuartzJob selectByPk(String id) {
        return super.selectByPk(id);
    }

    @Override
    public String insert(QuartzJob data) {
        data.setEnabled(true);
        String id = super.insert(data);
        startJob(data);
        return id;
    }

    @Override
    @CacheEvict(value = CACHE_KEY, key = "'id:'+#data.id")
    public int update(QuartzJob data) {
        QuartzJob old = selectByPk(data.getId());
        assertNotNull(old, "任务不存在");
        int i = getMapper().update(UpdateParam.build(data)
                .excludes("status", "enabled")
                .where("id", data.getId()));
        if (old.isEnabled()) {
            deleteJob(data.getId());
            startJob(data);
        }
        return i;
    }

    @Override
    public int saveOrUpdate(QuartzJob job) {
        throw new UnsupportedOperationException();
    }

    @Override
    @CacheEvict(value = CACHE_KEY, key = "'id:'+#id")
    public void enable(String id) {
        getMapper().update((UpdateParam) UpdateMapParam.build().set("enabled", true).where("id", id));
        startJob(getMapper().selectByPk(id));
    }

    @Override
    @CacheEvict(value = CACHE_KEY, key = "'id:'+#id")
    public void disable(String id) {
        getMapper().update((UpdateParam) UpdateMapParam.build().set("enabled", false).where("id", id));
        deleteJob(id);
    }

    @Override
    @CacheEvict(value = CACHE_KEY, key = "'id:'+#id")
    public int delete(String id) {
        deleteJob(id);
        quartzJobHistoryMapper.delete(DeleteParam.build().where("jobId", id));
        return super.delete(id);
    }

    void deleteJob(String id) {
        JobKey jobKey = createJobKey(id);
        try {
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            throw new BusinessException("更新任务失败", e, 500);
        }
    }


    @Override
    public List<Date> getExecTimes(String cron, int number) {
        try {
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            cronTriggerImpl.setCronExpression(cron);
            return computeFireTimesBetween(cronTriggerImpl, null, new Date(), new DateTime().plusYears(5).toDate(), number);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e, 500);
        }
    }

    public static List<Date> computeFireTimesBetween(OperableTrigger trigger,
                                                     org.quartz.Calendar cal, Date from, Date to, int num) {
        LinkedList<Date> lst = new LinkedList<>();
        OperableTrigger t = (OperableTrigger) trigger.clone();
        if (t.getNextFireTime() == null) {
            t.setStartTime(from);
            t.setEndTime(to);
            t.computeFirstFireTime(cal);
        }
        for (int i = 0; i < num; i++) {
            Date d = t.getNextFireTime();
            if (d != null) {
                if (d.before(from)) {
                    t.triggered(cal);
                    continue;
                }
                if (d.after(to)) {
                    break;
                }
                lst.add(d);
                t.triggered(cal);
            } else {
                break;
            }
        }
        return java.util.Collections.unmodifiableList(lst);
    }

    void startJob(QuartzJob job) {
        assertNotNull(job, "任务不存在");
        JobKey key = createJobKey(job.getId());
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity(key)
                .setJobData(createJobDataMap(job.getParameters()))
                .usingJobData(SimpleJobFactory.QUARTZ_ID_KEY, job.getId())
                .withDescription(job.getName() + (job.getRemark() == null ? "" : job.getRemark()))
                .build();
        MutableTrigger trigger = CronScheduleBuilder.cronSchedule(job.getCron()).build();
        trigger.setKey(createTriggerKey(job.getId()));
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new BusinessException("创建定时任务失败!", e, 500);
        }
    }

    JobDataMap createJobDataMap(String parameters) {
        JobDataMap map = new JobDataMap();
        if (!StringUtils.isNullOrEmpty(parameters)) {
            JSONArray jsonArray = JSON.parseArray(parameters);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject o = jsonArray.getJSONObject(i);
                map.put(o.getString("key"), o.get("value"));
            }
        }
        return map;
    }

    JobKey createJobKey(String jobId) {
        return new JobKey(jobId, "onion.scheduler");
    }

    TriggerKey createTriggerKey(String jobId) {
        return new TriggerKey(jobId, "onion.scheduler");
    }


}
