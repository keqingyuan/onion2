package org.onion.ezorm.render.dialect;

import org.onion.ezorm.render.Dialect;
import org.onion.ezorm.render.SqlRender;
import org.onion.ezorm.render.support.oracle.OracleMetaAlterRender;
import org.onion.ezorm.render.support.oracle.OracleMetaCreateRender;

public class OracleDatabaseMeta extends AbstractDatabaseMeta {
    private static final String DEFAULT_NAME = "oracle";

    private String name;


    public OracleDatabaseMeta() {
        name = DEFAULT_NAME;
    }

    @Override
    public void init() {
        super.init();
        renderMap.put(SqlRender.TYPE.META_CREATE, new OracleMetaCreateRender());
        renderMap.put(SqlRender.TYPE.META_ALTER, new OracleMetaAlterRender(this));
    }

    public OracleDatabaseMeta(String name) {
        this.name = name;
    }

    @Override
    public Dialect getDialect() {
        return Dialect.ORACLE;
    }

    @Override
    public String getName() {
        return name;
    }
}
