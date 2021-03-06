package org.onion.web.core.logger;

import com.alibaba.fastjson.JSON;
import org.onion.web.bean.po.logger.LoggerInfo;
import org.onion.web.core.message.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Slf4jAccessLoggerPersisting implements AccessLoggerPersisting {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired(required = false)
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    @Override
    public void save(LoggerInfo loggerInfo) {
        if (fastJsonHttpMessageConverter == null)
            logger.info(JSON.toJSONString(loggerInfo));
        else
            logger.info(fastJsonHttpMessageConverter.converter(loggerInfo));
    }
}
