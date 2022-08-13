package JUC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author: Wan Jiangyuan
 *
 * @Description: 测试java.util.current.Atomic包中的类
 * @Date: Created in 14:42 2021/8/7
 * @E-mail: 15611562852@163.com
 */

class AtomicIntegerTest {

    AtomicInteger atomicInteger=new AtomicInteger(0);

    public void addPlusPlus(){
        atomicInteger.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(10);
        AtomicIntegerTest atomic = new AtomicIntegerTest();
        // 10个线程进行循环100次调用addPlusPlus的操作,最终结果是10*100=1000
        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                try{
                    for (int j = 1; j <= 100; j++) {
                        atomic.addPlusPlus();
                    }
                }finally {
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }
        //(1). 如果不加上下面的停顿3秒的时间,会导致还没有进行i++ 1000次main线程就已经结束了
        // try { TimeUnit.SECONDS.sleep(3);  } catch (InterruptedException e) {e.printStackTrace();}
        //(2). 使用CountDownLatch去解决等待时间的问题
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t"+"获取到的result:"+atomic.atomicInteger.get());
    }
}

class AtomicIntegerArrayDemo {

    public static void main(String[] args) {

        //(1). 创建一个新的AtomicIntegerArray，其长度与从给定数组复制的所有元素相同。
        // int[] arr = {1, 2, 3, 4, 5};
        // AtomicIntegerArray array = new AtomicIntegerArray(arr);

        //(2). 创建给定长度的新AtomicIntegerArray,所有元素最初为零。
        AtomicIntegerArray array = new AtomicIntegerArray(5);

        // for (int i = 0; i < arr.length; i++) {
        //     System.out.print(arr[i]);
        // }
        array.getAndSet(0,1111);
        System.out.println("============");
        System.out.println("将数字中位置为0位置上的元素改为:"+array.get(0));
        System.out.println("数组位置为1位置上的旧值是:"+array.get(1));
        System.out.println("将数组位置为1位置上的数字进行加1的处理");
        array.getAndIncrement(1);
        System.out.println("数组位置为1位置上的新值是:"+array.get(1));
    }

}

class AtomicIntegerFieldUpdaterDemo {

    class DemoData{
        public volatile int value1 = 1;
        volatile int value2 = 2;
        protected volatile int value3 = 3;
        private volatile int value4 = 4;
    }

    AtomicIntegerFieldUpdater<DemoData> getUpdater(String fieldName) {
        return AtomicIntegerFieldUpdater.newUpdater(DemoData.class, fieldName);
    }

    void doit() {
        DemoData data = new DemoData();
        System.out.println("1 ==> "+getUpdater("value1").getAndSet(data, 10));
        System.out.println("2 ==> "+getUpdater("value2").incrementAndGet(data));
        System.out.println("3 ==> "+getUpdater("value3").decrementAndGet(data));
        System.out.println("true ==> "+getUpdater("value4").compareAndSet(data, 4, 5));
    }

    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterDemo demo = new AtomicIntegerFieldUpdaterDemo();
        demo.doit();
        Thread thread = new Thread();
    }
}