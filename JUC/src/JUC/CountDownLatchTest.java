package JUC;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:01 2021/7/25
 * @E-mail: 15611562852@163.com
 */

public class CountDownLatchTest {

    public static void main(String[] args) {

        // 方式一：CountDownLatch 设置计数器
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 1; i <= 5 ; i++) {
            new Thread(()->{
                // 计数器减一
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName()+" 执行完毕");
            }, String.valueOf(i)).start();
        }
        try {
            // 等待计数器归零，才能向下执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有线程执行完毕");
        System.out.println("main线程执行完毕");
        //
        // System.out.println("-----------------------------------------");

        // 方式二：CyclicBarrier 设置计数器
        // CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("所有计数线程执行完毕"));
        // for (int i = 1; i <= 5 ; i++) {
        //     new Thread(()->{
        //         System.out.println(Thread.currentThread().getName()+" 执行完毕");
        //         try {
        //             cyclicBarrier.await();
        //         } catch (InterruptedException | BrokenBarrierException e) {
        //             e.printStackTrace();
        //         }
        //     }, String.valueOf(i)).start();
        // }
        // System.out.println("main线程执行完毕");

    }


}
