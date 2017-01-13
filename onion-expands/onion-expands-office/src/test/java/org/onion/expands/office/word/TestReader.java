package org.onion.expands.office.word;


import org.onion.commons.file.FileUtils;
import org.onion.expands.office.word.support.template.DOCXTemplateReader;

import java.io.InputStream;

/**
 * Created by zhouhao on 16-6-6.
 */
public class TestReader {

    public static void main(String[] args) throws Exception {
        try (InputStream template = FileUtils.getResourceAsStream("docx/template.docx");
             InputStream data = FileUtils.getResourceAsStream("docx/template_data.docx")
        ) {
            DOCXTemplateReader reader = new DOCXTemplateReader(template, data);
            System.out.println(reader.read());
        }

    }
}
