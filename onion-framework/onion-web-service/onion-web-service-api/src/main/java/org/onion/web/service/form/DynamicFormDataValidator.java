package org.onion.web.service.form;

import org.onion.ezorm.run.Table;

import java.util.Map;

public interface DynamicFormDataValidator {
    String getRepeatDataId(Table table, Map<String, Object> data);

}
