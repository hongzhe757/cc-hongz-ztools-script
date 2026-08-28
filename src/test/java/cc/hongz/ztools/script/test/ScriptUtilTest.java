package cc.hongz.ztools.script.test;

import cc.hongz.ztools.core.io.resource.ResourceUtil;
import cc.hongz.ztools.script.ScriptRuntimeException;
import cc.hongz.ztools.script.ScriptUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * 脚本单元测试类
 *
 * @author looly
 *
 */
@DisplayName("脚本工具类测试")
public class ScriptUtilTest {

    @Test
    @DisplayName("编译脚本")
    @Disabled("Nashorn 已从 JDK 15+ 移除")
    public void compileTest() {
        CompiledScript script = ScriptUtil.compile("print('Script test!');");
        try {
            script.eval();
        } catch (ScriptException e) {
            throw new ScriptRuntimeException(e);
        }
    }

    @Test
    @DisplayName("执行脚本")
    @Disabled("Nashorn 已从 JDK 15+ 移除")
    public void evalTest() {
        ScriptUtil.eval("print('Script test!');");
    }

    @Test
    @DisplayName("调用脚本方法")
    @Disabled("Nashorn 已从 JDK 15+ 移除")
    public void invokeTest() {
        final Object result = ScriptUtil.invoke(ResourceUtil.readUtf8Str("filter1.js"), "filter1", 2, 1);
        assertTrue((Boolean) result);
    }

    @Test
    @DisplayName("Python 引擎")
    public void pythonTest() throws ScriptException {
        final ScriptEngine pythonEngine = ScriptUtil.getPythonEngine();
        pythonEngine.eval("print('Hello Python')");
    }

    @Test
    @DisplayName("Lua 引擎")
    public void luaTest() throws ScriptException {
        final ScriptEngine engine = ScriptUtil.getLuaEngine();
        engine.eval("print('Hello Lua')");
    }

    @Test
    @DisplayName("Groovy 引擎")
    public void groovyTest() throws ScriptException {
        final ScriptEngine engine = ScriptUtil.getGroovyEngine();
        engine.eval("println 'Hello Groovy'");
    }
}
