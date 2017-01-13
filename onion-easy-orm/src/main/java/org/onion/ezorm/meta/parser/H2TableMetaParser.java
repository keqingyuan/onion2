package org.onion.ezorm.meta.parser;

import org.onion.ezorm.executor.SqlExecutor;
import org.onion.ezorm.meta.FieldMetaData;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.meta.converter.ClobValueConverter;
import org.onion.ezorm.meta.converter.DateTimeConverter;
import org.onion.ezorm.meta.expand.ObjectWrapper;
import org.onion.ezorm.meta.expand.SimpleMapWrapper;
import org.onion.ezorm.render.support.simple.SimpleSQL;

import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by zhouhao on 16-6-5.
 */
public class H2TableMetaParser implements TableMetaParser {
    private SqlExecutor sqlExecutor;

    public H2TableMetaParser(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public TableMetaData parse(String name) {
        TableMetaData metaData = new TableMetaData();
        metaData.setName(name);
        metaData.setAlias(name);
        metaData.setComment("");
        String filedMetaSqlStr = "SELECT column_name AS \"name\",\n" +
                "type_name AS \"data_type\",\n" +
                "character_maximum_length as \"data_length\",\n" +
                "numeric_precision as \"data_precision\",\n" +
                "numeric_scale as \"data_scale\",\n" +
                "is_nullable as \"not-null\",\n" +
                "remarks as \"comment\"\n" +
                "FROM INFORMATION_SCHEMA.columns\n" +
                "WHERE TABLE_NAME = #{tableName}";
        String findTableCommentSqlStr = "SELECT remarks as \"comment\" FROM INFORMATION_SCHEMA.tables WHERE table_type='TABLE' and table_name=#{tableName}";
        Map<String, Object> param = new HashMap<>();
        param.put("tableName", metaData.getName().toUpperCase());
        SimpleSQL filedMetaSql = new SimpleSQL(filedMetaSqlStr, param);
        try {
            sqlExecutor.single(new SimpleSQL(findTableCommentSqlStr, param), new SimpleMapWrapper() {
                @Override
                public void done(Map<String, Object> instance) {
                    metaData.setComment((String) instance.get("comment"));
                }
            });
            List<FieldMetaData> fieldMetaData = sqlExecutor.list(filedMetaSql, new FieldMetaDataWrapper());
            if (fieldMetaData.isEmpty()) return null;
            fieldMetaData.forEach(meta -> metaData.addField(meta));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return metaData;
    }

    @Override
    public List<TableMetaData> parseAll() throws SQLException {
        String sql = "select table_name as \"name\" FROM INFORMATION_SCHEMA.tables where table_type='TABLE'";
        List<TableMetaData> metaDatas = new LinkedList<>();
        sqlExecutor.list(new SimpleSQL(sql), new SimpleMapWrapper() {
            @Override
            public void done(Map<String, Object> instance) {
                String name = (String) instance.get("name");
                TableMetaData metaData = parse(name);
                if (metaData != null)
                    metaDatas.add(metaData);
                super.done(instance);
            }
        });
        return metaDatas;
    }

    class FieldMetaDataWrapper implements ObjectWrapper<FieldMetaData> {

        @Override
        public FieldMetaData newInstance() {
            return new FieldMetaData();
        }

        @Override
        public void wrapper(FieldMetaData instance, int index, String attr, Object value) {
            if (attr.equalsIgnoreCase("name")) {
                instance.setName(String.valueOf(value).toLowerCase());
                instance.setProperty("old-name", instance.getName());
            } else if (attr.equalsIgnoreCase("comment")) {
                instance.setComment(String.valueOf(value));

            } else {
                if (attr.toLowerCase().equals("not-null")) {
                    value = !"yes".equals(String.valueOf(value).toLowerCase());
                }
                instance.setProperty(attr.toLowerCase(), value);
            }
        }

        @Override
        public void done(FieldMetaData instance) {
            String data_type = instance.getProperty("data_type").toString().toLowerCase();
            int len = instance.getProperty("data_length").toInt();
            int data_precision = instance.getProperty("data_precision").toInt();
            int data_scale = instance.getProperty("data_scale").toInt();
            if (data_type == null) {
                data_type = "varchar2";
            }
            JDBCType jdbcType = JDBCType.VARCHAR;
            Class javaType = String.class;
            switch (data_type) {
                case "varchar2":
                case "varchar":
                    data_type = data_type + "(" + len + ")";
                    jdbcType = JDBCType.VARCHAR;
                    break;
                case "integer":
                    jdbcType = JDBCType.INTEGER;
                    javaType = Integer.class;
                    data_type = "number(32,0)";
                    break;
                case "decimal":
                    data_type = "number";
                case "number":
                    if (data_scale == 0) {
                        jdbcType = JDBCType.INTEGER;
                        javaType = Integer.class;
                        data_type = data_type + "(" + data_precision + ")";
                    } else {
                        data_type = data_type + "(" + data_precision + "," + data_scale + ")";
                        jdbcType = JDBCType.NUMERIC;
                        javaType = Double.class;
                    }
                    break;
                case "timestamp":
                case "datetime":
                case "date":
                    javaType = Date.class;
                    instance.setValueConverter(new DateTimeConverter("yyyy-MM-dd HH:mm:ss", Date.class));
                    break;
                case "clob":
                    jdbcType = JDBCType.CLOB;
                    javaType = String.class;
                    instance.setValueConverter(new ClobValueConverter());
                    break;
            }
            instance.setDataType(data_type);
            instance.setJdbcType(jdbcType);
            instance.setJavaType(javaType);
        }
    }
}
