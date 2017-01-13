package org.onion.ezorm.render.support.simple;

import org.onion.ezorm.executor.SQL;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.param.SqlParam;
import org.onion.ezorm.param.Term;
import org.onion.ezorm.render.Dialect;
import org.onion.ezorm.render.SqlAppender;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhouhao on 16-6-4.
 */
public class SimpleDeleteSqlRender extends CommonSqlRender<SqlParam> {
    public SimpleDeleteSqlRender(Dialect dialect) {
        setDialect(dialect);
    }

    class SimpleDeleteSqlRenderProcess extends SimpleWhereSqlBuilder {
        private TableMetaData metaData;
        private SqlParam param;
        private SqlAppender whereSql = new SqlAppender();

        public SimpleDeleteSqlRenderProcess(TableMetaData metaData, SqlParam param) {
            this.metaData = metaData;
            this.param = param;
            List<Term> terms = param.getTerms();
            terms = terms.stream().filter(term -> !term.getField().contains(".")).collect(Collectors.toList());
            param.setTerms(terms);
            //解析查询条件
            buildWhere(metaData, "", terms, whereSql, new HashSet<>());
            if (!whereSql.isEmpty()) whereSql.removeFirst();
        }

        public SQL process() {
            SqlAppender appender = new SqlAppender();
            appender.add("DELETE FROM ", metaData.getName()," ",metaData.getAlias());
            if (whereSql.isEmpty()) {
                throw new UnsupportedOperationException("禁止执行未设置任何条件的删除操作!");
            }
            appender.add(" WHERE", " ").addAll(whereSql);
            String sql = appender.toString();
            SimpleSQL simpleSQL = new SimpleSQL( sql, param);
            return simpleSQL;
        }

        @Override
        public Dialect getDialect() {
            return dialect;
        }
    }

    @Override
    public SQL render(TableMetaData metaData, SqlParam param) {
        return new SimpleDeleteSqlRenderProcess(metaData, param).process();
    }

    private Dialect dialect;

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
