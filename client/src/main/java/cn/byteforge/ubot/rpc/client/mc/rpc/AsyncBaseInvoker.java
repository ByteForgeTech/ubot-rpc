package cn.byteforge.ubot.rpc.client.mc.rpc;

import cn.byteforge.ubot.rpc.client.mc.api.BConsumer;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 异步 RPC 调用抽象类
 * */
public abstract class AsyncBaseInvoker<ServiceStub, Response> {

    protected final Channel channel;

    public AsyncBaseInvoker(Channel channel) {
        this.channel = channel;
    }

    /**
     * 调用指定的 RPC 方法
     * */
    public abstract CompletableFuture<Response> invoke(
            BConsumer<ServiceStub, StreamObserver<Response>> stubConsumer
    );

    /**
     * 创建 StreamObserver
     * */
    public static <Response> StreamObserver<Response> createObserver(
            @Nullable Consumer<Response> onNext,
            @Nullable Consumer<Throwable> onError,
            @Nullable Runnable onCompleted
    ) {
        return new StreamObserver<Response>() {
            @Override
            public void onNext(Response response) {
                if (onNext != null) {
                    onNext.accept(response);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (onError != null) {
                    onError.accept(throwable);
                }
            }

            @Override
            public void onCompleted() {
                if (onCompleted != null) {
                    onCompleted.run();
                }
            }
        };
    }

}
