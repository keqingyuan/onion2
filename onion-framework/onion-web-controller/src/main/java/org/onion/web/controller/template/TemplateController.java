package org.onion.web.controller.template;

import org.onion.web.bean.po.template.Template;
import org.onion.web.controller.GenericController;
import org.onion.web.service.template.TemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhouhao on 16-5-23.
 */
@RestController
@RequestMapping("/template")
public class TemplateController extends GenericController<Template, String> {
    @Resource
    private TemplateService templateService;

    @Override
    protected TemplateService getService() {
        return templateService;
    }

}
