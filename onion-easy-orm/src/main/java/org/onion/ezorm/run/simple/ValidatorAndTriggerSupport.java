package org.onion.ezorm.run.simple;

import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.meta.expand.Validator;
import org.onion.ezorm.run.TriggerSkipSupport;

import java.util.Map;

/**
 * Created by zhouhao on 16-6-5.
 */
public abstract class ValidatorAndTriggerSupport<O> implements TriggerSkipSupport<O> {
    protected boolean triggerSkip = false;

    void tryValidate(Object date, Validator.Operation operation) {
        Validator validator = getTableMeta().getValidator();
        if (validator != null) {
            validator.validate(date, operation);
        }
    }

    void trigger(String name, Map<String, Object> root) {
        TableMetaData metaData = getTableMeta();
        metaData.on(name, root);
    }

    abstract TableMetaData getTableMeta();

    @Override
    public O skipTrigger() {
        triggerSkip = true;
        return (O) this;
    }
}
