import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 合并int数组 https://blog.csdn.net/m0_46202073/article/details/105192104?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-4-105192104-blog-107499924.pc_relevant_default&spm=1001.2101.3001.4242.3&utm_relevant_index=7
class MergeArray {

    // 方法一：System.arraycopy（原数组, 原数组复制起点， 目标数组， 目标数组放置起点， 复制个数）
    public static void test1() {
        int[] num1 = {1, 2, 3};
        int[] num2 = {4, 5, 6, 7};
        int[] res = new int[num1.length + num2.length];
        System.arraycopy(num1, 0, res, 0, num1.length);
        System.arraycopy(num2, 0, res, num1.length, num2.length);
        System.out.println(Arrays.toString(res));
    }

    // 方法二：System.arraycopy, Arrays.copyOf
    public static void test2() {
        int[] num1 = {1, 2, 3};
        int[] num2 = {4, 5, 6, 7};
        int[] res = Arrays.copyOf(num1, num1.length + num2.length);
        System.arraycopy(num2, 0, res, num1.length, num2.length);
        System.out.println(Arrays.toString(res));
    }



    public static void main(String[] args) {
        // test1();
        // test2();
    }
}


// List<Integer> int[] Integer[] 互转
class Convert {

    // int[] -–> List
    public static void test1() {
        int[] nums = new int[]{1, 2, 3};
        List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
        list.forEach(System.out::println); // 换行输出 1 2 3
        System.out.println(list); // [1, 2, 3]
    }

    // int[] --> Integer[]
    public static void test2() {
        int[] intArray = {1, 2, 3};
        Integer[] array = Arrays.stream(intArray).boxed().toArray(Integer[]::new);
        System.out.println(Arrays.toString(array));  // [1, 2, 3]
    }


    // List -–> Integer[]
    public static void test3() {
        List<Integer> list = Arrays.asList(1,2,3);
        Integer[] array = list.toArray(new Integer[0]);
        System.out.println(Arrays.toString(array)); // [1, 2, 3]
    }


    // List -–> int[]
    public static void test4() {
        List<Integer> list = Arrays.asList(1,2,3);
        int[] array = list.stream().mapToInt(Integer::valueOf).toArray();
        System.out.println(Arrays.toString(array)); // [1, 2, 3]
    }

    // Integer[] --> int[]
    public static void test5() {
        Integer[] array = new Integer[]{1, 2, 3};
        int[] intArray = Arrays.stream(array).mapToInt(Integer::valueOf).toArray();
        System.out.println(Arrays.toString(intArray));  // [1, 2, 3]
    }

    // Integer[] --> List
    public static void test6() {
        Integer[] array = new Integer[]{1, 2, 3};
        List<Integer> list = Arrays.asList(array);
        System.out.println(list);  // [1, 2, 3]

    }






    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
    }
}


