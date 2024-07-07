package cn.byteforge.ubot.rpc.client.mc.rpc.invoker;

import cn.byteforge.ubot.rpc.client.mc.api.BConsumer;
import cn.byteforge.ubot.rpc.client.mc.rpc.AsyncBaseInvoker;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.kritor.authentication.AuthenticateResponse;
import io.kritor.authentication.AuthenticationServiceGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * 验证 RPC 链接
 * */
@Slf4j
public class AuthenticationAsyncInvoker extends AsyncBaseInvoker<AuthenticationServiceGrpc.AuthenticationServiceStub, AuthenticateResponse> {

    public AuthenticationAsyncInvoker(Channel channel) {
        super(channel);
    }

    @Override
    public CompletableFuture<AuthenticateResponse> invoke(BConsumer<AuthenticationServiceGrpc.AuthenticationServiceStub, StreamObserver<AuthenticateResponse>> stubConsumer) {
        CompletableFuture<AuthenticateResponse> future = new CompletableFuture<>();
        stubConsumer.accept(AuthenticationServiceGrpc.newStub(channel), AsyncBaseInvoker.createObserver(
                future::complete,
                future::completeExceptionally,
                () -> log.debug("AuthenticationAsyncInvoker complete")));
        return future;
    }
}
