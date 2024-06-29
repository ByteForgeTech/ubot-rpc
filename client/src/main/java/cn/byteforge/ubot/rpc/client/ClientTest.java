package cn.byteforge.ubot.rpc.client;

import cn.byteforge.ubot.rpc.client.invoker.ActiveEventAsyncInvoker;
import cn.byteforge.ubot.rpc.client.invoker.AuthenticationAsyncInvoker;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.Executors;

public class ClientTest {
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 12345)
                .usePlaintext()
                .enableRetry() // 允许尝试
                .executor(Executors.newCachedThreadPool())
                .build();


        new AuthenticationAsyncInvoker().invoke(channel);
        new ActiveEventAsyncInvoker().invoke(channel);
        System.in.read();
    }

}