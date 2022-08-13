package JUC;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:06 2021/7/29
 * @E-mail: 15611562852@163.com
 */

//自旋锁
public class AtomicReferenceThreadDemo {

    static AtomicReference<Thread> atomicReference=new AtomicReference<>();
    static Thread thread;

    public static void lock() {
        thread=Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+" lock-start:"+thread);
        while(!atomicReference.compareAndSet(null,thread));
        System.out.println(Thread.currentThread().getName()+" lock-end:"+thread);
    }

    public static void unlock() {
        System.out.println(Thread.currentThread().getName()+" unlock-start:"+thread);
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+" unlock-end:"+thread);
    }

    public static void main(String[] args) {
        new Thread(()->{
            // expect:null(符合)->将共享变量thread修改为"A"
            AtomicReferenceThreadDemo.lock();
            // try {
            //     TimeUnit.SECONDS.sleep(3);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            AtomicReferenceThreadDemo.unlock();
        },"A").start();

        new Thread(()->{
            // B->lock() expect:null,update:B, but thread=A, then block
            AtomicReferenceThreadDemo.lock();
            AtomicReferenceThreadDemo.unlock();
        },"B").start();
    }
}




