package cc.hongz.ztools.script;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import java.io.Reader;

/**
 * Javascript引擎类.
 *
 * @author Looly
 */
public class JavaScriptEngine extends FullSupportScriptEngine {

    /**
     * 构造.
     */
    public JavaScriptEngine() {
        super(ScriptUtil.createJsEngine());
    }

    /**
     * 引擎实例.
     *
     * @return 引擎实例
     */
    public static JavaScriptEngine instance() {
        return new JavaScriptEngine();
    }

    //----------------------------------------------------------------------------------------------- Invocable
    @Override
    public Object invokeMethod(final Object thiz, final String name, final Object... args)
            throws ScriptException, NoSuchMethodException {
        return ((Invocable) engine).invokeMethod(thiz, name, args);
    }

    @Override
    public Object invokeFunction(final String name, final Object... args)
            throws ScriptException, NoSuchMethodException {
        return ((Invocable) engine).invokeFunction(name, args);
    }

    @Override
    public <T> T getInterface(final Class<T> clasz) {
        return ((Invocable) engine).getInterface(clasz);
    }

    @Override
    public <T> T getInterface(final Object thiz, final Class<T> clasz) {
        return ((Invocable) engine).getInterface(thiz, clasz);
    }

    //----------------------------------------------------------------------------------------------- Compilable
    @Override
    public CompiledScript compile(final String script) throws ScriptException {
        return ((Compilable) engine).compile(script);
    }

    @Override
    public CompiledScript compile(final Reader script) throws ScriptException {
        return ((Compilable) engine).compile(script);
    }

    //----------------------------------------------------------------------------------------------- ScriptEngine
    @Override
    public Object eval(final String script, final ScriptContext context) throws ScriptException {
        return engine.eval(script, context);
    }

    @Override
    public Object eval(final Reader reader, final ScriptContext context) throws ScriptException {
        return engine.eval(reader, context);
    }

    @Override
    public Object eval(final String script) throws ScriptException {
        return engine.eval(script);
    }

    @Override
    public Object eval(final Reader reader) throws ScriptException {
        return engine.eval(reader);
    }

    @Override
    public Object eval(final String script, final Bindings n) throws ScriptException {
        return engine.eval(script, n);
    }

    @Override
    public Object eval(final Reader reader, final Bindings n) throws ScriptException {
        return engine.eval(reader, n);
    }

    @Override
    public void put(final String key, final Object value) {
        engine.put(key, value);
    }

    @Override
    public Object get(final String key) {
        return engine.get(key);
    }

    @Override
    public Bindings getBindings(final int scope) {
        return engine.getBindings(scope);
    }

    @Override
    public void setBindings(final Bindings bindings, final int scope) {
        engine.setBindings(bindings, scope);
    }

    @Override
    public Bindings createBindings() {
        return engine.createBindings();
    }

    @Override
    public ScriptContext getContext() {
        return engine.getContext();
    }

    @Override
    public void setContext(final ScriptContext context) {
        engine.setContext(context);
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return engine.getFactory();
    }

}
