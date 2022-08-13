package JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 20:44 2021/7/22
 * @E-mail: 15611562852@163.com
 */


// 使用synchronized方式
class saleTicket1 {

    private int ticket = 100;
    public synchronized void sale() {
        if (ticket > 0) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":开始卖第" + (ticket--) + "张票");
        }
    }
}

// 使用lock方式
class saleTicket2 {
    private int ticket = 100;
    private ReentrantLock lock = new ReentrantLock(false);


    public void sale() {
        lock.lock();
        try {
            if (ticket > 0) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":开始卖第" + (ticket--) + "张票");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTicket {
    public static void main(String[] args) {
        // saleTicket1 ticket = new saleTicket1();
        saleTicket2 ticket = new saleTicket2();
        new Thread(() -> {for (int i = 0; i < 100; i++) {ticket.sale(); } }, "线程1").start();
        new Thread(() -> {for (int i = 0; i < 100; i++) {ticket.sale(); } }, "线程2").start();
        new Thread(() -> {for (int i = 0; i < 100; i++) {ticket.sale(); } }, "线程3").start();
    }
}

/**
 * Description:
 *  可重入锁(也叫做递归锁)
 *  指的是同一先生外层函数获得锁后,内层敌对函数任然能获取该锁的代码
 *  在同一线程外外层方法获取锁的时候,在进入内层方法会自动获取锁
 *  *  也就是说,线程可以进入任何一个它已经标记的锁所同步的代码块
 *  **/

// 代码验证synchronized和ReentrantLock是可重入锁
class Phone{

    private ReentrantLock lock = new ReentrantLock();

    public synchronized void sendSms() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\tsendSms");
        // 嵌套同步方法
        sendEmail();
    }
    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\tsendEmail");
    }
    public void sendSms1() throws Exception{
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\tsendSms");
            // 嵌套同步方法
            sendEmail1();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void sendEmail1() throws Exception{
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\tsendEmail");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Phone phone = new Phone();

        // synchronized is ReentrantLock
        new Thread(()->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Thread-1").start();
        new Thread(()->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Thread-2").start();

        // test ReentrantLock
        new Thread(()->{
            try {
                phone.sendSms1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Thread-3").start();
        new Thread(()->{
            try {
                phone.sendSms1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Thread-4").start();



    }

}

class test1 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            System.out.println("thread 1 is waiting");
            try {
                condition.await();
                System.out.println("thread 1 is wake up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("thread 2 is running");
                Thread.sleep(3000);
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();
    }
}

