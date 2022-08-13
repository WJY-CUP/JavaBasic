package JUC;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 11:00 2021/7/24
 * @E-mail: 15611562852@163.com
 */

public class SafeCollection {
    public static void main(String[] args) {
        /*
            (1) ArrayList:线程不安全
            (2) Vector：线程安全但过时
            (3) Collections.synchronizedList
            (4)

         */
        // ArrayList<String> stringArrayList = new ArrayList<>();
        // Vector<String> list = new Vector<>();
        // List<String> list = Collections.synchronizedList(stringArrayList);
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                // ConcurrentModificationException
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

    }


}
