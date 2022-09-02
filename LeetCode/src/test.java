import java.math.BigDecimal;
import java.util.*;
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

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine());
        System.out.println(num);
        List<String> searchList;
        List<String> stopList;
        Map<String, Integer> map = null;
        for (int i = 0; i < num; i++) {
            searchList = Arrays.asList(scanner.nextLine().split(" "));
            searchList.remove(0);
            stopList = Arrays.asList(scanner.nextLine().split(" "));
            stopList.remove(0);

            for (int j = 0; j < searchList.size(); j++) {
                String s = searchList.get(j);
                for (int k = 0; k < stopList.size(); k++) {
                    boolean match = false;
                    String t = stopList.get(k);
                    if (s.length() == t.length()) {
                        // 遍历当前单词
                        for (int l = 0; l < s.length(); l++) {
                            if (t.charAt(l) != '?') {
                                if (s.charAt(l) != t.charAt(l)) {
                                    break;
                                } else {
                                    if (l == s.length()-1) {
                                        match = true;
                                    }
                                }
                            }
                        }
                    }
                    // 如果当前单词与stopList任何一个匹配
                    if (match) {
                        searchList.remove(j);
                        break;
                    }
                }
            }
            map = new HashMap<>();
            for (String s : searchList) {
                map.put(s, map.getOrDefault(s, 0) + 1);
            }
            Collection<Integer> values = map.values();
            System.out.println(values.stream().max(Integer::compareTo).get());
            Integer min = Collections.min(map.values());

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


    public static void main(String[] args) {
        String a = "bbb";
        String b = "(b)*";
        System.out.println(a.matches(b));

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


class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            char[] array = s.toCharArray();
            if (!(array[0] >= '1' && array[0] <= '9')) {
                System.out.println(-1);
            } else {
                for (int i = 0; i < array.length; ) {
                    int num=0;
                    while (i < array.length && array[i] != '.') {
                        char c = array[i];
                        num = num * 10 + c - '0';
                        i++;
                    }
                }
            }
        }
    }

    // public static void main(String[] args) {
    //     System.out.println(Long.toString(10, 2));
    //     System.out.println(Long.parseLong("1010",2));
    // }

}

// 神策数据笔试
class Q2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.nextLine();
        HashMap<Integer, Integer> map = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < num; i++) {
            int[] path = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            map.put(path[0], path[1]);
            set.add(path[0]);
            set.add(path[1]);
            // System.out.println(map.get(path[0]));
        }
        int[] begin = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        while (true){
            if (!set.contains(begin[0]) || !set.contains(begin[1])) {
                return;
            }
            Integer end0 = map.get(begin[0]);
            Integer end1 = map.get(begin[1]);
            if (end0 == null || end1 == null) {
                System.out.println(begin[0]);
            }
            if (end0.equals(end1)) {
                System.out.println(end0);
                return;
            }
            if (begin[0] == begin[1]) {
                System.out.println(begin[0]);
                return;
            }
            if (end0.equals(begin[1])) {
                System.out.println(end0);
                return;
            }
            if (end1.equals(begin[0])) {
                System.out.println(end1);
                return;
            }
            begin[0] = end0;
            begin[1] = end1;
        }

    }



}


class Q3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int[] price = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int ans = 0;
        for (int i = 0; i < price.length; i++) {
            for (int j = i+1; j < price.length; j++) {
                ans = Math.max(ans, price[i] + price[j] - (j - i));
            }
        }
        System.out.println(ans);
    }
}


class JDQ1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] num = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        String s = scanner.nextLine();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num[1]; i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z') {
                sb.append((char) (c - 32));
            } else {
                sb.append(c);
            }
        }
        for (int i = num[1]; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                sb.append((char) (c + 32));
            } else {
                sb.append(c);
            }
        }
        System.out.println(sb.toString());

    }

}

class JDQ3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (n <= 5) {
            System.out.println(0);
        }
        int result = 0;
        for (int i = 6; i <= n; i++) {
            result += B(i);
        }
        int mod = (int) 1e9 + 7;
        System.out.println(result%mod);
    }

    public static int B(int n) {
        int zheng = n / 3;
        int result = 0;
        for (int i = 2; i <= zheng; i++) {
            result += C(n - 3 * i + i, i);
        }
        return result;
    }

    public static int C(int n, int m) {
        int son=1;
        int mom=1;
        m = Math.min(m, (n - m));
        for (int i = m; i > 0; i--) {
            son*=n;
            mom*=i;
            n--;
        }
        return son / mom;
    }
}

class KunlunwanweiQ1 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param s string字符串
     * @return int整型
     */
    public int longestValidParentheses (String s) {
        // write code here
        int n = s.length();
        int[] dp = new int[n];
        int max_len = 0;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    if (i < 2) {
                        dp[i] = 2;
                    } else {
                        dp[i] = dp[i - 2] + 2;
                    }
                } else {
                    if (dp[i - 1] > 0) {
                        int left = i-dp[i-1]-1;
                        if (left >= 0 && s.charAt(left) == '(') {
                            dp[i] = dp[i - 1] + 2;
                            if (left - 1 > 0) {
                                dp[i] = dp[i] + dp[left - 1];
                            }
                        }
                    }
                }
            }
            max_len = Math.max(max_len, dp[i]);
        }
        return max_len;
    }
}


class TeslaQ1 {
    public boolean solution(int[] A) {
        // write your code in Java SE 8
        int length = A.length;
        if (length % 2 == 1) {
            return false;
        }
        Arrays.sort(A);
        for (int i = 0; i < A.length; i=i+2) {
            if (A[i] != A[i + 1]) {
                return false;
            }
        }
        return true;
    }
}

// 1578
class TeslaQ2 {
    public int solution(String S, int[] C) {
        // write your code in Java SE 8
        int res = 0;
        char[] array = S.toCharArray();
        for (int i = 0; i < array.length-1; i++) {
            if (array[i] == array[i + 1]) {
                res += Math.min(C[i], C[i + 1]);
                if (C[i] >= C[i + 1]) {
                    C[i + 1] = C[i];
                }
            }
        }
        return res;
    }

}




