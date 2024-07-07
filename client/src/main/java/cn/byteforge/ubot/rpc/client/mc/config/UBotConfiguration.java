package cn.byteforge.ubot.rpc.client.mc.config;

import cn.byteforge.ubot.rpc.client.mc.api.ClientAPI;
import cn.byteforge.ubot.rpc.client.mc.api.adapter.Bootstrap;
import cn.byteforge.ubot.rpc.client.mc.api.adapter.ConfigSection;
import cn.byteforge.ubot.rpc.client.mc.config.driver.ConfigFile;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nonnull;

@Getter
public class UBotConfiguration {

    private final ConfigFile configFile;
    private UBotConfig uBot;
    private SettingsConfig settings;

    private UBotConfiguration() {
        Bootstrap instance = ClientAPI.getInstance();
        this.configFile = new ConfigFile("config.yml", instance);
        loadConfigurations();
    }

    /**
     * 重载配置类
     * @apiNote 仅重载 config.yml 文件内容，其它组件不提供重载支持
     * */
    public static void reload() {
        UBotConfiguration instance = InstanceHolder.instance;
        instance.configFile.reload();
        instance.loadConfigurations();
    }

    private void loadConfigurations() {
        uBot = new UBotConfig();
        settings = new SettingsConfig();
    }

    @ToString
    public class UBotConfig {

        private ConfigSection section = configFile.getConfig().getSection("ubot");

        @Nonnull
        public final String host = section.getString("host");

        @Nonnull
        public final Integer port = section.getInt("port");

        @Nonnull
        public final String account = section.getString("account");

        @Nonnull
        public final String ticket = section.getString("ticket");

    }

    @ToString
    public class SettingsConfig {

        private ConfigSection section = configFile.getConfig().getSection("settings");

        @Nonnull
        public final Long group = section.getLong("group");

    }

    private static final class InstanceHolder {
        static final UBotConfiguration instance = new UBotConfiguration();
    }

    public static UBotConfiguration getInstance() {
        return UBotConfiguration.InstanceHolder.instance;
    }


}
