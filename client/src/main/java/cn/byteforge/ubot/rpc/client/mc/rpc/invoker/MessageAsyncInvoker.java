package cn.byteforge.ubot.rpc.client.mc.rpc.invoker;

import cn.byteforge.ubot.rpc.client.mc.api.BConsumer;
import cn.byteforge.ubot.rpc.client.mc.rpc.AsyncBaseInvoker;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.kritor.message.MessageServiceGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class MessageAsyncInvoker<Response> extends AsyncBaseInvoker<MessageServiceGrpc.MessageServiceStub, Response> {

    public MessageAsyncInvoker(Channel channel) {
        super(channel);
    }

    @Override
    public CompletableFuture<Response> invoke(BConsumer<MessageServiceGrpc.MessageServiceStub, StreamObserver<Response>> stubConsumer) {
        CompletableFuture<Response> future = new CompletableFuture<>();
        stubConsumer.accept(MessageServiceGrpc.newStub(channel), AsyncBaseInvoker.createObserver(
                future::complete,
                future::completeExceptionally,
                () -> log.debug("MessageAsyncInvoker complete")));
        return future;
    }

}
