package JUC;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:26 2021/7/25
 * @E-mail: 15611562852@163.com
 */

public class SemaphoreTest {

    public static void main(String[] args) {

        // 5个停车位
        Semaphore semaphore = new Semaphore(5);
        // 6辆车
        for (int i = 1; i <= 20 ; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+" 抢到车位");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName()+" 离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }


    }


}
