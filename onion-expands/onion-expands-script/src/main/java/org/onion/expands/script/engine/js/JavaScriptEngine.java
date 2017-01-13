package org.onion.expands.script.engine.js;


import org.onion.commons.MD5;
import org.onion.commons.StringUtils;
import org.onion.expands.script.engine.common.CommonScriptEngine;

import javax.script.CompiledScript;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * Created by 浩 on 2015-10-27 0027.
 */
public class JavaScriptEngine extends CommonScriptEngine {

    @Override
    public String getScriptName() {
        return "javascript";
    }

    @Override
    public boolean compile(String id, String code) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("compile {} {} : {}", getScriptName(), id, code);
        }
        if (compilable == null)
            init();
        CompiledScript compiledScript = compilable.compile(StringUtils.concat("(function(){", code, "\n})();"));
        CommonScriptContext scriptContext = new CommonScriptContext(id, MD5.defaultEncode(code), compiledScript);
        scriptBase.put(id, scriptContext);
        return true;
    }
}
