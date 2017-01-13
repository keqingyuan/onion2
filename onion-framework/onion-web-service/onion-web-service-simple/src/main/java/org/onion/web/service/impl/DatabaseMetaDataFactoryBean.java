package org.onion.web.service.impl;

import org.onion.ezorm.meta.DatabaseMetaData;
import org.onion.ezorm.render.dialect.H2DatabaseMeta;
import org.onion.ezorm.render.dialect.MysqlDatabaseMeta;
import org.onion.ezorm.render.dialect.OracleDatabaseMeta;
import org.onion.web.core.Install;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by zhouhao on 16-7-1.
 */
@Component
public class DatabaseMetaDataFactoryBean implements FactoryBean<DatabaseMetaData> {

    @Override
    public DatabaseMetaData getObject() throws Exception {
        DatabaseMetaData databaseMetaData = null;
        switch (Install.getDatabaseType()) {
            case "mysql":
                databaseMetaData = new MysqlDatabaseMeta();
                break;
            case "oracle":
                databaseMetaData = new OracleDatabaseMeta();
                break;
            case "h2":
                databaseMetaData = new H2DatabaseMeta();
                break;
        }
        if (databaseMetaData == null)
            throw new UnsupportedOperationException();
        databaseMetaData.init();
        return databaseMetaData;
    }

    @Override
    public Class<?> getObjectType() {
        return DatabaseMetaData.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
