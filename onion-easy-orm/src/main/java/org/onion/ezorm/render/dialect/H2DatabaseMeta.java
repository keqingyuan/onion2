package org.onion.ezorm.render.dialect;

import org.onion.ezorm.render.Dialect;
import org.onion.ezorm.render.SqlRender;
import org.onion.ezorm.render.support.h2.H2MetaAlterRender;
import org.onion.ezorm.render.support.oracle.OracleMetaAlterRender;
import org.onion.ezorm.render.support.oracle.OracleMetaCreateRender;

public class H2DatabaseMeta extends AbstractDatabaseMeta {
    private static final String DEFAULT_NAME = "h2";

    private String name;

    public H2DatabaseMeta() {
        name = DEFAULT_NAME;
    }

    @Override
    public void init() {
        super.init();
        renderMap.put(SqlRender.TYPE.META_CREATE, new OracleMetaCreateRender());
        renderMap.put(SqlRender.TYPE.META_ALTER, new H2MetaAlterRender(this));
    }

    public H2DatabaseMeta(String name) {
        this.name = name;
    }

    @Override
    public Dialect getDialect() {
        return Dialect.H2;
    }

    @Override
    public String getName() {
        return name;
    }
}
