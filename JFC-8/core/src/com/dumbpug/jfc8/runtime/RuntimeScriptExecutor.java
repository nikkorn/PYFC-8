package com.dumbpug.jfc8.runtime;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RuntimeScriptExecutor {
    /**
     * The script.
     */
    private String script;
    /**
     * The script adapter.
     */
    private ScriptAdapter scriptAdapter;
    /**
     * The invokable engine.
     */
    private Invocable invocableEngine;

    /**
     * Creates a new instance of the RuntimeScriptExecutor class.
     * @param script The script.
     * @param scriptAdapter The script adapter.
     */
    public RuntimeScriptExecutor(String script, ScriptAdapter scriptAdapter) throws ScriptException {
        this.script        = script;
        this.scriptAdapter = scriptAdapter;

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        engine.put("_adapter_", scriptAdapter);
        engine.eval("this['line'] = function (x,y,w,h,c) { _adapter_.line(x,y,w,h,c); };");
        engine.eval("this['circle'] = function (x,y,r,c,f) { _adapter_.circle(x,y,r,c,f); };");

        // Evaluate the actual script.
        engine.eval(script);

        // javax.script.Invocable is an optional interface.
        // Check whether your script engine implements or not!
        // Note that the JavaScript engine implements Invocable interface.
        this.invocableEngine = (Invocable) engine;
    }

    /**
     * Attempt to invoke the 'update' function in the script, if it exists.
     * @throws ScriptException
     */
    public void invokeScriptUpdate() throws ScriptException {
        try {
            this.invocableEngine.invokeFunction("update" );
        } catch (NoSuchMethodException e) {
            // The user may not have defined an 'update' method, which is OK.
        }
    }
}
