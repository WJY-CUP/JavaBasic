package Search;

import java.io.Serializable;
import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description: 深度优先搜索 DFS
 * @Date: Created in 14:46 2021/8/8
 * @E-mail: 15611562852@163.com
 */

// 695. 最大的岛屿面积 https://leetcode-cn.com/problems/max-area-of-island/
class maxAreaOfIsland {

    private int[][] grid;
    private int height;
    private int width;
    private static final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        this.height = grid.length;
        this.width = grid[0].length;
        this.grid = grid;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res = Math.max(res, dfs(i, j));
            }

        }
        return res;

    }

    private int dfs(int i, int j) {
        // 如果越界或者为水域（值为0）
        if (!inArea(i, j) || grid[i][j] == 0)
            return 0;
        grid[i][j] = 0;
        int res = 1;
        for (int[] direction : directions) {
            res += dfs(i + direction[0], j + direction[1]);
        }
        return res;
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < this.height && j >= 0 && j < this.width ;
    }

}

// 934. 最短的桥 https://leetcode-cn.com/problems/shortest-bridge/
class shortestBridge {

    private static final int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public int shortestBridge(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        // 标记是否找到第一个小岛了
        boolean findIsland = false;
        // 记录第一个小岛的边界
        Queue<int[]> board = new LinkedList<>();
        // 去标记第一个小岛，将原来的 1 改成 2
        for (int x = 0; x < n && !findIsland; ++x) {
            for (int y = 0; y < m && !findIsland; ++y) {
                // 如果该位置是陆地
                if (grid[x][y] == 1) {
                    dfs(grid, visited, x, y, board);
                    findIsland = true;
                }
            }
        }

        // 记录两岛距离
        int ans = 0;
        while (!board.isEmpty()) {
            // 遍历边界上的每一个位置
            for (int i = 0; i < board.size(); ++i) {
                // 取出来下一个要检查的坐标
                int[] next = board.poll();
                for (int[] dir : dirs) {
                    // 对这个坐标进行上下左右检查
                    assert next != null;
                    int newX = next[0] + dir[0];
                    int newY = next[1] + dir[1];

                    // 如果这个新坐标合法，而且没有访问过，就对其进行检查
                    if (inArea(newX, newY, grid) && !visited[newX][newY]) {
                        visited[newX][newY] = true; // 首先标记成访问过了
                        // 如果接触到了第二个小岛，那么直接返回距离计数器 ans
                        if (grid[newX][newY] == 1) return ans;
                            // 否则的话，更新边界队列
                        else board.add(new int[]{newX, newY});
                    }
                }
            }
            // 边界计数器加一
            ++ans;
        }
        return ans;
    }

