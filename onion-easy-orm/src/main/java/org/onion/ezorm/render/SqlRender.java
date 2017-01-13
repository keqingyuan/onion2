package org.onion.ezorm.render;

import org.onion.ezorm.executor.SQL;
import org.onion.ezorm.meta.TableMetaData;

/**
 * SQL渲染器
 *
 * @param <R> 参数泛型
 */
public interface SqlRender<R> {

    /**
     * 根据表结构对象渲染SQL
     *
     * @param metaData 表结构对象 {@link TableMetaData}
     * @param param    渲染参数
     * @return 渲染结果
     * @see {@link org.onion.ezorm.render.support.simple.SimpleSQL}
     */
    SQL render(TableMetaData metaData, R param);

    enum TYPE {
        INSERT, DELETE, UPDATE, SELECT, SELECT_TOTAL, META_ALTER, META_CREATE, META_DROP
    }
}
