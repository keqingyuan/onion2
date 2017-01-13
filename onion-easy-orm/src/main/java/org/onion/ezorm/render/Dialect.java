package org.onion.ezorm.render;

import org.onion.ezorm.meta.FieldMetaData;
import org.onion.ezorm.param.Term;
import org.onion.ezorm.render.dialect.DefaultDialect;

/**
 * Created by zhouhao on 16-5-17.
 */
public interface Dialect {
    interface Mapper {
        String accept(String wherePrefix, Term term, FieldMetaData fieldMetaData, String tableAlias);
    }

    void setTermTypeMapper(String termType, Mapper mapper);

    Dialect MSSQL = new DefaultDialect() {
        @Override
        public String getQuoteStart() {
            return "[";
        }

        @Override
        public String getQuoteEnd() {
            return "]";
        }

        @Override
        public String doPaging(String sql, int pageIndex, int pageSize) {
            // TODO: 16-9-29
            throw new UnsupportedOperationException();
        }
    };

    Dialect ORACLE = new DefaultDialect() {
        @Override
        public String getQuoteStart() {
            return "\"";
        }

        @Override
        public String getQuoteEnd() {
            return "\"";
        }

        @Override
        public String doPaging(String sql, int pageIndex, int pageSize) {
            return new StringBuilder()
                    .append("SELECT * FROM ( SELECT row_.*, rownum rownum_ FROM (")
                    .append(sql)
                    .append(") row_ )")
                    .append("WHERE rownum_ <= ")
                    .append(pageSize * (pageIndex + 1))
                    .append(" AND rownum_ > ")
                    .append(pageSize * pageIndex).toString();
        }
    };

    Dialect H2 = ORACLE;

    Dialect MYSQL = new DefaultDialect() {
        @Override
        public String getQuoteStart() {
            return "`";
        }

        @Override
        public String getQuoteEnd() {
            return "`";
        }

        @Override
        public String doPaging(String sql, int pageIndex, int pageSize) {
            return new StringBuilder(sql)
                    .append(" LIMIT ")
                    .append(pageSize * pageIndex)
                    .append(",")
                    .append(pageSize * (pageIndex + 1)).toString();
        }

    };

    String getQuoteStart();

    String getQuoteEnd();

    String wrapperWhere(String wherePrefix, Term term, FieldMetaData fieldMetaData, String tableAlias);

    String doPaging(String sql, int pageIndex, int pageSize);
}
