package org.onion.expands.office.excel;


import org.onion.expands.office.excel.config.ExcelWriterConfig;

import java.io.OutputStream;

/**
 * Created by 浩 on 2015-12-07 0007.
 */
public interface ExcelWriter {
    void write(OutputStream outputStream, ExcelWriterConfig config, ExcelWriterConfig... moreSheet) throws Exception;
}
