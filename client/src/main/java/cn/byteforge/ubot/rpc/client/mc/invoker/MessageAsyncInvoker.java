package cn.byteforge.ubot.rpc.client.mc.invoker;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.kritor.message.MessageServiceGrpc;

public class MessageAsyncInvoker<Response> extends AsyncBaseInvoker<MessageServiceGrpc.MessageServiceStub, Response> {

    public MessageAsyncInvoker(Channel channel) {
        super(channel);
    }

    @Override
    public void invoke(BConsumer<MessageServiceGrpc.MessageServiceStub, StreamObserver<Response>> stubConsumer) {
        stubConsumer.accept(MessageServiceGrpc.newStub(channel), createStreamObserver(resp -> {
                    System.out.println("MessageAsyncInvoker response: " + resp.toString());
                },
                Throwable::printStackTrace,
                () -> {
                    System.out.println("MessageAsyncInvoker complete");
                }));
    }

}
