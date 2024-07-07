import cn.byteforge.ubot.rpc.client.mc.rpc.RpcManager;

import java.io.IOException;

public class TestMain {

    public static void main(String[] args) throws IOException {
        RpcManager manager = new RpcManager("127.0.0.1", 12345);
        System.out.println(manager);
        System.in.read();
    }

}
