package cn.byteforge.ubot.rpc.client.mc.api.adapter;

import cn.byteforge.ubot.rpc.client.mc.rpc.RpcManager;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

public interface Bootstrap {

    /**
     * 保存文件资源到磁盘
     * @param replace 是否替换
     * */
    void saveResource(String fileName, boolean replace);

    /**
     * 创建配置类实例
     * */
    Configuration createConfig();

    /**
     * 获取资源文件夹
     * */
    File getDataFolder();

    /**
     * 获取日志实例
     * */
    Logger getLogger();

    /**
     * 获取 jar 内资源文件输入流
     * @param fileName 文件路径
     * */
    InputStream getResource(String fileName);

    /**
     * 获取当前启动器类加载器
     * */
    ClassLoader getInstClassLoader();

    RpcManager getRpcManager();

    Type getType();

    enum Type {
        // bukkit server
        BUKKIT
    }

}
