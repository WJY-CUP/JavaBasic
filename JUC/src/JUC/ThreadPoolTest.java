package JUC;

import java.util.concurrent.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 18:44 2021/7/26
 * @E-mail: 15611562852@163.com
 */

public class ThreadPoolTest {

    public static void main(String[] args) {

        // ExecutorService threadPool = Executors.newFixedThreadPool(3);
        // try {
        //     for (int i = 0; i < 10; i++) {
        //         threadPool.execute(() -> System.out.println(Thread.currentThread().getName()+" 办理业务"));
        //     }
        //     // TimeUnit.SECONDS.sleep(1);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // } finally {
        //     threadPool.shutdown();
        // }

        // The approach to improve parallel efficiency is setting maximumPoolSize as Runtime.getRuntime().availableProcessors()
        System.out.println("CPU core "+Runtime.getRuntime().availableProcessors());

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                Runtime.getRuntime().availableProcessors(),
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 20; i++) {
                threadPool.execute(()-> System.out.println(Thread.currentThread().getName()+" 执行任务"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
