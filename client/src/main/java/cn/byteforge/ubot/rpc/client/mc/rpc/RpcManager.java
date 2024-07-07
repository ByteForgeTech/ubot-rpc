package cn.byteforge.ubot.rpc.client.mc.rpc;

import cn.byteforge.ubot.rpc.client.mc.api.Scheduler;
import cn.byteforge.ubot.rpc.client.mc.rpc.invoker.AuthenticationAsyncInvoker;
import cn.byteforge.ubot.rpc.client.mc.rpc.invoker.MessageAsyncInvoker;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.kritor.authentication.AuthenticateRequest;
import io.kritor.authentication.AuthenticateResponse;
import io.kritor.common.Contact;
import io.kritor.common.Element;
import io.kritor.common.Scene;
import io.kritor.common.TextElement;
import io.kritor.message.SendMessageRequest;
import io.kritor.message.SendMessageResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Getter
@Slf4j
public class RpcManager {

    private final ManagedChannel channel;

    public RpcManager(String host, int port) {
        log.info("正在连接到RPC服务器 {}:{}", host, port);
        this.channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .enableRetry() // 允许重试
                .executor(Scheduler.IO_INTENSIVE)
                .build();
    }

    /**
     * 客户端发送群消息
     * */
    public boolean sendGroupMessage(String groupId, String message) {
        CompletableFuture<SendMessageResponse> future = new MessageAsyncInvoker<SendMessageResponse>(channel).invoke((stub, observer) ->
                stub.sendMessage(SendMessageRequest.newBuilder()
                        .setContact(Contact.newBuilder()
                                .setScene(Scene.GROUP)
                                .setPeer(groupId)
                                .build())
                        .addElements(Element.newBuilder()
                                .setType(Element.ElementType.TEXT)
                                .setText(TextElement.newBuilder()
                                        .setText(message)
                                        .build())
                                .build())
                        .build(), observer));
        try {
            SendMessageResponse resp = future.get();
            log.debug("客户端发送群消息成功: groupId-{} messageId-{}", groupId, resp.getMessageId());
            return true;
        } catch (InterruptedException | ExecutionException e) {
            log.warn("客户端发送群消息异常: groupId-{}", groupId, e);
        }
        return false;
    }

//    public void listenRpcEvent() {
//        new ActiveEventAsyncInvoker(channel).invoke((stub, observer) ->
//                stub.registerActiveListener(RequestPushEvent.newBuilder()
//                        .setType(EventType.EVENT_TYPE_MESSAGE)
//                        .build(), observer));
//    }

    /**
     * 客户端链接鉴权
     * */
    public boolean auth(String account, String ticket) {
        CompletableFuture<AuthenticateResponse> future = new AuthenticationAsyncInvoker(channel).invoke((stub, observer) ->
                stub.authenticate(AuthenticateRequest.newBuilder()
                        .setAccount("ubot-account")
                        .setTicket("1234561").build(), observer));
        try {
            AuthenticateResponse resp = future.get();
            if (resp.getCode() != AuthenticateResponse.AuthenticateResponseCode.OK) {
                log.warn("客户端验证失败: code-{} msg-{}", resp.getCode(), resp.getMsg());
                return false;
            }
            return true;
        } catch (InterruptedException | ExecutionException e) {
            log.warn("客户端验证异常", e);
        }
        return false;
    }

}
