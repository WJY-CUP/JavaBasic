import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 18:42 2021/10/24
 * @E-mail: 15611562852@163.com
 */

class practice {

    private int[][] grid;
    private int height;
    private int width;
    boolean[][] visited;
    private static final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int solution02(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[n]) {
                res++;
                dfs(n, isConnected, visited);
            }
        }
        return res;
    }




    private void dfs(int i, int[][] isConnected, boolean[] visited) {
        visited[i] = true;
        for (int j = 0; j < isConnected.length; j++) {
            if (!visited[j] && isConnected[i][j] == 1) {
                visited[j] = true;
                dfs(j, isConnected, visited);
            }
        }
    }



    private boolean inArea(int i, int j) {
        return i >= 0 && i < this.height && j >= 0 && j < this.width ;
    }
}

class UnionFind {

    private Map<Integer, Integer> father;

    private int num = 0;

    public UnionFind() {
        this.father = new HashMap<>();
    }

    public void add(int x) {
        if (!father.containsKey(x)) {
            father.put(x, null);
            num++;
        }
    }

    public void merge(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            father.put(rootX, rootY);
            num--;
        }
    }

    public int find(int x) {
        int root = x;
        while (father.get(root) != null) {
            root = father.get(root);
        }
        while (x != root) {
            int ori_father = father.get(x);
            father.put(x, root);
            x = ori_father;
        }
        return root;
    }

    public boolean isConnected(int x,int y) {
        return find(x) == find(y);
    }

    public int getNumOfSets() {
        return num;
    }

    public int solution01(int[][] isConnected) {
        UnionFind unionFind = new UnionFind();
        for (int i = 0; i < isConnected.length; i++) {
            unionFind.add(i);
            for (int j = 0; j < i; j++) {
                if (isConnected[i][j] == 1) {
                    unionFind.merge(i, j);
                }
            }
        }
        return unionFind.getNumOfSets();
    }

    public int solution02(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                res++;
                dfs(i, isConnected, visited);
            }
        }
        return res;
    }

    private void dfs(int i, int[][] isConnected, boolean[] visited) {
        visited[i] = true;
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                dfs(j, isConnected, visited);
            }
        }
    }

    public int solution03(int[][] isConnected) {
        int n = isConnected.length;
        int res = 0;
        boolean[] visited = new boolean[n];
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                res++;
                deque.add(i);
                visited[i] = true;
                while (!deque.isEmpty()) {
                    Integer a = deque.poll();
                    for (int j = 0; j < n; j++) {
                        if (isConnected[a][j] == 1 && !visited[j]) {
                            visited[j] = true;
                            deque.add(j);
                        }
                    }
                }
            }
        }
        return res;


    }




}

class exist {

    private static final int[][] DIRECTIONS = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    private int height;
    private int width;
    private int len;
    private char[][] board;
    private char[] charArray;
    private boolean[][] visited;

    public boolean exist(char[][] board, String word) {
        height = board.length;
        if (height == 0) {
            return false;
        }
        width = board[0].length;
        this.len = word.length();
        this.board = board;
        this.charArray = word.toCharArray();
        this.visited = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (dfs(i, j, 0)) {
                    return true;
                }
            }
        }
        return false;

    }

    private boolean dfs(int x, int y, int begin) {
        if (begin == len - 1) {
            return board[x][y] == charArray[begin];
        }
        if (board[x][y] == charArray[begin]) {
            visited[x][y] = true;
            for (int[] direction : DIRECTIONS) {
                int newX = x + direction[0];
                int newY = y + direction[1];
                if (inArea(newX, newY) && !visited[newX][newY]) {
                    if (dfs(newX, newY, begin + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < height && j >= 0 && j < width;
    }

}

/**
 * 伪共享演示
 *
 */
class FalseSharingDemo {

    public static void main(String[] args) throws InterruptedException {
        // testPointer(new Pointer());
        // System.out.println(0x3f3f3f3f);
        System.out.println(1.2/0);
        System.out.println(-7%2);
        System.out.println(Math.sqrt(4));
    }


    private static void testPointer(Pointer pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.a++;
            }
        }, "A");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.b++;
            }
        },"B");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(System.currentTimeMillis() - start);
        System.out.println(pointer.a + "@" + Thread.currentThread().getName());
        System.out.println(pointer.b + "@" + Thread.currentThread().getName());
    }
}

class Pointer {
    // 在一个缓存行中，如果有一个线程在读取a时，会顺带把b带出
    volatile long a;  // 需要volatile，保证线程间可见并避免重排序
    // 放开下面这行，解决伪共享的问题，提高了性能
    long p1, p2, p3, p4, p5, p6, p7;
    volatile long b;   // 需要volatile，保证线程间可见并避免重排序
}




class minCostClimbingStairs1 {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(3);
        for (int i = 1; i <=6; i++) {
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+"\t抢占了车位");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }


}


class testqw {
    public static void main(String[] args) {
        // Scanner in = new Scanner(System.in);
        // while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
        //     int a = in.nextInt();
        //     int b = in.nextInt();
        //     System.out.println(a + b);
        // }
        Scanner scanner =  new Scanner(System.in);
        scanner.nextInt();
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] strs = scanner.nextLine().split(" ");
            Arrays.sort(strs);
            String a = "";
            for(int i=0;i<strs.length;i++) {
                a = a + strs[i] + " ";
            }
            System.out.println(a.substring(0, a.length()-1));
        }


        //
        // Scanner sc = new Scanner(System.in);
        // int n = sc.nextInt();
        // int ans = 0, x;
        // for(int i = 0; i < n; i++){
        //     for(int j = 0; j < n; j++){
        //         x = sc.nextInt();
        //         ans += x;
        //     }
        // }
        // System.out.println(ans);
        // ArrayList<String> list = new ArrayList<>();
        // list.add("aaa");
        // list.add("bbb");
        // list.add("ccc");
        // System.out.println(list.toString());
        //

    }


}













