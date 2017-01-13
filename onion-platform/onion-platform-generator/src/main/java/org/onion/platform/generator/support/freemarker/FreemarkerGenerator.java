package org.onion.platform.generator.support.freemarker;

import org.onion.expandes.template.Template;
import org.onion.platform.generator.Generator;
import org.onion.platform.generator.template.CodeTemplate;
import org.onion.platform.generator.utils.GeneratorUtils;

import java.util.HashMap;
import java.util.Map;

public class FreemarkerGenerator implements Generator<String> {
    static GeneratorUtils utils = new GeneratorUtils();

    @Override
    public void start(CodeTemplate<String> codeTemplate, CodeTemplate<String>... codeTemplates) {
        render(codeTemplate);
        for (int i = 0; i < codeTemplates.length; i++) {
            render(codeTemplates[i]);
        }
    }

    public static void render(CodeTemplate<String> codeTemplate) {
        String template = codeTemplate.getInput().read();
        try {
            Map<String, Object> var = new HashMap<>(codeTemplate.getVars());
            var.put("utils", utils);
            String code = Template.freemarker.compile(template).render(var);
            codeTemplate.getOutPut().write(code);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
