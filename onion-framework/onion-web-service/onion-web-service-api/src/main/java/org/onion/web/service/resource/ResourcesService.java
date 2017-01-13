package org.onion.web.service.resource;

import org.onion.web.bean.po.resource.Resources;
import org.onion.web.service.GenericService;

/**
 * 资源服务类
 * Created by generator
 */
public interface ResourcesService extends GenericService<Resources, String> {
    /**
     * 根据资源md5 查询资源信息,如果没有资源则返回null
     *
     * @param md5 md5值
     * @return 资源对象
     */
    Resources selectByMd5(String md5);

}
