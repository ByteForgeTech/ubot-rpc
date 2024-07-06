package cn.byteforge.ubot.rpc.client.mc.start.bukkit;

import cn.byteforge.ubot.rpc.client.mc.api.ClientAPI;
import cn.byteforge.ubot.rpc.client.mc.api.adapter.Bootstrap;
import cn.byteforge.ubot.rpc.client.mc.api.adapter.Configuration;
import cn.byteforge.ubot.rpc.client.mc.listener.PlayerChatListener;
import cn.byteforge.ubot.rpc.client.mc.script.ScriptLoader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class UBotMCClient extends JavaPlugin implements Bootstrap {

    private static UBotMCClient instance;

    @Override
    public void onEnable() {
        ClientAPI.setInstance(instance = this);
        ScriptLoader.getInstance();
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), instance);
    }

    @Override
    public void onDisable() {
        ClientAPI.setInstance(instance = null);
    }

    @Override
    public Configuration createConfig() {
        return new BukkitConfigSection.Config();
    }

    @Override
    public ClassLoader getInstClassLoader() {
        return getClassLoader();
    }

    @Override
    public Type getType() {
        return Type.BUKKIT;
    }

}
