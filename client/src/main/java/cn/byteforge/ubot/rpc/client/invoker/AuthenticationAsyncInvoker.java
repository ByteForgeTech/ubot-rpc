package cn.byteforge.ubot.rpc.client.invoker;

import io.grpc.Channel;
import io.kritor.authentication.AuthenticateRequest;
import io.kritor.authentication.AuthenticateResponse;
import io.kritor.authentication.AuthenticationServiceGrpc;

public class AuthenticationAsyncInvoker extends AsyncBaseInvoker<AuthenticateResponse> {

    @Override
    public void invoke(Channel channel) {
        AuthenticationServiceGrpc
                .newStub(channel)
                .authenticate(AuthenticateRequest.newBuilder()
                        .setAccount("ubot-account")
                        .setTicket("1234561").build(), createStreamObserver(
                                resp -> {
                                    System.out.println("AuthenticationRequest response: " + resp.getCode() + resp.getMsg());
                                },
                                Throwable::printStackTrace,
                                () -> {
                                    System.out.println("AuthenticationRequest complete");
                                })
                );
    }

}
