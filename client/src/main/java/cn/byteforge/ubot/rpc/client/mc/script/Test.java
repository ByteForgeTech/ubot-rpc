package cn.byteforge.ubot.rpc.client.mc.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class Test {

    public static void main(String[] args) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        // TODO jdk15+手动导入 nashorn
        ScriptEngine engine = manager.getEngineByName("nashorn");
        // TODO 高频率调用编译为字节码
        engine.eval(new FileReader("/home/illtamer/Code/java/idea/github/ubot-rpc/client/src/main/resources/plugin.js"));
        Invocable invocable = (Invocable) engine;
        String result = (String) invocable.invokeFunction("showUser", "javascript engine");
        System.out.println("the result is : " + result);
    }

}
