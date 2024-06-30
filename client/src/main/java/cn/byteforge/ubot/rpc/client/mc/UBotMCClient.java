package cn.byteforge.ubot.rpc.client.mc;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class UBotMCClient extends JavaPlugin {

    private static UBotMCClient instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

}
