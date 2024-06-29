package cn.byteforge.ubot.rpc.client.invoker;

import io.grpc.Channel;
import io.kritor.event.EventServiceGrpc;
import io.kritor.event.EventStructure;
import io.kritor.event.EventType;
import io.kritor.event.RequestPushEvent;

/**
 * 主动事件监听
 * */
public class ActiveEventAsyncInvoker extends AsyncBaseInvoker<EventStructure> {

    @Override
    public void invoke(Channel channel) {
        EventServiceGrpc
                .newStub(channel)
                .registerActiveListener(RequestPushEvent.newBuilder()
                        .setType(EventType.EVENT_TYPE_MESSAGE)
                        .build(), createStreamObserver(
                                resp -> {
                                    System.out.println("ActiveEventAsyncInvoker resp: " + resp.toString());
                                },
                                Throwable::printStackTrace,
                                () -> {
                                    System.out.println("ActiveEventAsyncInvoker complete");
                                })
                );
    }

}
