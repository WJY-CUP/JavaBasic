import com.sun.org.apache.xerces.internal.xs.ItemPSVI;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 11:07 2022/8/15
 * @E-mail: 15611562852@163.com
 */

public class test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.nextLine();
        String[] a = scanner.nextLine().split(" ");
        String[] b = scanner.nextLine().split(" ");
        int scoreA = 0, scoreB = 0;
        for (int i = 0; i < num; i++) {
            if (a[i].equals(b[i])) {
                continue;
            }
            if (a[i].equals("Rock")) {
                if (b[i].equals("Scissor")) {
                    scoreA++;
                } else {
                    scoreB++;
                }
            }
            if (a[i].equals("Scissor")) {
                if (b[i].equals("Rock")) {
                    scoreB++;
                } else {
                    scoreA++;
                }
            }
            if (a[i].equals("Paper")) {
                if (b[i].equals("Rock")) {
                    scoreA++;
                } else {
                    scoreB++;
                }
            }
        }
        System.out.print(scoreA + " " + scoreB);
    //    Rock Rock Rock
    //    Rock Paper Scissor




    }


}

class test2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.nextLine();
        ArrayList<String> list = new ArrayList<>();
        String[] temp;
        for (int i = 0; i < num; i++) {
            temp = scanner.nextLine().split(" ");
            Arrays.sort(temp);
            list.addAll(Arrays.asList(temp));
        }
        List<String> newList = list.stream().distinct().collect(Collectors.toList());

        int length = 0;
        for (String s : newList) {
            if (length + s.length() + 1 > 50) {
                System.out.println();
                System.out.print(s);
                length = s.length();
            } else {
                if (length != 0) {
                    System.out.print(" ");
                }
                System.out.print(s);
                length += (s.length()+1);
            }
        }
    }
}

class streamTest {

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 输出所有非空字符串
        // strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList()).forEach(System.out::println);
        // 输出空字符串的数量
        // System.out.println(strings.parallelStream().filter(String::isEmpty).count());
        // 输出以", "为分隔符合并非空字符串
        // System.out.println(strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", ")));
        // 输出字符串长度为 3 的数量
        System.out.println(strings.stream().filter(string -> string.length() == 3).count());

        //  输出了10个随机数
        Random random = new Random();
        // random.ints().limit(10).forEach(System.out::println);

        List<Integer> numbers = Arrays.asList(3, 10, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        // numbers.stream().distinct().map(i -> i*i).collect(Collectors.toList()).forEach(System.out::println);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
        System.out.println("数量 : " + stats.getCount());

        int[] nums = {1, 2, 3, 4};
        IntStream stream = Arrays.stream(nums);
        Stream<int[]> stream1 = Stream.of(nums);


    }

}


class Solution1 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 求取指定长度序列的和
     * @param n int整型 序列长度
     * @return float浮点型
     */
    public float seqSum (int n) {


        // write code here
        float sum = 0;
        float a=1, b=2;
        float temp;
        for (int i = 0; i < n; i++) {
            sum+=a/b;
            temp=b;
            b=a+b;
            a=temp;
        }

        // return Float.parseFloat(String.format("%.2f", sum));


        BigDecimal decimal = new BigDecimal(sum);
        return decimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

    }
}


class Solution3 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 验证指令的正确性
     * @param signal string字符串 待验证的指令
     * @return bool布尔型
     */
    public boolean signalVerify (String signal) {
        // write code here
        if (signal == null || signal.length() == 0) {
            return false;
        }
        char[] array = signal.toCharArray();
        if (array[0] < 'a' || array[0] > 'z' || array[2] == ' ' || array[1] != '=') {
            return false;
        }
        for (int i = 3; i < array.length; i++) {
            if (array[i] < ' ' || (array[i] > ' ' && array[i] < '0') || (array[i] > '9' && array[i] < 'a') || array[i] > 'z') {
                return false;
            }
        }
        return true;
    }
}




/*
 * public class Point {
 *   int x;
 *   int y;
 *   public Point(int x, int y) {
 *     this.x = x;
 *     this.y = y;
 *   }
 * }
 */

final class Point {
    int x,y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

class test1 {
    public static void main(String[] args) {
        Point point = new Point(1, 2);
        point.setX(2);
        System.out.println(point.getX());
    }
}

class Solution2 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param maze int整型二维数组 迷宫数据 固定为4x4的二维数组，0表示路，可走；1 表示墙，不可通过； 8 表示礼物，是我们的目标
     * @return Point类ArrayList
     */
    public ArrayList<Point> winMazeGift (int[][] maze) {
        // write code here
        int x = 0, y = 0;
        List<List<Point>> res = new ArrayList<>();
        List<Point> path = new ArrayList<>();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 8) {
                    x = i;
                    y = j;
                    break;
                }
            }

        }

        dfs(maze, x, y, path, res);

        int min = Integer.MAX_VALUE;
        List<Point> shortPath =new ArrayList<>();
        for (List<Point> points : res) {
            if (points.size() < min) {
                min = points.size();
                shortPath = points;
            }
        }

        ArrayList<Point> ans = new ArrayList<>();
        for (int i = shortPath.size() - 1; i >= 0; i--) {
            ans.add(shortPath.get(i));
        }
        return ans;
    }

    private static final int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    private void dfs(int[][] maze, int i, int j, List<Point> path, List<List<Point>> res) {
        if (isBorder(i, j, maze)) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int[] dir : dirs) {
            int newX = i + dir[0];
            int newY = j + dir[1];
            if (inArea(newX, newY, maze) && maze[newX][newY] == 0) {
                path.add(new Point(newX, newY));
                dfs(maze, newX, newY, path, res);
            }
        }
    }

    private boolean inArea(int x, int y, int[][] maze) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length;
    }
    private boolean isBorder(int x, int y, int[][] maze) {
        return x < 1 || x > maze.length - 1 || y < 1 || y > maze[0].length - 1;
    }
}
















