package cc.hongz.ztools.script.test;


import cc.hongz.ztools.core.io.resource.ResourceUtil;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@DisplayName("Nashorn 深层测试")
public class NashornDeepTest {

    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");

        engine.eval(ResourceUtil.readUtf8Str("filter1.js"));

        final Object filter1 = ((Invocable) engine).invokeFunction("filter1", 1, 2);
        assertFalse((Boolean) filter1);
    }
}
