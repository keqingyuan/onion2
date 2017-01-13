package org.onion.web.controller.script;

import org.onion.expands.script.engine.DynamicScriptEngine;
import org.onion.expands.script.engine.DynamicScriptEngineFactory;
import org.onion.expands.script.engine.ExecuteResult;
import org.onion.web.bean.common.QueryParam;
import org.onion.web.core.exception.NotFoundException;
import org.onion.web.core.logger.annotation.AccessLogger;
import org.onion.web.core.authorize.annotation.Authorize;
import org.onion.web.bean.po.role.Role;
import org.onion.web.bean.po.script.DynamicScript;
import org.onion.web.controller.GenericController;
import org.onion.web.core.message.ResponseMessage;
import org.onion.web.core.utils.WebUtil;
import org.onion.web.service.script.DynamicScriptExecuteService;
import org.onion.web.service.script.DynamicScriptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态脚本控制器，继承自GenericController,使用rest+json
 * Created by generator 2015-10-27 14:42:34
 */
@RestController
@RequestMapping(value = "/script")
@AccessLogger("动态脚本")
@Authorize(module = "script")
public class DynamicScriptController extends GenericController<DynamicScript, String> {

    //默认服务类
    @Resource
    private DynamicScriptService dynamicScriptService;

    @Resource
    private DynamicScriptExecuteService dynamicScriptExecuteService;

    @Override
    public DynamicScriptService getService() {
        return this.dynamicScriptService;
    }

    @RequestMapping(value = "/compile", method = {RequestMethod.GET})
    @Authorize(action = "compile")
    @AccessLogger("编译所有脚本")
    public ResponseMessage compileAll() throws Exception {
        dynamicScriptService.compileAll();
        return ResponseMessage.ok("success");
    }

    @RequestMapping(value = "/compile/{id:.+}", method = {RequestMethod.GET})
    @Authorize(action = "compile")
    @AccessLogger("编译脚本")
    public ResponseMessage compile(@PathVariable("id") String id) throws Exception {
        dynamicScriptService.compile(id);
        return ResponseMessage.ok("success");
    }

    @RequestMapping(value = "/exec/{id:.+}", method = {RequestMethod.GET})
    @Authorize(action = "exec")
    @AccessLogger("执行脚本")
    public ResponseMessage execGet(@PathVariable("id") String id,
                                   QueryParam queryParam,
                                   @RequestParam(required = false) Map<String, Object> param) throws Throwable {
        if (param == null)
            param = new HashMap<>();
        param.put("queryParam", queryParam);
        param.put("user", WebUtil.getLoginUser());

        Object data = dynamicScriptExecuteService.exec(id, param);
        return ResponseMessage.ok(data);
    }

    @RequestMapping(value = "/exec/{id:.+}", method = {RequestMethod.POST, RequestMethod.PUT})
    @Authorize(action = "exec")
    @AccessLogger("执行脚本")
    public ResponseMessage execPostOrPut(@PathVariable("id") String id,
                                         @RequestBody(required = false) Map<String, Object> param) throws Throwable {
        if (param == null)
            param = new HashMap<>();
        param.put("user", WebUtil.getLoginUser());
        Object data = dynamicScriptExecuteService.exec(id, param);
        return ResponseMessage.ok(data);
    }

    @RequestMapping(value = "/exec/{id:.+}", method = RequestMethod.DELETE)
    @Authorize(action = "exec")
    @AccessLogger("执行脚本")
    public ResponseMessage execDelete(@PathVariable("id") String id) throws Throwable {
        Map<String, Object> param = new HashMap<>();
        param.put("user", WebUtil.getLoginUser());
        Object data = dynamicScriptExecuteService.exec(id, param);
        return ResponseMessage.ok(data);
    }

    @RequestMapping(value = "/exec/runtime/{type}", method = RequestMethod.POST)
    @Authorize(action = "runtime")
    @AccessLogger("执行脚本-runtime")
    public ResponseMessage runtime(@PathVariable("type") String type,
                                   @RequestBody String script) throws Throwable {
        DynamicScriptEngine engine = DynamicScriptEngineFactory.getEngine(type);
        if (engine == null) {
            throw new NotFoundException("不支持的动态脚本引擎!");
        }
        String id = "script.runtime";
        engine.compile(id, script);
        ExecuteResult result = engine.execute(id);
        if (!result.isSuccess()) {
            if (result.getException() != null) throw result.getException();
        }
        return ResponseMessage.ok(result.getResult());
    }


}
