package org.onion.web.controller.role;

import org.onion.web.core.logger.annotation.AccessLogger;
import org.onion.web.core.authorize.annotation.Authorize;
import org.onion.web.bean.common.QueryParam;
import org.onion.web.bean.po.role.Role;
import org.onion.web.controller.GenericController;
import org.onion.web.core.message.ResponseMessage;
import org.springframework.web.bind.annotation.RestController;
import org.onion.web.service.role.RoleService;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 后台管理角色控制器，继承自GenericController,使用rest+json
 * Created by generator 2015-8-26 10:57:38
 */
@RestController
@RequestMapping(value = "/role")
@AccessLogger("角色管理")
@Authorize(module = "role")
public class RoleController extends GenericController<Role, String> {

    //默认服务类
    @Resource
    private RoleService roleService;

    @Override
    public RoleService getService() {
        return this.roleService;
    }

    @Override
    public ResponseMessage list(QueryParam param) {
        return super.list(param).exclude(Role.class, "modules");
    }
}
