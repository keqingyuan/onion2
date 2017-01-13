package org.onion.web.dao.role;

import org.onion.web.dao.GenericMapper;
import org.onion.web.bean.po.role.RoleModule;

import java.util.List;

/**
 * 系统模块角色绑定数据映射接口
 * Created by generator
 */
public interface RoleModuleMapper extends GenericMapper<RoleModule, String> {
    /**
     * 根据角色id查询
     *
     * @param roleId 角色id
     * @return
     */
    List<RoleModule> selectByRoleId(String roleId) ;

    int deleteByRoleId(String roleId) ;

    int deleteByModuleId(String moduleId) ;
}
