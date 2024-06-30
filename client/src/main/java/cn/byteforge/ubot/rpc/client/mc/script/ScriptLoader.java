package cn.byteforge.ubot.rpc.client.mc.script;

import java.util.Collections;
import java.util.List;

public class ScriptLoader {

    private final String mainPath = "scripts/%s/main.js";

    private List<ScriptDescription> scripts;

    public void init() {
        // todo download
        ScriptDescription desc = new ScriptDescription();
        desc.setCode("chat");
        desc.setName("聊天互通");
        desc.setDescription("群与游戏聊天互通附属");
        scripts = Collections.singletonList(desc);

        for (ScriptDescription script : scripts) {
            String path = String.format(mainPath, desc.getCode());
            // getVersion 函数

        }
    }

}
