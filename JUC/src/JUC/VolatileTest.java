package JUC;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:29 2021/8/1
 * @E-mail: 15611562852@163.com
 */

public class VolatileTest {

    private static volatile int num2 = 0;
    private static int num1 = 0;

    @Test
    // 1.volatile 保证可见性，如果不加，Thread-1检测不到num已被修改,会一直循环
    public void test01() {

        new Thread(()->
        {
            while (num2 == 0) {}
            if (num2 == 1) {
                System.out.println("num已被修改为1，循环结束");
            }
        }, "Thread-1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num2 = 1;
    }

    @Test
    // 2.volatile 不保证原子性，可用lock,synchronized（但性能差），最好用AtomicInteger
    public void test02() {

        AtomicInteger num3 = new AtomicInteger();

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    // AtomicInteger 实现原子性自增
                    num3.getAndIncrement();
                    // volatile 不保证原子性自增
                    num2++;
                    // 普通自增 不保证原子性自增
                    num1++;
                }
            }).start();
        }

        // try {
        //     TimeUnit.SECONDS.sleep(1);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // 上面的100个线程执行完后，只剩下main,gc两个线程
        while (Thread.activeCount() > 2) {
            // main线程礼让，让上面的100个自增运行
            Thread.yield();
        }

        System.out.println("AtomicInteger num = " + num3);
        System.out.println("volatile num = " + num2);
        System.out.println("num = " + num1);
    }

    @Test
    // 3.volatile禁止指令重排
    public void test03() {
        int x = 0, y = 0;

        for (int i = 0; i < 10; i++) {
            num2++;
            x = num2;
            num1++;
            y = num1;
        }
        System.out.println("x = " + x);
        System.out.println("y = " + y);
    }



}
