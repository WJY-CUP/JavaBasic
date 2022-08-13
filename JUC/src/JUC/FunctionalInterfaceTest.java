package JUC;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 20:36 2021/7/26
 * @E-mail: 15611562852@163.com
 */

public class FunctionalInterfaceTest {

    public static void main(String[] args) {

        Supplier<String> supplier = () -> "Hello";
        System.out.println(supplier.get());
        Consumer<String> consumer = System.out::println;
        consumer.accept("WJY");
        Function<Integer, Integer> function = o -> o+1;
        System.out.println(function.apply(1));


    }


}
