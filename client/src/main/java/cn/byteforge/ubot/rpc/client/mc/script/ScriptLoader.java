package cn.byteforge.ubot.rpc.client.mc.script;

import cn.byteforge.ubot.rpc.client.mc.api.ClientAPI;
import cn.byteforge.ubot.rpc.client.mc.api.adapter.Bootstrap;
import cn.byteforge.ubot.rpc.client.mc.rpc.invoker.BConsumer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
public class ScriptLoader {

    private static final String SCRIPTS_FOLDER_PATH = "scripts/";
    private static final String SCRIPTS_PATH = "scripts/%s/";
    // 含内置和网络下载的附属
    private static final List<ScriptDescription> EMBED_SCRIPTS;

    private final ScriptEngine engine;
    private final List<ScriptDescription> scripts;

    static {
        EMBED_SCRIPTS = new ArrayList<>(Collections.singletonList(
                ScriptDescription.builder()
                        .code("chat")
                        .name("聊天互通")
                        .description("群与游戏聊天互通附属").build()));
        init();
    }

    private ScriptLoader() {
        this.engine = loadScriptEngine();
        this.scripts = loadScripts();
    }

    /**
     * 执行 js 脚本中指定的方法
     * @return 脚本函数返回值
     * */
    public Object run(String script, String method, Object... args) throws ScriptException, NoSuchMethodException {
        engine.eval(script);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(method, args);
    }

    /**
     * 执行所有 js 脚本中指定的方法
     * */
    public void runAll(String method, @Nullable Consumer<Object> callback, @Nullable BConsumer<ScriptDescription, Exception> eCallback, Object... args) {
        for (ScriptDescription desc : scripts) {
            try {
                Object result = run(desc.getContent(), method, args);
                if (callback != null) {
                    callback.accept(result);
                }
            } catch (Exception e) {
                if (eCallback != null) {
                    eCallback.accept(desc, e);
                }
            }
        }
    }

    private ScriptEngine loadScriptEngine() {
        ScriptEngineManager manager = new ScriptEngineManager(ClientAPI.getInstance().getInstClassLoader());
        // TODO jdk15+手动导入 nashorn
        ScriptEngine nashorn = manager.getEngineByName("nashorn");
        nashorn.put("ScriptHelper", ScriptHelper.getInstance());
        return nashorn;
    }

    // 加载可用的有效脚本
    private List<ScriptDescription> loadScripts() {
        File folder = ClientAPI.getInstance().getDataFolder();
        File scriptsFolder = new File(folder, SCRIPTS_FOLDER_PATH);

        // 加载含描述文件的有效脚本
        File[] listFiles = scriptsFolder.listFiles();
        List<ScriptDescription> validScripts;
        if (listFiles == null) {
            validScripts = Collections.emptyList();
        } else {
            validScripts = Arrays.stream(listFiles).map(sf -> {
                try {
                    File file = new File(sf, "description.json");
                    return new Gson().fromJson(new FileReader(file), ScriptDescription.class);
                } catch (Exception e) {
                    log.warn("脚本 {} 描述文件加载失败", sf.getName(), e);
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }

        // 加载脚本到内存
        List<ScriptDescription> loadedScripts = new ArrayList<>();
        for (ScriptDescription script : validScripts) {
            File mainFile = new File(folder, String.format(SCRIPTS_PATH, script.getCode()) + "main.js");
            String method = "getVersion";
            try {
                // TODO 预编译为字节码
                byte[] bytes = Files.readAllBytes(mainFile.toPath());
                String content = new String(bytes, StandardCharsets.UTF_8);
                script.setContent(content);
                String version = String.valueOf(run(content, method));
                loadedScripts.add(script);
                log.info("成功加载脚本 [{}]{} -v{}", script.getCode(), script.getName(), version);
            } catch (IOException e) {
                log.warn("无法从 {} 加载脚本文件", mainFile.getAbsolutePath(), e);
            } catch (NoSuchMethodException | ScriptException e) {
                log.error("执行脚本 [{}]{} 方法 {} 出错", script.getCode(), script.getName(), method, e);
            }
        }
        return loadedScripts;
    }

    private static void init() {
        Bootstrap instance = ClientAPI.getInstance();
        // 初始化内置脚本
        for (ScriptDescription embedScript : EMBED_SCRIPTS) {
            String parentPath = String.format(SCRIPTS_PATH, embedScript.getCode());
            // init main.js
            String mainPath = parentPath + "main.js";
            File mainFile = new File(instance.getDataFolder(), mainPath);
            if (!mainFile.exists()) {
                instance.saveResource(mainPath, false);
            }
            // description.json
            String descPath = parentPath + "description.json";
            File descFile = new File(instance.getDataFolder(), descPath);
            if (!descFile.exists()) {
                PrintStream printStream = null;
                try {
                    descFile.createNewFile();
                    printStream = new PrintStream(descFile);
                    printStream.println(new Gson().toJson(embedScript));
                } catch (IOException e) {
                    log.warn("初始化脚本描述文件 {} 失败", parentPath, e);
                } finally {
                    if (printStream != null) {
                        printStream.close();
                    }
                }
            }
        }
        // TODO 下载网络脚本
    }

    private static final class InstanceHolder {
        static final ScriptLoader instance = new ScriptLoader();
    }

    public static ScriptLoader getInstance() {
        return InstanceHolder.instance;
    }

}
