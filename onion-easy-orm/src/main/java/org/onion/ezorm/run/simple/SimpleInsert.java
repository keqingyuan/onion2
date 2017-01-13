package org.onion.ezorm.run.simple;

import org.onion.ezorm.executor.SQL;
import org.onion.ezorm.executor.SqlExecutor;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.meta.expand.Trigger;
import org.onion.ezorm.meta.expand.Validator;
import org.onion.ezorm.param.InsertParam;
import org.onion.ezorm.render.SqlRender;
import org.onion.ezorm.run.Insert;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by zhouhao on 16-6-5.
 */
class SimpleInsert<T> extends ValidatorAndTriggerSupport<Insert<T>> implements Insert<T> {
    private InsertParam insertParam;
    private SimpleTable<T> table;
    private SqlExecutor sqlExecutor;

    public SimpleInsert(SimpleTable<T> table, SqlExecutor sqlExecutor) {
        this.table = table;
        this.sqlExecutor = sqlExecutor;
        insertParam = new InsertParam();
    }

    @Override
    public Insert<T> value(T data) {
        this.insertParam.setData(data);
        return this;
    }

    @Override
    public Insert<T> values(Collection<T> data) {
        this.insertParam.setData(data);
        return this;
    }

    @Override
    public int exec() throws SQLException {
        boolean supportBefore = !triggerSkip && table.getMeta().triggerIsSupport(Trigger.insert_before);
        boolean supportDone =  !triggerSkip &&table.getMeta().triggerIsSupport(Trigger.insert_done);
        Map<String, Object> context = table.getDatabase().getTriggerContextRoot();
        if (supportBefore || supportDone) {
            context = table.getDatabase().getTriggerContextRoot();
            context.put("table", table);
            context.put("database", table.getDatabase());
            context.put("param", insertParam);
        }
        if (supportBefore) {
            table.getMeta().on(Trigger.insert_before, context);
        }
        SqlRender<InsertParam> render = table.getMeta().getDatabaseMetaData().getRenderer(SqlRender.TYPE.INSERT);
        SQL sql = render.render(table.getMeta(), insertParam);
        tryValidate(insertParam.getData(), Validator.Operation.INSERT);
        int total = sqlExecutor.insert(sql);
        if (supportDone) {
            context.put("total", total);
            table.getMeta().on(Trigger.insert_done, context);
        }
        return total;
    }

    @Override
    TableMetaData getTableMeta() {
        return table.getMeta();
    }
}
