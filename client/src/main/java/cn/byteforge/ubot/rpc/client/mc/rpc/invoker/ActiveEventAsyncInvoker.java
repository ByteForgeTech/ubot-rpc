package cn.byteforge.ubot.rpc.client.mc.rpc.invoker;

import cn.byteforge.ubot.rpc.client.mc.api.BConsumer;
import cn.byteforge.ubot.rpc.client.mc.rpc.AsyncBaseInvoker;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.kritor.event.EventServiceGrpc;
import io.kritor.event.EventStructure;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * 主动事件监听
 * */
@Slf4j
public class ActiveEventAsyncInvoker extends AsyncBaseInvoker<EventServiceGrpc.EventServiceStub, EventStructure> {

    public ActiveEventAsyncInvoker(Channel channel) {
        super(channel);
    }

    @Override
    public CompletableFuture<EventStructure> invoke(BConsumer<EventServiceGrpc.EventServiceStub, StreamObserver<EventStructure>> stubConsumer) {
        CompletableFuture<EventStructure> future = new CompletableFuture<>();
        stubConsumer.accept(EventServiceGrpc.newStub(channel), createObserver(
                future::complete,
                future::completeExceptionally,
                () -> log.debug("ActiveEventAsyncInvoker complete")));
        return future;
    }


}
