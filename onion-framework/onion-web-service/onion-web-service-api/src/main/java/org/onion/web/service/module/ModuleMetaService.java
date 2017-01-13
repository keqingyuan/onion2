package org.onion.web.service.module;

import org.onion.web.bean.common.QueryParam;
import org.onion.web.bean.po.module.ModuleMeta;
import org.onion.web.service.GenericService;

import java.util.List;

/**
 * Created by zhouhao on 16-5-10.
 */
public interface ModuleMetaService extends GenericService<ModuleMeta, String> {

    default List<ModuleMeta> selectByKeyAndRoleId(String key, String... roleId) {
        QueryParam queryParam = new QueryParam();
        queryParam.where("key", key).and("role_id$IN", roleId);
        return this.select(queryParam);
    }

    default List<ModuleMeta> selectByKey(String key) {
        QueryParam queryParam = new QueryParam();
        queryParam.where("key", key);
        return this.select(queryParam);
    }

}
