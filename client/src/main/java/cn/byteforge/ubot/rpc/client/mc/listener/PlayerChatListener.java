package cn.byteforge.ubot.rpc.client.mc.listener;

import cn.byteforge.ubot.rpc.client.mc.api.Scheduler;
import cn.byteforge.ubot.rpc.client.mc.script.ScriptHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Scheduler.runTask(() ->
                ScriptHelper.broadcastEvent(event));
    }

}
