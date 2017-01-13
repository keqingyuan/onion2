package org.onion.web.core.logger;

import org.onion.web.bean.po.logger.LoggerInfo;

/**
 * Created by zhouhao on 16-4-28.
 */
public interface AccessLoggerPersisting {
    void save(LoggerInfo loggerInfo);
}
