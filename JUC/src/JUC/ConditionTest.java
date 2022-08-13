package JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 15:36 2021/7/23
 * @E-mail: 15611562852@163.com
 */

/**
 * @Description: A B C依次输出，使用Condition.await,Condition.signal()
 * @param:
 * @return:
 */
public class ConditionTest {

    public static void main(String[] args) {
        Data data = new Data();
        new Thread(()->{
            for (int i = 0; i < 3; i++) {
                data.printA();
            }
        }, "线程A").start();
        new Thread(()->{
            for (int i = 0; i < 3; i++) {
                data.printB();
            }
        }, "线程B").start();
        new Thread(()->{
            for (int i = 0; i < 3; i++) {
                data.printC();
            }
        }, "线程C").start();
    }


}

class Data {

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int number = 1;

    public void printA() {
        lock.lock();
        try {
            while (number != 1) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "--> AAA");
            number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (number != 2) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "--> BBB");
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (number != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "--> CCC");
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }



}


