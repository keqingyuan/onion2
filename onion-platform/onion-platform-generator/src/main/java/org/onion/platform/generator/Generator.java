package org.onion.platform.generator;

import org.onion.platform.generator.template.CodeTemplate;

import java.util.List;

/**
 * @author zhouhao
 */
public interface Generator<IN> {
     void start(CodeTemplate<IN> codeTemplate,CodeTemplate<IN>... codeTemplates);
}
