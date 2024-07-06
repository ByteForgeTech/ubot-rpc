package cn.byteforge.ubot.rpc.client.mc.api;

import cn.byteforge.ubot.rpc.client.mc.api.adapter.Bootstrap;

public class ClientAPI {

    private static Bootstrap instance;

    /**
     * 设置当前启动器实例
     * @apiNote 启动器启动后自动调用
     * */
    public static void setInstance(Bootstrap instance) {
        ClientAPI.instance = instance;
    }

    /**
     * 获取当前启动器实例
     * */
    public static Bootstrap getInstance() {
        return instance;
    }

}
