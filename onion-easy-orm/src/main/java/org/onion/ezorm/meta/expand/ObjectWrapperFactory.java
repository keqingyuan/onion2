package org.onion.ezorm.meta.expand;


import org.onion.ezorm.meta.TableMetaData;

public interface ObjectWrapperFactory {
    <T> ObjectWrapper<T> createObjectWrapper(TableMetaData metaData);
}
