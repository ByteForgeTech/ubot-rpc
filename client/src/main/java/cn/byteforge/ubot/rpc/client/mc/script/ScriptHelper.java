package cn.byteforge.ubot.rpc.client.mc.script;

import cn.byteforge.ubot.rpc.client.mc.api.ClientAPI;
import cn.byteforge.ubot.rpc.client.mc.config.UBotConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.event.Event;

/**
 * 游戏内元素广播帮助类
 * @apiNote 成员方法供 js 调用
 * */
@Slf4j
public class ScriptHelper {

    private ScriptHelper() {}

    /**
     * 广播游戏事件
     * */
    public static void broadcastEvent(Event event) {
        String eventType = event.getEventName();
        String method = "onGameEvent";
        ScriptLoader.getInstance().runAll(method, null, (desc, e) -> {
            log.error("脚本 {} 执行函数 {} 出错", desc.getCode(), method, e);
        }, event, eventType);
    }

    /**
     * 向指定群组发送 RPC 消息
     * @param message 消息内容
     * */
    public void sendGroupRpcMessage(String message) {
        UBotConfiguration.SettingsConfig config = UBotConfiguration.getInstance().getSettings();
        ClientAPI.getInstance().getRpcManager().sendGroupMessage(Long.toString(config.group), message);
    }

    private static final class InstanceHolder {
        static final ScriptHelper instance = new ScriptHelper();
    }

    public static ScriptHelper getInstance() {
        return ScriptHelper.InstanceHolder.instance;
    }

}
