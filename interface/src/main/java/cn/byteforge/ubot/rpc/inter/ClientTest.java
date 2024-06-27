package cn.byteforge.ubot.rpc.inter;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.kritor.authentication.AuthenticateRequest;
import io.kritor.authentication.AuthenticateResponse;
import io.kritor.authentication.AuthenticationServiceGrpc;
import io.kritor.event.EventServiceGrpc;
import io.kritor.event.EventStructure;
import io.kritor.event.EventType;
import io.kritor.event.RequestPushEvent;

import java.util.concurrent.Executors;

public class ClientTest {
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 12345)
                .usePlaintext()
                .enableRetry() // 允许尝试
                .executor(Executors.newCachedThreadPool()) // 使用协程的调度器
                .build();

        asyncAuth(channel);
//        register(channel);
        System.in.read();
    }

    /**
     * 异步，主动 grpc 请求
     * */
    public static void asyncAuth(Channel channel) {
        StreamObserver<AuthenticateResponse> observer = new StreamObserver<>() {
            @Override
            public void onNext(AuthenticateResponse response) {
                System.out.println(response);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };

        AuthenticationServiceGrpc
                .newStub(channel)
                .authenticate(AuthenticateRequest.newBuilder()
                        .setAccount("ubot-account")
                        .setTicket("123456").build(), observer);
    }

    public static void register(Channel channel) {
        StreamObserver<EventStructure> observer = new StreamObserver<>() {
            @Override
            public void onNext(EventStructure response) {
                System.out.println(response);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Client Register Error");
                System.out.println(throwable);
            }

            @Override
            public void onCompleted() {
                System.out.println("Client Register Complete");
            }
        };

        EventServiceGrpc
                .newStub(channel)
                .registerActiveListener(RequestPushEvent.newBuilder()
                        .setType(EventType.EVENT_TYPE_MESSAGE)
                        .build(), observer);
    }

}