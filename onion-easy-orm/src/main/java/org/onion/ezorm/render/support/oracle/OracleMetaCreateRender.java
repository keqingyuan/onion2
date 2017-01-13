package org.onion.ezorm.render.support.oracle;

import org.onion.commons.StringUtils;
import org.onion.ezorm.executor.BindSQL;
import org.onion.ezorm.executor.SQL;
import org.onion.ezorm.meta.FieldMetaData;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.render.SqlAppender;
import org.onion.ezorm.render.SqlRender;
import org.onion.ezorm.render.support.simple.SimpleSQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by zhouhao on 16-6-5.
 */
public class OracleMetaCreateRender implements SqlRender<Object> {
    @Override
    public SQL render(TableMetaData metaData, Object param) {
        SqlAppender createBody = new SqlAppender();
        List<String> comments = new ArrayList<>();
        Set<FieldMetaData> fieldMetaDatas = metaData.getFields();
        if (fieldMetaDatas.isEmpty()) throw new UnsupportedOperationException("未指定任何字段");
        createBody.add("\nCREATE TABLE ", metaData.getName(), "(");
        fieldMetaDatas.forEach(fieldMetaData -> {
            createBody.add("\n\t", fieldMetaData.getName(), " ").add(fieldMetaData.getDataType());
            if (fieldMetaData.getProperty("not-null", false).isTrue()
                    || metaData.getPrimaryKeys().contains(fieldMetaData.getName())) {
                createBody.add(" not null ");
            }
            //注释
            if (!StringUtils.isNullOrEmpty(fieldMetaData.getComment())) {
                comments.add(String.format("COMMENT ON COLUMN %s IS '%s'", fieldMetaData.getFullName(), fieldMetaData.getComment()));
            } else {
                comments.add(String.format("COMMENT ON COLUMN %s IS '%s'", fieldMetaData.getFullName(), fieldMetaData.getAlias()));

            }
            createBody.add(",");
        });
        createBody.removeLast();
        createBody.add("\n)");
        SimpleSQL simpleSQL = new SimpleSQL( createBody.toString(), param);
        if (!metaData.getPrimaryKeys().isEmpty()) {
            String pkList = metaData.getPrimaryKeys().stream().reduce((s, s2) -> s + "," + s2).get();
            String pkStr = String.format("alter table %s add constraint %s primary key(%s)",
                    metaData.getName(), metaData.getName() + "_pk", pkList);
            comments.add(pkStr);
        }
        List<BindSQL> bindSQLs = comments.stream().map(s -> {
            BindSQL sql = new BindSQL();
            sql.setSql(new SimpleSQL( s, param));
            return sql;
        }).collect(Collectors.toList());
        simpleSQL.setBindSQLs(bindSQLs);
        return simpleSQL;
    }
}
