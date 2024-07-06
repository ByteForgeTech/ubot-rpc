package cn.byteforge.ubot.rpc.client.mc.api;

import cn.byteforge.ubot.rpc.client.mc.util.ThreadPoolUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class Scheduler {

    public static final ExecutorService IO_INTENSIVE;

    static {
        IO_INTENSIVE = new ThreadPoolExecutor(
                3,
                ThreadPoolUtil.poolSize(0.90),
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactoryBuilder()
                        .setNameFormat("ubot-thread-%d")
                        .setUncaughtExceptionHandler((t, e) -> e.printStackTrace())
                        .build());
    }

    public static void runTask(Runnable runnable) {
        IO_INTENSIVE.submit(runnable);
    }

    public static <V> void runTask(Callable<V> callable) {
        IO_INTENSIVE.submit(callable);
    }

    public static <T> Future<T> runTask(Runnable runnable, T t) {
        return IO_INTENSIVE.submit(runnable, t);
    }

    public static void close() {
        IO_INTENSIVE.shutdownNow();
    }


}
