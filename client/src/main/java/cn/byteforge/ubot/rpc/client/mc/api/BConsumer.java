package cn.byteforge.ubot.rpc.client.mc.api;

public interface BConsumer<T1, T2> {

    void accept(T1 t1, T2 t2);

}
