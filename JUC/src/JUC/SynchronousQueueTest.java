package JUC;

import java.util.concurrent.SynchronousQueue;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 19:12 2021/7/30
 * @E-mail: 15611562852@163.com
 */

public class SynchronousQueueTest {

    public static void main(String[] args) {

        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println("put a");
                synchronousQueue.put("a");

                System.out.println("put b");
                synchronousQueue.put("b");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread-1").start();

        new Thread(()->{
            // try {
            //     System.out.println("remove a");
            //     synchronousQueue.take();
            //
            //     System.out.println("remove b");
            //     synchronousQueue.take();
            //
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        }, "thread-2").start();


    }




}
