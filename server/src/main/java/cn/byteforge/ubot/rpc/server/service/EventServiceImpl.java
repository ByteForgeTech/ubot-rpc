package cn.byteforge.ubot.rpc.server.service;

import io.grpc.stub.StreamObserver;
import io.kritor.common.*;
import io.kritor.event.EventServiceGrpc;
import io.kritor.event.EventStructure;
import io.kritor.event.EventType;
import io.kritor.event.RequestPushEvent;

public class EventServiceImpl extends EventServiceGrpc.EventServiceImplBase {

    /**
     * Client -> Server 主动事件监听注册
     * */
    @Override
    public void registerActiveListener(RequestPushEvent request, StreamObserver<EventStructure> observer) {
        System.out.println("EventServiceImpl registerActiveListener: RequestPushEvent#type " + request.getType());
        int messageId = 0;
        while (true) {
            System.out.println("服务端下发事件");
            observer.onNext(EventStructure.newBuilder()
                    .setType(EventType.EVENT_TYPE_MESSAGE)
                    .setMessage(PushMessageBody.newBuilder()
                            .setTime(System.currentTimeMillis())
                            .setMessageId(String.valueOf(messageId++))
                            .setMessageSeq(-1)
                            .setContact(Contact.newBuilder()
                                    .setScene(Scene.FRIEND)
                                    .setPeer("765743073")
                                    .build())
                            .setSender(Sender.newBuilder()
                                    .setUid("765743073")
                                    .setNick("IllTamer")
                                    .build())
                            .addElements(Element.newBuilder()
                                    .setType(Element.ElementType.TEXT)
                                    .setText(TextElement.newBuilder()
                                            .setText("Hello World")
                                            .build())
                                    .build())
                            .build())
                    .build());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public StreamObserver<EventStructure> registerPassiveListener(StreamObserver<RequestPushEvent> observer) {
        return super.registerPassiveListener(observer);
    }

}
