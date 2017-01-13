package org.onion.ezorm.run;

import org.onion.ezorm.param.UpdateParam;

import java.sql.SQLException;

/**
 * Created by zhouhao on 16-6-4.
 */
public interface Update<T> extends Conditional<Update<T>>, TriggerSkipSupport<Update<T>> {
    Update<T> set(T data);

    Update<T> set(String property, Object value);

    Update<T> includes(String... fields);

    Update<T> excludes(String... fields);

    Update<T> setParam(UpdateParam param);

    int exec() throws SQLException;
}
