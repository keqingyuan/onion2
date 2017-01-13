package org.onion.ezorm.meta.expand;

import org.onion.ezorm.meta.TableMetaData;

/**
 * Created by zhouhao on 16-6-4.
 */
public interface ValidatorFactory {
    Validator createValidator(TableMetaData tableMetaData);
}