    /**
     * 对 grid 中的一个小岛进行标记
     *
     * @param grid    小岛描述图
     * @param visited 标记某个坐标是否访问过了
     * @param x       出发点 [x, y]
     * @param y       出发点 [x, y]
     * @param board   用来保存边界的队列
     */
    private void dfs(int[][] grid, boolean[][] visited, int x, int y, Queue<int[]> board) {
        grid[x][y] = 2;
        // 防止第二行判断溢出
        if (x == 0 || y == 0 || x == grid.length - 1 || y == grid[0].length - 1 || isBorder(x, y, grid)) {
            board.add(new int[]{x, y});
        }
        // 对 [x, y] 上下左右四个方向进行拓展检查
        for (int[] dir : dirs) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            // 如果新坐标没越界+未被访问过+是陆地 --> 对小岛进行扩展查找
            if (inArea(newX, newY, grid) && !visited[newX][newY] && grid[newX][newY] == 1) {
                visited[newX][newY] = true;
                dfs(grid, visited, newX, newY, board);
            }
        }
    }

    // 判断是否出界
    private boolean inArea(int x, int y, int[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    // 判断该点是不是岛的边界，也就是看该点的四个方向有没有一个点是海洋
    private boolean isBorder(int x, int y, int[][] grid) {
        return grid[x + 1][y] == 0 || grid[x - 1][y] == 0 || grid[x][y + 1] == 0 || grid[x][y - 1] == 0;
    }


}

// 547. 朋友圈个数 https://leetcode-cn.com/problems/number-of-provinces/solution/python-duo-tu-xiang-jie-bing-cha-ji-by-m-vjdr/
class findCircleNum {

    // 并查集模板
    class UnionFind {

        private HashMap<Integer, Integer> father;

        public UnionFind(HashMap<Integer, Integer> father) {
            this.father = new HashMap<>();
        }

        public void add(int x) {
            if (!father.containsKey(x)) {
                father.put(x, null);
            }
        }

        public void merge(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                father.put(rootX, rootY);
            }
        }

        public int find(int x) {

            int root = x;
            // 迭代找到根节点
            while (father.get(root) != null) {
                root = father.get(root);
            }
            // 将所有次级父节点连接到根节点，路径压缩
            while (x != root) {
                int original_father = father.get(x);
                father.put(x, root);
                x = original_father;
            }

            return root;
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }

    // 547 变形并查集，加入集合数量属性
    class UnionFind1 {
        // 记录父节点
        private Map<Integer,Integer> father;
        // 记录集合的数量
        private int numOfSets = 0;

        public UnionFind1() {
            father = new HashMap<>();
            numOfSets = 0;
        }

        public void add(int x) {
            if (!father.containsKey(x)) {
                father.put(x, null);
                numOfSets++;
            }
        }

        public void merge(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY){
                father.put(rootX,rootY);
                numOfSets--;
            }
        }

        public int find(int x) {
            int root = x;

            while(father.get(root) != null){
                root = father.get(root);
            }

            while(x != root){
                int original_father = father.get(x);
                father.put(x,root);
                x = original_father;
            }

            return root;
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }

        public int getNumOfSets() {
            return numOfSets;
        }
    }


    // ---------------------------1.Union-Find Set---------------------------
    public int solution01(int[][] isConnected) {
        UnionFind1 uf = new UnionFind1();
        // 遍历邻接矩阵下三角
        for(int i = 0;i < isConnected.length;i++){
            uf.add(i);
            for(int j = 0; j < i; j++){
                if(isConnected[i][j] == 1){
                    uf.merge(i,j);
                }
            }
        }
        return uf.getNumOfSets();
    }

    // ---------------------------2.DFS---------------------------
    public int solution02(int[][] isConnected) {
        // int[][] isConnected 是无向图的邻接矩阵，n 为无向图的顶点数量
        int n = isConnected.length;
        // 定义 boolean 数组标识顶点是否被访问
        boolean[] visited = new boolean[n];
        // 定义 cnt 来累计遍历过的连通域的数量
        int res = 0;
        for (int i = 0; i < n; i++) {
            // 若当前顶点 i 未被访问，说明又是一个新的连通域，则遍历新的连通域且cnt+=1.
            if (!visited[i]) {
                res++;
                dfs(i, isConnected, visited);
            }
        }
        return res;
    }

    private void dfs(int i, int[][] isConnected, boolean[] visited) {
        // 对当前顶点 i 进行访问标记
        visited[i] = true;
        // 继续遍历与顶点 i 相邻的顶点（使用 visited 数组防止重复访问）
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                dfs(j, isConnected, visited);
            }
        }
    }

    // ---------------------------3.BFS---------------------------
    public int solution03(int[][] isConnected) {
        // int[][] isConnected 是无向图的邻接矩阵，n 为无向图的顶点数量
        int n = isConnected.length;
        // 定义 boolean 数组标识顶点是否被访问
        boolean[] visited = new boolean[n];
        // 定义 cnt 来累计遍历过的连通域的数量
        int cnt = 0;
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            // 若当前顶点 i 未被访问，说明又是一个新的连通域，则bfs新的连通域且cnt+=1.
            if (!visited[i]) {
                cnt++;
                queue.offer(i);
                visited[i] = true;
                while (!queue.isEmpty()) {
                    int v = queue.poll();
                    for (int j = 0; j < n; j++) {
                        if (isConnected[v][j] == 1 && !visited[j]) {
                            visited[j] = true;
                            queue.offer(j);
                        }
                    }
                }
            }
        }
        return cnt;
    }

}

// 79. 单词搜索 https://leetcode-cn.com/problems/word-search/
class exist {

