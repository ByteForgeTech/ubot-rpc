package cn.byteforge.ubot.rpc.server;

import io.grpc.stub.StreamObserver;
import io.kritor.authentication.AuthenticateRequest;
import io.kritor.authentication.AuthenticateResponse;
import io.kritor.authentication.AuthenticationServiceGrpc;

public class Authentication extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {

    @Override
    public void authenticate(AuthenticateRequest request, StreamObserver<AuthenticateResponse> responseObserver) {
        String account = request.getAccount();
        String ticket = request.getTicket();
        System.out.println("接收到 account=" + account + " ticket=" + ticket);
        responseObserver.onNext(AuthenticateResponse.newBuilder()
                .setCode(AuthenticateResponse.AuthenticateResponseCode.OK)
                .build());
//        throw new StatusException(Status.UNAUTHENTICATED.withDescription("验证失败"));
        responseObserver.onCompleted();
    }

}
