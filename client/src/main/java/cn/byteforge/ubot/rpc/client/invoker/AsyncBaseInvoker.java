package cn.byteforge.ubot.rpc.client.invoker;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;

import java.util.function.Consumer;

public abstract class AsyncBaseInvoker<Response> {

    public abstract void invoke(Channel channel);

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
