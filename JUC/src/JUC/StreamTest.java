package JUC;


import org.junit.Test;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 16:29 2021/7/27
 * @E-mail: 15611562852@163.com
 */

public class StreamTest {

    public static ArrayList<Apple> list = new ArrayList<>();

    static {
        list.add(new Apple(1, "red", 100, "BJ"));
        list.add(new Apple(2, "green", 200, "TJ"));
        list.add(new Apple(3, "blue", 300, "SH"));
        list.add(new Apple(4, "white", 100, "BJ"));
        list.add(new Apple(5, "red", 200, "SH"));
        list.add(new Apple(6, "green", 300, "GZ"));
    }

    public static void main(String[] args) {

        Predicate<Apple> predicate = apple -> apple.testColor();

        List<Apple> red = list.stream()
                // （1）lambda表达式
                // .filter(a -> a.getColor().equals("red"))
                // （2）方法引用
                // .filter(Apple::testColor)
                .filter(predicate)
                .filter(a -> a.getWeight() >= 200)
                .collect(Collectors.toList());
        System.out.println(red);

    }


    // get the average weight of apples of each color by iteration
    @Test
    public void getAverageByColor1() {
        HashMap<String, List<Apple>> map = new HashMap<>();
        for (Apple apple : list) {
            List<Apple> colorLlist = map.computeIfAbsent(apple.getColor(), key -> new ArrayList<>());
            colorLlist.add(apple);
        }
        for (Map.Entry<String, List<Apple>> entry : map.entrySet()) {
            int sum = 0;
            for (Apple apple : entry.getValue()) {
                sum += apple.getWeight();
            }
            System.out.println(String.format("color %s average weight: %s", entry.getKey(), sum / entry.getValue().size()));
        }
    }

    // get the average weight of apples of each color by Stream
    @Test
    public void getAverageByColor2() {
        list.stream().
                collect(Collectors.groupingBy(apple -> apple.getColor(),
                        Collectors.averagingInt(apple -> apple.getWeight())))
                .forEach((k,v)->System.out.println(k+":"+v));
    }

    // test put,compute,computeIfAbsent,putIfAbsent
    @Test
    public void test03() {
        HashMap<Integer, Character> map = new HashMap<>();
        System.out.println(map.put(1, 'a'));
        System.out.println(map.put(1, 'b'));
        System.out.println(map.compute(1,(key, value)->(char)(value+1)));
        // System.out.println(map.computeIfAbsent(1,(key,value)->(char)(value+1)));
        System.out.println(map.putIfAbsent(3, 'e'));
        System.out.println(map.putIfAbsent(3, 'f'));
    }

    /**
        1.Stream can't save data
        2.Stream can't modify data source
        3.Stream is not reusable
     */
    @Test
    public void test04() {
        new Thread((myInterface) () -> System.out.println("WJY"));
        Consumer consumer = System.out::println;

        // three methods for creating Stream
        IntStream stream = Arrays.stream(new int[]{1, 2, 3, 4});
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4);
        Stream<int[]> stream2 = Stream.of(new int[]{5, 6, 7, 8});
        System.out.println(stream.toString());
        System.out.println(stream1.toString());
        System.out.println(stream2.toString());

        System.out.println("--------------------------");

        // Stream is not reusable
        Stream<Apple> appleStream = list.stream();
        Stream<Apple> appleStream1 = appleStream.filter(Apple::testColor);

        // error:java.lang.IllegalStateException: stream has already been operated upon or closed
        // Stream<Apple> appleStream2 = appleStream.filter(Apple::testWeight);

        Stream<Apple> appleStream2 = appleStream1.filter(Apple::testWeight);
        System.out.println(appleStream.toString());
        System.out.println(appleStream1.toString());
        System.out.println(appleStream2.toString());

        list.forEach(System.out::println);

        System.out.println("--------------------------");


        list.stream()
                .filter(a -> a.getColor().equals("red") || a.getColor().equals("green"))
                .map(Apple::getColor)
                .distinct()
                .forEach(System.out::println);

        AtomicInteger i = new AtomicInteger();
        System.out.println(i.addAndGet(2));
        System.out.println(i.getAndAdd(2));


    }

    @Test
    public void test05() {
        // 获取对应的平方数
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.parallelStream().map( i -> i*i).distinct().collect(Collectors.toList());
        System.out.println(squaresList);




    }

    @FunctionalInterface
    private interface myInterface extends Runnable{
        default void a() {
            System.out.println("default");
        }

        // void b();

        String toString();
        boolean equals(Object object);
        Predicate<String> p = s -> s.equals("red");
    }



}


class Apple{

    private int id;
    private String color;
    private int weight;
    private String location;

    public boolean testColor() {
        return color.equals("red");
    }

    public boolean testWeight() {
        return weight >= 200;
    }



    @Override
    public String toString() {
        return "Apple{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", weight=" + weight +
                ", location='" + location + '\'' +
                '}';
    }

    public Apple(int id, String color, int weight, String location) {
        this.id = id;
        this.color = color;
        this.weight = weight;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}