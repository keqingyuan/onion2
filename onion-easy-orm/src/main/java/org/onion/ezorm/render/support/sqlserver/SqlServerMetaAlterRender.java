package org.onion.ezorm.render.support.sqlserver;

import org.onion.ezorm.executor.SQL;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.render.SqlRender;

/**
 * sqlServer 表结构修改sql渲染器,用于渲染sqlServer修改表的sql
 */
public class SqlServerMetaAlterRender implements SqlRender<Boolean> {

    @Override
    public SQL render(TableMetaData metaData, Boolean executeRemove) {
        // TODO: 16-9-29
        throw new UnsupportedOperationException();
    }
}
