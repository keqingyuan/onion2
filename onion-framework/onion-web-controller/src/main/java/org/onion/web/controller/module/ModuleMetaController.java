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

package org.onion.web.controller.module;

import org.onion.web.bean.po.module.ModuleMeta;
import org.onion.web.bean.po.role.UserRole;
import org.onion.web.bean.po.user.User;
import org.onion.web.controller.GenericController;
import org.onion.web.core.authorize.annotation.Authorize;
import org.onion.web.core.message.ResponseMessage;
import org.onion.web.core.utils.WebUtil;
import org.onion.web.service.module.ModuleMetaService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 模板配置定义控制器，继承自{@link GenericController<ModuleMeta, String>}
 *
 * @author zhouhao
 * @since 1.0
 */
@RestController
@RequestMapping("/module-meta")
@Authorize(module = "module-meta")
public class ModuleMetaController extends GenericController<ModuleMeta, String> {
    @Resource
    private ModuleMetaService moduleMetaService;

    @Override
    protected ModuleMetaService getService() {
        return moduleMetaService;
    }

    /**
     * 查询当前用户持有制定key的所有模块配置定义信息
     *
     * @param key
     * @return {@link ResponseMessage}
     */
    @RequestMapping(value = "/{key}/own", method = RequestMethod.GET)
    public ResponseMessage userModuleMeta(@PathVariable String key) {
        User user = WebUtil.getLoginUser();
        List<UserRole> roles = user.getUserRoles();
        if (roles == null) roles = new ArrayList<>();
        String[] roleIdList = roles
                .stream()
                .map(userRole -> userRole.getRoleId())
                .collect(Collectors.toList()).toArray(new String[roles.size()]);
        return ResponseMessage.ok(getService().selectByKeyAndRoleId(key, roleIdList));
    }
}
