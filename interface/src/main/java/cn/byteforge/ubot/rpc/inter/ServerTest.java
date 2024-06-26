package cn.byteforge.ubot.rpc.inter;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.Optional;

public class ServerTest {

    private Server server;

    public void start() throws IOException, InterruptedException {
        server = ServerBuilder
                .forPort(12345)
                .addService(new Authentication())
                .build().start();

        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(
                                () -> {
//                                    logger.log(Level.WARNING, "监听到JVM停止,正在关闭GRPC服务....");
                                    ServerTest.this.stop();
//                                    logger.log(Level.WARNING, "服务已经停止...");
                                }));
        System.out.println("服务已开启");
        server.awaitTermination();
    }

    public void stop() {
        Optional.of(server).map(Server::shutdown);
    }

    public static void main(String[] args) throws Exception {
        ServerTest server = new ServerTest();
        server.start();
    }

}
