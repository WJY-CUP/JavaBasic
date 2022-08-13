package JUC;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:35 2021/7/25
 * @E-mail: 15611562852@163.com
 */

public class ReadWriteLockTest {

    public static void main(String[] args) {
        MyMap map = new MyMap();
        char c = 'A';

        // 多线程写
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            new Thread(()->{
                map.put(finalI, (char) (c + finalI));
                map.get(finalI);
            }, ("线程"+ i)).start();
        }

        try {
            Thread.sleep(1000);
            System.out.println("------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 多线程读
        for (int i = 0; i < 5; i++) {
            int finalI1 = i;
            new Thread(()->{
                map.get(finalI1);
            }, ("线程"+ i)).start();
        }

        try {
            Thread.sleep(1000);
            System.out.println(map.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}

class MyMap {

    private HashMap<Integer, Character> map = new HashMap<Integer, Character>();
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 加写锁
    public void put(Integer key, char value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+" 开始写");
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+" 结束写");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 加读锁
    public void get(Object key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+" 开始读");
            Character s = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取内容：" + s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public String toString() {
        return "MyMap{" +
                "map=" + map +
                '}';
    }
}