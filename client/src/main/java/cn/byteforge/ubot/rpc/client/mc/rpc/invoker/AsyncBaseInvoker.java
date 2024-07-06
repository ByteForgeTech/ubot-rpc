package cn.byteforge.ubot.rpc.client.mc.rpc.invoker;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;

import java.util.function.Consumer;

public abstract class AsyncBaseInvoker<ServiceStub, Response> {

    protected final Channel channel;

    public AsyncBaseInvoker(Channel channel) {
        this.channel = channel;
    }

    public abstract void invoke(
            BConsumer<ServiceStub, StreamObserver<Response>> stubConsumer
    );

    protected StreamObserver<Response> createStreamObserver(
            Consumer<Response> onNext,
            Consumer<Throwable> onError,
            Runnable onCompleted
    ) {
        return new StreamObserver<Response>() {
            @Override
            public void onNext(Response response) {
                onNext.accept(response);
            }

            @Override
            public void onError(Throwable throwable) {
                onError.accept(throwable);
            }

            @Override
            public void onCompleted() {
                onCompleted.run();
            }
        };
    }

}
