package cn.byteforge.ubot.rpc.client.mc.invoker;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.kritor.authentication.AuthenticateResponse;
import io.kritor.authentication.AuthenticationServiceGrpc;

public class AuthenticationAsyncInvoker extends AsyncBaseInvoker<AuthenticationServiceGrpc.AuthenticationServiceStub, AuthenticateResponse> {

    public AuthenticationAsyncInvoker(Channel channel) {
        super(channel);
    }

    @Override
    public void invoke(BConsumer<AuthenticationServiceGrpc.AuthenticationServiceStub, StreamObserver<AuthenticateResponse>> stubConsumer) {
        stubConsumer.accept(AuthenticationServiceGrpc.newStub(channel), createStreamObserver(
                resp -> {
                    System.out.println("AuthenticationRequest response: " + resp.getCode() + resp.getMsg());
                },
                Throwable::printStackTrace,
                () -> {
                    System.out.println("AuthenticationRequest complete");
                }));
    }
}
