package cc.hongz.ztools.script;

import cc.hongz.ztools.core.map.reference.WeakKeyValueConcurrentMap;
import cc.hongz.ztools.core.util.StrUtil;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 脚本工具类，基于 JSR 223 封装常用脚本引擎的获取与执行.
 *
 * <p>
 * 内置便捷方法支持的引擎：
 * <ul>
 *     <li>JavaScript：{@link #getJsEngine()} / {@link #createJsEngine()}（JDK 内置，JDK 15+ 需 GraalJS）</li>
 *     <li>Python：{@link #getPythonEngine()} / {@link #createPythonEngine()}（需引入 org.python:jython）</li>
 *     <li>Lua：{@link #getLuaEngine()} / {@link #createLuaEngine()}（需引入 org.luaj:luaj-jse）</li>
 *     <li>Groovy：{@link #getGroovyEngine()} / {@link #createGroovyEngine()}（需引入 org.codehaus.groovy:groovy-all）</li>
 *     <li>Ruby：{@link #getRubyEngine()} / {@link #createRubyEngine()}（需引入 org.jruby:jruby-core）</li>
 *     <li>Kotlin：{@link #getKotlinEngine()} / {@link #createKotlinEngine()}（需引入
 *         org.jetbrains.kotlin:kotlin-scripting-jsr223）</li>
 *     <li>BeanShell：{@link #getBeanShellEngine()} / {@link #createBeanShellEngine()}（需引入
 *         org.apache-extras.beanshell:bsh）</li>
 * </ul>
 * 其它实现 JSR 223 的引擎可通过 {@link #getScript(String)} 或 {@link #createScript(String)} 加载.
 *
 * @author Looly
 */
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class ScriptUtil {

    private static final ScriptEngineManager MANAGER = new ScriptEngineManager();
    private static final WeakKeyValueConcurrentMap<String, ScriptEngine> CACHE = new WeakKeyValueConcurrentMap<>();

    /**
     * 获得单例的{@link ScriptEngine} 实例.
     *
     * @param nameOrExtOrMime 脚本名称
     * @return {@link ScriptEngine} 实例
     */
    public static ScriptEngine getScript(final String nameOrExtOrMime) {
        return CACHE.computeIfAbsent(nameOrExtOrMime, (key) -> createScript(nameOrExtOrMime));
    }

    /**
     * 创建 {@link ScriptEngine} 实例.
     *
     * @param nameOrExtOrMime 脚本名称
     * @return {@link ScriptEngine} 实例
     * @since 5.2.6
     */
    public static ScriptEngine createScript(final String nameOrExtOrMime) {
        ScriptEngine engine = MANAGER.getEngineByName(nameOrExtOrMime);
        if (null == engine) {
            engine = MANAGER.getEngineByExtension(nameOrExtOrMime);
        }
        if (null == engine) {
            engine = MANAGER.getEngineByMimeType(nameOrExtOrMime);
        }
        if (null == engine) {
            throw new NullPointerException(StrUtil.format("Script for [{}] not support !", nameOrExtOrMime));
        }
        return engine;
    }

    /**
     * 获得非单例的 Javascript引擎 {@link JavaScriptEngine}.
     *
     * @return {@link JavaScriptEngine}
     */
    public static JavaScriptEngine getJavaScriptEngine() {
        return new JavaScriptEngine();
    }

    /**
     * 获得单例的JavaScript引擎.
     *
     * @return Javascript引擎
     * @since 5.2.5
     */
    public static ScriptEngine getJsEngine() {
        return getScript("js");
    }

    /**
     * 创建新的JavaScript引擎.
     *
     * @return Javascript引擎
     * @since 5.2.6
     */
    public static ScriptEngine createJsEngine() {
        return createScript("js");
    }

    /**
     * 获得单例的Python引擎. <br>
     * 需要引入org.python:jython
     *
     * @return Python引擎
     * @since 5.2.5
     */
    public static ScriptEngine getPythonEngine() {
        System.setProperty("python.import.site", "false");
        return getScript("python");
    }

    /**
     * 创建Python引擎. <br>
     * 需要引入org.python:jython
     *
     * @return Python引擎
     * @since 5.2.6
     */
    public static ScriptEngine createPythonEngine() {
        System.setProperty("python.import.site", "false");
        return createScript("python");
    }

    /**
     * 获得单例的Lua引擎. <br>
     * 需要引入org.luaj:luaj-jse
     *
     * @return Lua引擎
     * @since 5.2.5
     */
    public static ScriptEngine getLuaEngine() {
        return getScript("lua");
    }

    /**
     * 创建Lua引擎. <br>
     * 需要引入org.luaj:luaj-jse
     *
     * @return Lua引擎
     * @since 5.2.6
     */
    public static ScriptEngine createLuaEngine() {
        return createScript("lua");
    }

    /**
     * 获得单例的Groovy引擎. <br>
     * 需要引入org.codehaus.groovy:groovy-all
     *
     * @return Groovy引擎
     * @since 5.2.5
     */
    public static ScriptEngine getGroovyEngine() {
        return getScript("groovy");
    }

    /**
     * 创建Groovy引擎. <br>
     * 需要引入org.codehaus.groovy:groovy-all
     *
     * @return Groovy引擎
     * @since 5.2.6
     */
    public static ScriptEngine createGroovyEngine() {
        return createScript("groovy");
    }

    /**
     * 获得单例的Ruby引擎. <br>
     * 需要引入org.jruby:jruby-core
     *
     * @return Ruby引擎
     * @since 1.0.0
     */
    public static ScriptEngine getRubyEngine() {
        return getScript("ruby");
    }

    /**
     * 创建Ruby引擎. <br>
     * 需要引入org.jruby:jruby-core
     *
     * @return Ruby引擎
     * @since 1.0.0
     */
    public static ScriptEngine createRubyEngine() {
        return createScript("ruby");
    }

    /**
     * 获得单例的Kotlin引擎. <br>
     * 需要引入org.jetbrains.kotlin:kotlin-scripting-jsr223
     *
     * @return Kotlin引擎
     * @since 1.0.0
     */
    public static ScriptEngine getKotlinEngine() {
        return getScript("kotlin");
    }

    /**
     * 创建Kotlin引擎. <br>
     * 需要引入org.jetbrains.kotlin:kotlin-scripting-jsr223
     *
     * @return Kotlin引擎
     * @since 1.0.0
     */
    public static ScriptEngine createKotlinEngine() {
        return createScript("kotlin");
    }

    /**
     * 获得单例的BeanShell引擎. <br>
     * 需要引入org.apache-extras.beanshell:bsh
     *
     * @return BeanShell引擎
     * @since 1.0.0
     */
    public static ScriptEngine getBeanShellEngine() {
        return getScript("beanshell");
    }

    /**
     * 创建BeanShell引擎. <br>
     * 需要引入org.apache-extras.beanshell:bsh
     *
     * @return BeanShell引擎
     * @since 1.0.0
     */
    public static ScriptEngine createBeanShellEngine() {
        return createScript("beanshell");
    }

    /**
     * 执行Javascript脚本，返回Invocable，此方法分为两种情况.
     *
     * <ol>
     *     <li>执行的脚本返回值是可执行的脚本方法</li>
     *     <li>脚本为函数库，则ScriptEngine本身为可执行方法</li>
     * </ol>
     *
     * @param script 脚本内容
     * @return 执行结果
     * @throws ScriptRuntimeException 脚本异常
     * @since 5.3.6
     */
    public static Invocable evalInvocable(final String script) throws ScriptRuntimeException {
        final ScriptEngine jsEngine = getJsEngine();
        final Object eval;
        try {
            eval = jsEngine.eval(script);
        } catch (ScriptException e) {
            throw new ScriptRuntimeException(e);
        }
        if (eval instanceof Invocable) {
            return (Invocable) eval;
        } else if (jsEngine instanceof Invocable) {
            return (Invocable) jsEngine;
        }
        throw new ScriptRuntimeException("Script is not invocable !");
    }

    /**
     * 执行有返回值的Javascript脚本.
     *
     * @param script 脚本内容
     * @return 执行结果
     * @throws ScriptRuntimeException 脚本异常
     * @since 3.2.0
     */
    public static Object eval(final String script) throws ScriptRuntimeException {
        try {
            return getJsEngine().eval(script);
        } catch (ScriptException e) {
            throw new ScriptRuntimeException(e);
        }
    }

    /**
     * 执行有返回值的脚本.
     *
     * @param script  脚本内容
     * @param context 脚本上下文
     * @return 执行结果
     * @throws ScriptRuntimeException 脚本异常
     * @since 3.2.0
     */
    public static Object eval(final String script, final ScriptContext context) throws ScriptRuntimeException {
        try {
            return getJsEngine().eval(script, context);
        } catch (ScriptException e) {
            throw new ScriptRuntimeException(e);
        }
    }

    /**
     * 执行有返回值的脚本.
     *
     * @param script   脚本内容
     * @param bindings 绑定的参数
     * @return 执行结果
     * @throws ScriptRuntimeException 脚本异常
     * @since 3.2.0
     */
    public static Object eval(final String script, final Bindings bindings) throws ScriptRuntimeException {
        try {
            return getJsEngine().eval(script, bindings);
        } catch (ScriptException e) {
            throw new ScriptRuntimeException(e);
        }
    }

    /**
     * 执行JS脚本中的指定方法.
     *
     * @param script js脚本
     * @param func 方法名
     * @param args 方法参数
     * @return 结果
     * @since 5.3.6
     */
    public static Object invoke(final String script, final String func, final Object... args) {
        final Invocable eval = evalInvocable(script);
        try {
            return eval.invokeFunction(func, args);
        } catch (ScriptException | NoSuchMethodException e) {
            throw new ScriptRuntimeException(e);
        }
    }

    /**
     * 编译Javascript脚本.
     *
     * @param script 脚本内容
     * @return {@link CompiledScript}
     * @throws ScriptRuntimeException 脚本异常
     * @since 3.2.0
     */
    public static CompiledScript compile(final String script) throws ScriptRuntimeException {
        try {
            return compile(getJsEngine(), script);
        } catch (ScriptException e) {
            throw new ScriptRuntimeException(e);
        }
    }

    /**
     * 编译Javascript脚本.
     *
     * @param engine 引擎
     * @param script 脚本内容
     * @return {@link CompiledScript}
     * @throws ScriptException 脚本异常
     */
    public static CompiledScript compile(final ScriptEngine engine, final String script) throws ScriptException {
        if (engine instanceof Compilable) {
            final Compilable compEngine = (Compilable) engine;
            return compEngine.compile(script);
        }
        return null;
    }
}
