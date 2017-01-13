package org.onion.ezorm.render.support.h2;

import org.onion.commons.StringUtils;
import org.onion.ezorm.executor.BindSQL;
import org.onion.ezorm.executor.EmptySQL;
import org.onion.ezorm.executor.SQL;
import org.onion.ezorm.meta.DatabaseMetaData;
import org.onion.ezorm.meta.FieldMetaData;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.render.SqlAppender;
import org.onion.ezorm.render.SqlRender;
import org.onion.ezorm.render.support.simple.SimpleSQL;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * Created by zhouhao on 16-6-5.
 */
public class H2MetaAlterRender implements SqlRender<Boolean> {

    private DatabaseMetaData databaseMetaData;

    public H2MetaAlterRender(DatabaseMetaData databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
    }

    @Override
    public SQL render(TableMetaData metaData, Boolean executeRemove) {
        TableMetaData old = databaseMetaData.getTable(metaData.getName());
        if (old == null) throw new UnsupportedOperationException("旧表不存在!");
        List<FieldMetaData> changedField = new ArrayList<>();
        List<FieldMetaData> addedField = new ArrayList<>();
        List<FieldMetaData> deletedField = new ArrayList<>();

        TableMetaData oldMeta = old;
        if (executeRemove)
            oldMeta.getFields().forEach(oldField -> {
                FieldMetaData newMeta = metaData.findFieldByName(oldField.getName());
                if (newMeta == null) {
                    try {
                        newMeta = metaData.getFields().stream()
                                .filter(fieldMetaData -> oldField.getName().equals(fieldMetaData.getProperty("old-name").getValue()))
                                .findFirst().get();
                    } catch (NoSuchElementException e) {
                    }
                }
                if (newMeta == null) {
                    //删除的字段
                    deletedField.add(oldField);
                }
            });
        metaData.getFields().forEach(newField -> {
            String oldName = newField.getProperty("old-name", newField.getName()).getValue();
            FieldMetaData oldField = oldMeta.findFieldByName(oldName);
            if (oldField == null) {
                //增加的字段
                addedField.add(newField);
            } else {
                if (!newField.getName().equals(oldField.getName()) ||
                        !newField.getDataType().equals(oldField.getDataType())
                        || !newField.getComment().equals(oldField.getComment())
                        || oldField.getProperty("not-null", false).getValue() != newField.getProperty("not-null", false).getValue()) {
                    changedField.add(newField);
                }
            }
        });
        LinkedList<BindSQL> bind = new LinkedList<>();
        List<String> comments = new ArrayList<>();
        String newTableComment = metaData.getComment();
        String oldTableComment = old.getComment();
        if (newTableComment == null) newTableComment = "";
        if (oldTableComment == null) oldTableComment = "";
        if (!newTableComment.equals(oldTableComment)) {
            comments.add(String.format("COMMENT ON TABLE %s IS '%s'", metaData.getName(), metaData.getComment()));
        }
        if (addedField.isEmpty() && changedField.isEmpty() && deletedField.isEmpty() && comments.isEmpty()) {
            return new EmptySQL();
        }
        addedField.forEach(field -> {
            SqlAppender append = new SqlAppender();
            append.add("ALTER TABLE ", metaData.getName(), " ADD ", field.getName(), " ", field.getDataType());
            if (field.getProperty("not-null").isTrue()) {
                append.add(" not null");
            }
            if (StringUtils.isNullOrEmpty(field.getComment())) {
                comments.add(String.format("COMMENT ON COLUMN %s.%s is '新建字段:%s'", metaData.getName(), field.getName(), field.getAlias()));
            } else {
                comments.add(String.format("COMMENT ON COLUMN %s.%s is '%s'", metaData.getName(), field.getName(), field.getComment()));
            }
            SimpleSQL simpleSQL = new SimpleSQL(append.toString(), field);
            BindSQL bindSQL = new BindSQL();
            bindSQL.setSql(simpleSQL);
            bindSQL.setToField(field.getName());
            bind.add(bindSQL);
        });
        changedField.forEach(field -> {
            String oldName = field.getProperty("old-name").getValue();
            if (oldName == null) oldName = field.getName();
            FieldMetaData oldField = oldMeta.findFieldByName(oldName);
            if (!oldName.equals(field.getName())) {
                SqlAppender renameSql = new SqlAppender();
                renameSql.add("ALTER TABLE ", metaData.getName(), " ALTER COLUMN ", oldName, " RENAME TO ", field.getName());
                BindSQL bindSQL = new BindSQL();
                bindSQL.setSql(new SimpleSQL(renameSql.toString()));
                bind.add(bindSQL);
                metaData.renameField(oldName, field.getName());
            }
            if (!oldField.getDataType().equals(field.getDataType())
                    || oldField.getProperty("not-null", false).getValue() != field.getProperty("not-null", false).getValue()) {
                SqlAppender append = new SqlAppender();
                append.add("ALTER TABLE ", metaData.getName(), " MODIFY ", field.getName(), " ", field.getDataType());
                if (field.getProperty("not-null").isTrue()) {
                    append.add(" NOT NULL");
                }
                SimpleSQL simpleSQL = new SimpleSQL(append.toString(), field);
                BindSQL bindSQL = new BindSQL();
                bindSQL.setSql(simpleSQL);
                bindSQL.setToField(field.getName());
                bind.add(bindSQL);
            }
            String nc = field.getComment();
            String oc = oldField.getComment();
            if (nc == null) nc = "";
            if (oc == null) oc = "";
            if (nc.equals(oc)) return;
            if (StringUtils.isNullOrEmpty(nc)) {
                comments.add(String.format("COMMENT ON COLUMN %s.%s IS '新建字段:%s'", metaData.getName(), field.getName(), field.getAlias()));
            } else {
                comments.add(String.format("COMMENT ON COLUMN %s.%s IS '%s'", metaData.getName(), field.getName(), nc));
            }
        });
        deletedField.forEach(field -> {
            String dropSql = String.format("ALTER TABLE %s DROP COLUMN %s", metaData.getName(), field.getName());
            SimpleSQL simpleSQL = new SimpleSQL(dropSql, field);
            BindSQL bindSQL = new BindSQL();
            bindSQL.setSql(simpleSQL);
            bindSQL.setToField(field.getName());
            bind.add(bindSQL);
        });
        LinkedList<BindSQL> commentSql = new LinkedList<>(comments.stream().map(s -> {
            BindSQL binSql = new BindSQL();
            binSql.setSql(new SimpleSQL(s, s));
            return binSql;
        }).collect(Collectors.toList()));

        SQL sql = null;
        bind.addAll(commentSql);
        if (!bind.isEmpty()) {
            sql = bind.get(0).getSql();
            bind.removeFirst();
        }
        if (!bind.isEmpty())
            ((SimpleSQL) sql).setBindSQLs(bind);
        return sql;
    }

}
