package org.onion.ezorm.run.simple.wrapper;

import org.onion.ezorm.meta.FieldMetaData;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.meta.expand.OptionConverter;
import org.onion.ezorm.meta.expand.SimpleMapWrapper;
import org.onion.ezorm.meta.expand.ValueConverter;

import java.util.Map;

public class AdvancedMapWrapper extends SimpleMapWrapper {
    private TableMetaData tableMetaData;

    public AdvancedMapWrapper(TableMetaData tableMetaData) {
        this.tableMetaData = tableMetaData;
    }

    @Override
    public void wrapper(Map<String, Object> instance, int index, String attr, Object value) {
        FieldMetaData metaData = tableMetaData.findFieldByName(attr);
        if (null != metaData) {
            ValueConverter valueConverter = metaData.getValueConverter();
            super.wrapper(instance, index, attr, valueConverter.getValue(value));
            ValueConverter converter = metaData.getValueConverter();
            value = converter.getValue(value);
            OptionConverter optionConverter = metaData.getOptionConverter();
            if (optionConverter != null) {
                Object value1 = optionConverter.converterValue(value);
                String targetName = optionConverter.getFieldName();
                if (attr.contains(".")) {
                    targetName = attr.split("[.]")[0] + "." + targetName;
                }
                putValue(instance, targetName, value1);
            }
        } else {
            super.wrapper(instance, index, attr, value);
        }
    }
}
