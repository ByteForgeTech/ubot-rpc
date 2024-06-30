package cn.byteforge.ubot.rpc.client.mc;

import cn.byteforge.ubot.rpc.client.mc.invoker.ActiveEventAsyncInvoker;
import cn.byteforge.ubot.rpc.client.mc.invoker.AuthenticationAsyncInvoker;
import cn.byteforge.ubot.rpc.client.mc.invoker.MessageAsyncInvoker;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.kritor.authentication.AuthenticateRequest;
import io.kritor.common.Contact;
import io.kritor.common.Element;
import io.kritor.common.Scene;
import io.kritor.common.TextElement;
import io.kritor.event.EventType;
import io.kritor.event.RequestPushEvent;
import io.kritor.message.SendMessageRequest;
import io.kritor.message.SendMessageResponse;

import java.util.concurrent.Executors;

public class ClientTest {
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 12345)
                .usePlaintext()
                .enableRetry() // 允许尝试
                .executor(Executors.newCachedThreadPool())
                .build();


        new AuthenticationAsyncInvoker(channel).invoke((stub, observer) ->
                stub.authenticate(AuthenticateRequest.newBuilder()
                        .setAccount("ubot-account")
                        .setTicket("1234561").build(), observer));
        new ActiveEventAsyncInvoker(channel).invoke((stub, observer) ->
                stub.registerActiveListener(RequestPushEvent.newBuilder()
                        .setType(EventType.EVENT_TYPE_MESSAGE)
                        .build(), observer));
        new MessageAsyncInvoker<SendMessageResponse>(channel).invoke((stub, observer) ->
                stub.sendMessage(SendMessageRequest.newBuilder()
                        .setContact(Contact.newBuilder()
                                .setScene(Scene.FRIEND)
                                .setPeer("765743073")
                                .build())
                        .addElements(Element.newBuilder()
                                .setType(Element.ElementType.TEXT)
                                .setText(TextElement.newBuilder()
                                        .setText("发送消息测试")
                                        .build())
                                .build())
                        .build(), observer));
        System.in.read();
    }

}