    private static final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        int m = board.length;
        int n = board[0].length;

        char[] chars = word.toCharArray();
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 从 (0, 0) 点开始进行 dfs 操作，不断地去找，
                // 如果以 (0, 0) 点没有对应的路径的话，那么就从 (0, 1) 点开始去找
                if (dfs(board, chars, visited, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    // start：当前在word中该找的位置
    private boolean dfs(char[][] board, char[] chars, boolean[][] visited, int i, int j, int start) {
        // 如果越界、当前字符不是期望的、当前字符已经访问过（寻找方向不对）
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length
                || chars[start] != board[i][j] || visited[i][j]) {
            return false;
        }
        // 如果该找的位置已经到达末尾，则说明已经找到
        if (start == chars.length - 1) {
            return true;
        }
        visited[i][j] = true;
        for (int[] d : directions) {
            if (dfs(board, chars, visited, i + d[0], j + d[1], start + 1)) {
                return true;
            }
        }
        // boolean ans = dfs(board, chars, visited, i + 1, j, start + 1)
        //         || dfs(board, chars, visited, i - 1, j, start + 1)
        //         || dfs(board, chars, visited, i, j + 1, start + 1)
        //         || dfs(board, chars, visited, i, j - 1, start + 1);
        visited[i][j] = false;
        return false;
    }


}

// 417. 太平洋大西洋水流问题 https://leetcode-cn.com/problems/pacific-atlantic-water-flow/
class pacificAtlantic {


    private int m, n;
    private int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public List<List<Integer>> solution01(int[][] heights) {

        List<List<Integer>> res = new ArrayList<>();
        if (heights == null || heights.length == 0)
            return res;
        
        this.m = heights.length;
        this.n = heights[0].length;

        //记录坐标地是否到达太平洋、大西洋
        boolean canReachP[][] = new boolean[m][n], canReachA[][] = new boolean[m][n];

        //上下左右出发，深度优先搜索
        for (int i = 0; i < m; i++) {
            dfs(heights, canReachP, i, 0);
            dfs(heights, canReachA, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
            dfs(heights, canReachP, 0, j);
            dfs(heights, canReachA, m - 1, j);
        }

        //遍历记录，如果都可到达即可加入结果
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (canReachP[i][j] && canReachA[i][j]) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }
        return res;
    }

    private void dfs(int[][] heights, boolean[][] canReach, int x, int y) {
        //如果已经扫描过可达就不用扫描
        if (canReach[x][y]) return;
        //扫描过即说明可达，这也是逆流的优点
        canReach[x][y] = true;
        // 如果未出界或新坐标不低于旧坐标，递归
        for (int[] dir : direction) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (inArea(newX, newY) && heights[newX][newY] >= heights[x][y]) {
                dfs(heights, canReach, newX, newY);
            }
        }
    }

    private boolean inArea(int x, int y){
        return 0 <= x && x < m && 0 <= y && y < n;
    }

}

// 130. 被围绕的区域 https://leetcode-cn.com/problems/surrounded-regions/
class solve {
    public void solve(char[][] board) {


    }
}

// 4. 寻找两个正序数组的中位数 https://leetcode.cn/problems/median-of-two-sorted-arrays/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-2/
class findMedianSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        return (findKth(nums1, 0, nums2, 0, left) + findKth(nums1, 0, nums2, 0, right)) / 2.0;
    }
    //i: nums1的起始位置 j: nums2的起始位置
    public int findKth(int[] nums1, int i, int[] nums2, int j, int k){
        int m = nums1.length;
        int n = nums2.length;

        if( i >= m) return nums2[j + k - 1]; // nums1为空数组
        if( j >= n) return nums1[i + k - 1]; // nums2为空数组
        if(k == 1){
            return Math.min(nums1[i], nums2[j]);
        }
        int midVal1 = (i + k / 2 - 1 < m) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int midVal2 = (j + k / 2 - 1 < n) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        if(midVal1 < midVal2){
            return findKth(nums1, i + k / 2, nums2, j , k - k / 2);
        }else{
            return findKth(nums1, i, nums2, j + k / 2 , k - k / 2);
        }
    }

}