package cn.byteforge.ubot.rpc.server.service;

import io.grpc.stub.StreamObserver;
import io.kritor.message.MessageServiceGrpc;
import io.kritor.message.SendMessageRequest;
import io.kritor.message.SendMessageResponse;

public class MessageServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {

    @Override
    public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> observer) {
        System.out.println("收到发送信息请求: " + request);;
        observer.onNext(SendMessageResponse.newBuilder()
                .setMessageId("666")
                .setMessageTime(System.currentTimeMillis())
                .build());
        observer.onCompleted();
    }

}
