package Observer;



/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 11:40 2022/6/8
 * @E-mail: 15611562852@163.com
 */

public class ObserverPatternDemo {
    public static void main(String[] args) {
        // Subject subject = new Subject();
        //
        // new OctalObserver(subject);
        // new BinaryObserver(subject);
        //
        // System.out.println("First state change: 15");
        // subject.setState(15);
        // System.out.println("Second state change: 10");
        // subject.setState(10);
        // System.out.println(Integer.MAX_VALUE*2);
        // System.out.println(Integer.MIN_VALUE*2);
        // int i = 0xFFFFFFF1;
        // int j = ~i;
        // System.out.println(j);
        // System.out.println(cal());

        // String s1 = new String("hello");
        // String s2 = new String("hello");
        // char[] c = {'h','e','l','l','o'};
        // System.out.println(s1.equals(s2));
        // System.out.println(s2.equals(c));
        // System.out.println(s2 == s1);
        // System.out.println(s2.equals(new String("hello")));
        // System.out.println(s2==c);

        for (int i = 1, count = 0; i < 100; i++) {
            if (i % 3 == 0) {
                System.out.println(i);
                if (++count >= 10) {
                    break;
                }
            }
        }


    }



}


