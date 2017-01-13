package org.onion.web.service.form;

import org.onion.ezorm.meta.TableMetaData;
import org.onion.web.bean.po.form.Form;

import javax.xml.bind.Marshaller;

/**
 * Created by zhouhao on 16-4-20.
 */
public interface FormParser {
    TableMetaData parse(Form form);

    String parseHtml(Form form);

    interface Listener {
        void afterParse(TableMetaData tableMetaData);
    }
}
