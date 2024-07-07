package cn.byteforge.ubot.rpc.server.service;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import io.kritor.authentication.AuthenticateRequest;
import io.kritor.authentication.AuthenticateResponse;
import io.kritor.authentication.AuthenticationServiceGrpc;

public class AuthenticationServiceImpl extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {

    @Override
    public void authenticate(AuthenticateRequest request, StreamObserver<AuthenticateResponse> observer) {
        String account = request.getAccount();
        String ticket = request.getTicket();
        System.out.println("接收到 account=" + account + " ticket=" + ticket);
        if ("admin".equals(account) && "admin".equals(ticket)) {
            observer.onNext(AuthenticateResponse.newBuilder()
                    .setCode(AuthenticateResponse.AuthenticateResponseCode.OK)
                    .setMsg("验证通过")
                    .build());
        } else {
            observer.onError(new StatusException(Status.UNAUTHENTICATED.withDescription("未知的账号或密码")));
        }
        observer.onCompleted();
    }

}
