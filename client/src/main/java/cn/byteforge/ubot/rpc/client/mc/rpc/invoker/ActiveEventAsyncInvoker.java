package cn.byteforge.ubot.rpc.client.mc.rpc.invoker;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.kritor.event.EventServiceGrpc;
import io.kritor.event.EventStructure;

/**
 * 主动事件监听
 * */
public class ActiveEventAsyncInvoker extends AsyncBaseInvoker<EventServiceGrpc.EventServiceStub, EventStructure> {

    public ActiveEventAsyncInvoker(Channel channel) {
        super(channel);
    }

    @Override
    public void invoke(BConsumer<EventServiceGrpc.EventServiceStub, StreamObserver<EventStructure>> stubConsumer) {
        stubConsumer.accept(EventServiceGrpc.newStub(channel), createStreamObserver(
                resp -> {
                    System.out.println("ActiveEventAsyncInvoker resp: " + resp.toString());
                },
                Throwable::printStackTrace,
                () -> {
                    System.out.println("ActiveEventAsyncInvoker complete");
                }));
    }


}
