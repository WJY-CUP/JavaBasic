package JUC;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Wan Jiangyuan
 * @Description: 单例模式
 * @Date: Created in 12:31 2021/8/1
 * @E-mail: 15611562852@163.com
 */

public class SingletonTest {


}

// 单例模式--饿汉式
class Hungry {

    private static final Hungry object = new Hungry();

    private Hungry() {
    }

    public static Hungry getInstance() {
        return object;
    }
}

// 单例模式--懒汉式
class Lazy {

    private volatile static Lazy object;

    private Lazy() {
        synchronized (Lazy.class) {
            if (object != null) {
                throw new RuntimeException("Do not use reflection to get new instance");
            }
        }
    }

    /**
    1.使用synchronized同步getInstance()效率低，可使用同步代码块
    2.从if (object == null)开始进行同步同样效率低，故将重要代码进行同步，推荐 Double Check Locking 双检查锁机制（推荐）
     */
    public static Lazy getInstance() {
        if (object == null) {
            // try {
            //     TimeUnit.SECONDS.sleep(1);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            synchronized (Lazy.class) {
                // 只在synchronized外加一层检测会造成线程不安全,推荐 Double Check Locking 双检查锁机制!
                if (object == null) {
                    object = new Lazy();
                }
            }
        }
        return object;
    }

    public static void main(String[] args)  {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                System.out.println(Lazy.getInstance().toString());
            }).start();
        }

    }

}