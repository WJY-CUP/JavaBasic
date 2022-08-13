package JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 9:37 2021/7/23
 * @E-mail: 15611562852@163.com
 */

public class ProducerConsumer {
    public static void main(String[] args) {
        // product product = new product();
        product1 product = new product1();

        // 使用Runnable创建线程
        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                product.produce();
            }
        }, "生产者1").start();
        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                product.produce();
            }
        }, "生产者2").start();
        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                product.consume();
            }
        }, "消费者1").start();
        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                product.consume();
            }
        }, "消费者2").start();

        // 使用Callable创建线程
        // new Thread(new FutureTask(new Callable<Integer>() {
        //     @Override
        //     public Integer call() {
        //         while (true) {
        //             // Thread.sleep(500);
        //             product.consume();
        //             return product.getP();
        //         }
        //     }
        // }),"消费者3").start();



    }
}

// 1.synchronized-wait()-notify() 解决生产者-消费者问题
class product {

    // 默认最多一个产品
    private int p = 0;

    public product() { }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    /*
            在if块中使用wait方法，是非常危险的，因为一旦线程被唤醒，并得到锁，就不会再判断if条件，
            而执行if语句块外的代码，所以建议，凡是先要做条件判断，再wait的地方，都使用while循环来做。
         */
    public synchronized void produce() {
        /*
        此处如果使用if，多个生产者会出现产品个数为负数的情况（虚假唤醒:消费者消费了一个产品，notifyAll唤醒了所有生产者，但是只能一个生产者获得锁）
        p!=0(p==1)，生产者wait释放锁进入阻塞状态，被唤醒后，if判断已经被执行完，即使p == 1，也会p++，导致 p > 1
            解决方案：
                （1）使用if-else,不使用单if
                （2）if 替换为 while （官方推荐）
         */
        // if (p != 0) {
        while (p != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "生产第" + (++p) + "个产品");
        notifyAll();
    }

    public synchronized void consume() {
        /*
        此处如果使用if，多个消费者会出现产品个数为负数的情况（虚假唤醒:生产者生产了一个产品，notifyAll唤醒了所有消费者，但是只能一个消费者获得锁）
        p<=0，wait释放锁进入阻塞状态，被唤醒后，if判断已经被执行完，即使p<=0，也会p--，导致p < 0
            解决方案：
                （1）使用if-else,不使用单if
                （2）if 替换为 while（官方推荐）
         */
        while (p == 0) {
            // if (p <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "消费第" + (p--) + "个产品");
        notifyAll();
    }
}

// 2.ReentrantLock-await()-signal() 解决生产者-消费者问题
class product1 {

    // 默认最多一个产品
    private int p = 0;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    private Lock lock = new ReentrantLock();
    // Condition 可精准通知与唤醒线程
    private Condition condition = lock.newCondition();

    public product1() {

    }

    public void produce() {
        lock.lock();
        try {
            while (p != 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "生产第" + (++p) + "个产品");
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume() {
        lock.lock();
        try {
            while (p == 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "消费第" + (p--) + "个产品");
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}

class RunnableDemo {
    public static void main(String[] args) {
        //4.创建Runnable的子类对象
        MyRunnale mr=new MyRunnale();
        //5.将子类对象当做参数传递给Thread的构造函数,并开启线程
        //MyRunnale taget=mr; 多态
        // 通过Runnable实现类调用run()只是调用方法，并没有开启新的线程
        // mr.run();
        new Thread(mr).start();
        for (int i = 0; i < 10; i++) {
            System.out.println("我是主线程"+i);
        }
    }
}

//1.定义一个类实现Runnable
class MyRunnale implements Runnable{
    //2.重写run方法
    @Override
    public void run() {
        //3.将要执行的代码写在run方法中
        for (int i = 0; i < 10; i++) {
            System.out.println("我是线程"+i);
        }
    }
}
