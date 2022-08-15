import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 19:02 2021/10/27
 * @E-mail: 15611562852@163.com
 */

// 45. 跳跃游戏 II https://leetcode.cn/problems/jump-game-ii/solution/45-by-ikaruga/
class jump {
    public int jump(int[] nums) {
        // 双指针
        int step = 0;
        int start = 0;
        int end = 1;
        while (end < nums.length) {
            int max = 0;
            // 找到 [start,end) 范围内能跳的最远的位置
            for (int i = start; i < end; i++) {
                max = Math.max(max, nums[i] + i);
            }
            start = end;
            end = max + 1;
            step++;
        }
        return step;
    }
}

// 55. 跳跃游戏 https://leetcode.cn/problems/jump-game/
class Solution {
    public boolean canJump(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            // 如果当前下标 > 当前能跳到的最远位置，肯定到不了最后
            if (i > max) {
                return false;
            }
            // 更新能跳到的最远位置
            max = Math.max(max, nums[i] + i);
        }
        return true;
    }
}

// 70. 爬楼梯 https://leetcode-cn.com/problems/climbing-stairs/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
class climbStairs {

    // 经典斐波那契数列解法
    public int climbStairs(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        int a = 1, b = 2; //a 表示第n-2个台阶的走法，b表示第n-1个台阶的走法,传统迭代
        int count = 0 ;
        for (int i = 3; i <= n; i++) {
            count = a + b;//累加结果
            //向下迭代
            a = b;//下次迭代的第n-2个台阶的走法等于上次迭代n-1个台阶的走法
            b = count;//下次迭代的第n-1个台阶的走法等于上次迭代的第n个台阶走法
        }
        return count;
    }

    // 使用斐波那契数列公式 https://leetcode-cn.com/problems/climbing-stairs/solution/hua-jie-suan-fa-70-pa-lou-ti-by-guanpengchn/
    public int climbStairs1(int n) {
        double sqrt_5 = Math.sqrt(5);
        double fib_n = Math.pow((1 + sqrt_5) / 2, n + 1) - Math.pow((1 - sqrt_5) / 2,n + 1);
        return (int)(fib_n / sqrt_5);
    }

}

// 746. 使用最小花费爬楼梯 https://leetcode-cn.com/problems/min-cost-climbing-stairs/
class minCostClimbingStairs {

    // cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用
    public int minCostClimbingStairs(int[] cost) {
        int size = cost.length;
        int[] minCost = new int[size];
        minCost[0] = 0;
        minCost[1] = Math.min(cost[0], cost[1]);
        for (int i = 2; i < size; i++) {
            minCost[i] = Math.min(minCost[i - 1] + cost[i], minCost[i - 2] + cost[i - 1]);
        }
        return minCost[size - 1];
    }

    // 优化空间复杂度
    public int minCostClimbingStairs1(int[] cost) {
        int n = cost.length;
        int down = 0;
        int downdown = 0;
        for (int i = 2; i <= n; i++) {
            int cur = Math.min(cost[i - 1] + down, cost[i - 2] + downdown);   // 到当前楼层两种路径消耗的体力最小值
            downdown = down;       //及时更新便于下一层循环，楼下就变成了楼下的楼下
            down = cur;      //当前楼层就变成了楼下
        }
        return down;  //本该返回cur，不过cur是局部变量，但到达当前楼层的体力值cur在循环里已经赋值给了down
    }
}

// 62. 不同路径 https://leetcode-cn.com/problems/unique-paths/
class uniquePaths {

    // ----------------------- 解法一：动态规划 -----------------------
    public int uniquePaths(int m, int n) {
        int[][] f = new int[m][n];
        // 最左侧一列的位置都只有一种到达方式，就是从起点一直向下走
        for (int i = 0; i < m; ++i) {
            f[i][0] = 1;
        }
        // 最上面一行的位置都只有一种到达方式，就是从起点一直向右走
        for (int j = 0; j < n; ++j) {
            f[0][j] = 1;
        }
        // 每个位置的到达方式种数 = 左侧位置+上面位置
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                f[i][j] = f[i - 1][j] + f[i][j - 1];
            }
        }
        return f[m - 1][n - 1];
    }

    // ----------------------- 解法二：空间优化 -----------------------
    // https://zhuanlan.zhihu.com/p/91582909
    public static int uniquePaths1(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }

        int[] dp = new int[n]; //
        // 最上面一行的位置都只有一种到达方式，就是从起点一直向右走
        for(int i = 0; i < n; i++){
            dp[i] = 1;
        }

        // 公式：dp[i] = dp[i-1] + dp[i]，dp[i-1]：到达左侧点的方式种数，dp[i]：到达上方点的方式种数
        for (int i = 1; i < m; i++) {
            // 第 i 行第 0 列的初始值
            dp[0] = 1;
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j-1] + dp[j];
            }
        }
        return dp[n-1];
    }

    // ----------------------- 解法三：排列组合 -----------------------
    public int uniquePaths2(int m, int n) {
        long ans = 1;
        for (int x = n, y = 1; y < m; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }
}

// 63. 不同路径 II https://leetcode-cn.com/problems/unique-paths-ii/
class uniquePathsWithObstacles {

    // 网格中的障碍物和空位置分别用 1 和 0 来表示。
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0) {
            return 0;
        }

        // 定义 dp 数组并初始化第 1 行和第 1 列。
        // 网格中的障碍物和空位置分别用 1 和 0 来表示。
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m && obstacleGrid[i][0] == 0; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n && obstacleGrid[0][j] == 0; j++) {
            dp[0][j] = 1;
        }

        // 根据状态转移方程 dp[i][j] = dp[i - 1][j] + dp[i][j - 1] 进行递推。
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }
}

// 198. 打家劫舍 https://leetcode-cn.com/problems/house-robber/
class rob {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0];
        }

        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i-1], nums[i] + dp[i-2]);
        }
        return dp[n-1];
    }
}

// 213. 打家劫舍 II https://leetcode-cn.com/problems/house-robber-ii/
class rob1 {
    public int rob(int[] nums) {
        //将环分成0 - n-1 和 1 - n
        //转移方程: dp[n] = Math.max(dp[n-1], dp[n-2] + nums[n])
        if(nums.length == 1){
            return nums[0];
        }
        if(nums.length == 2){
            return Math.max(nums[0], nums[1]);
        }
        return Math.max(robber(Arrays.copyOfRange(nums,0,nums.length - 1)),
                robber(Arrays.copyOfRange(nums,1,nums.length)));
    }

    int robber(int[] nums){
        int n = nums.length;
        int[] dp = new int[n+1];
        dp[0] = nums[0];
        dp[1] = Math.max(dp[0], nums[1]);
        for(int i = 2; i < n; i++){
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }
        return dp[n-1];
    }
}

// 64. 最小路径和 https://leetcode-cn.com/problems/minimum-path-sum/
class minPathSum {

    public int minPathSum1(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int height = grid.length, width = grid[0].length;
        // 第一列只能从起点往下走到达
        for (int i = 1; i < height; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        // 第一行只能从起点往右走到达
        for (int i = 1; i < width; i++) {
            grid[0][i] += grid[0][i - 1];
        }
        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                grid[i][j] += Math.min(grid[i][j - 1], grid[i - 1][j]);
            }
        }
        return grid[height-1][width-1];
    }

    // --------------------- 空间优化 ---------------------
    public int minPathSum2(int[][] grid) {
        int len = grid[0].length;
        // 只需要记录上一行的距离，上上行的用不到
        int[] dp = new int[len];
        // 到达起点的距离为0
        dp[0] = grid[0][0];

        // 第一行的距离 = 左侧所有位置的距离之和
        for (int i = 1; i < len; i++) {
            dp[i] = dp[i-1] + grid[0][i];
        }

        // 依次遍历每一行
        for (int i = 1; i < grid.length; i++) {
            // 每一行第一个点的距离 = 上方所有位置的距离之和
            dp[0] = dp[0] + grid[i][0];
            for (int j = 1; j < len; j++)
                // dp[j-1] 左侧点的最小距离 dp[j]：上方点的最小距离
                dp[j] = Math.min(dp[j-1] + grid[i][j], dp[j] + grid[i][j]);
        }
        return dp[len-1];
    }

}

// 542. 01 矩阵 https://leetcode-cn.com/problems/01-matrix/  practical application
class updateMatrix {

    private static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // -------------------------- 解法一：BFS --------------------------
    public int[][] updateMatrix1(int[][] matrix) {
        int height = matrix.length, width = matrix[0].length;
        int[][] dist = new int[height][width];
        boolean[][] visited = new boolean[height][width];
        Queue<int[]> queue = new LinkedList<>();

        // 将所有的 0 添加进初始队列中
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        // BFS
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0], y = cell[1];
            for (int[] dir : dirs) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                // 如果未出界且未被访问过
                if (inArea(newX, newY, matrix) && !visited[newX][newY]) {
                    dist[newX][newY] = dist[x][y] + 1;
                    queue.offer(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
        return dist;
    }

    // 判断是否出界
    private boolean inArea(int x, int y, int[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    // -------------------------- 解法二：动态规划 --------------------------
    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        // 初始化动态规划的数组，所有的距离值都设置为一个很大的数
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; ++i) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        // 如果 (i, j) 的元素为 0，那么距离为 0
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        // 从左上角开始
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i - 1 >= 0) {
                    // 与上方
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
                }
                if (j - 1 >= 0) {
                    // 与左侧
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
                }
            }
        }
        // 从右下角开始
        for (int i = m - 1; i >= 0; --i) {
            for (int j = n - 1; j >= 0; --j) {
                if (i + 1 <= m-1) {
                    // 与下方
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j] + 1);
                }
                if (j + 1 <= n-1) {
                    // 与右侧
                    dp[i][j] = Math.min(dp[i][j], dp[i][j + 1] + 1);
                }
            }
        }
        return dp;
    }

}

// 221. 最大正方形 https://leetcode-cn.com/problems/maximal-square/
class maximalSquare {
    // https://leetcode-cn.com/problems/maximal-square/solution/li-jie-san-zhe-qu-zui-xiao-1-by-lzhlyle/
    public int maximalSquare(char[][] matrix) {
        /**
         dp[i][j]表示以第i行第j列为右下角所能构成的最大正方形边长, 则递推式为:
         dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]);
         **/
        int m = matrix.length;
        if(m < 1) return 0;
        int n = matrix[0].length;
        int max = 0;
        int[][] dp = new int[m+1][n+1];

        for(int i = 1; i <= m; ++i) {
            for(int j = 1; j <= n; ++j) {
                if(matrix[i-1][j-1] == '1') {
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
                    max = Math.max(max, dp[i][j]);
                }
            }
        }

        return max*max;    }

    // 空间优化
    public int maximalSquare1(char[][] matrix) {
        if (matrix == null || matrix.length < 1 || matrix[0].length < 1) return 0;

        int height = matrix.length;
        int width = matrix[0].length;
        int maxSide = 0;

        int[] dp = new int[width + 1];

        for (char[] chars : matrix) {
            int northwest = 0; // 个人建议放在这里声明，而非循环体外
            //逐列遍历
            for (int col = 0; col < width; col++) {
                //获取以该元素上方元素为右下角的正方形的最大边长，用于下一列元素计算边长时使用(下一列元素的左上角)
                int nextNorthwest = dp[col + 1];
                //若该位置为1，则查询其左侧/上方的正方形的大小，并添加
                if (chars[col] == '1') {
                    //dp[col]：保存以该元素左边的元素为右下角的正方形的最大边长
                    //赋值右边的dp[col+1]: 保存移该元素上方的元素为右下角的正方形的最大边长
                    //northwest: 保存以该元素左上角的元素为右下角的正方形的最大边长
                    //+1: 既然本元素为1，那么，一定是在前边三个元素的基础上增加了1的
                    //赋值右边的dp[col+1]: 保存以该元素为右下角的正方形的最大边长
                    dp[col + 1] = Math.min(Math.min(dp[col], dp[col + 1]), northwest) + 1;

                    //尝试更新最大边长
                    maxSide = Math.max(maxSide, dp[col + 1]);
                } else {
                    //否则，以该元素为右下角的正方形的边长一定为0
                    dp[col + 1] = 0;
                }
                //更新下一个左上角的位置
                northwest = nextNorthwest;
            }
        }
        return maxSide * maxSide;
    }





}

// 279. 完全平方数 https://leetcode-cn.com/problems/perfect-squares/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
class numSquares {
    // https://leetcode-cn.com/problems/perfect-squares/solution/hua-jie-suan-fa-279-wan-quan-ping-fang-shu-by-guan/
    public int numSquares(int n) {
        // 存储0-n的数都各自最少需要几个完全平方数相加
        int[] dp = new int[n + 1]; // 默认初始化值都为0
        for (int i = 1; i <= n; i++) {
            dp[i] = i; // 最坏的情况就是每次+1，n=n个1*1相加，2=2个1*1相加,3=3个1*1相加
            for (int j = 1; i - j * j >= 0; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1); // 动态转移方程
            }
        }
        return dp[n];
    }

}

// 91. 解码方法??????? https://leetcode-cn.com/problems/decode-ways/solution/gong-shui-san-xie-gen-ju-shu-ju-fan-wei-ug3dd/
class numDecodings {
    public int numDecodings(String s) {
        int n = s.length();
        s = " " + s;
        char[] array = s.toCharArray();
        int[] f = new int[n + 1];
        f[0] = 1;
        for (int i = 1; i <= n; i++) {
            // a : 代表「当前位置」单独形成 item
            // b : 代表「当前位置」与「前一位置」共同形成 item
            int a = array[i] - '0', b = (array[i - 1] - '0') * 10 + (array[i] - '0');
            // 如果 a 属于有效值，那么 f[i] 可以由 f[i - 1] 转移过来
            if (1 <= a && a <= 9) f[i] = f[i - 1];
            // 如果 b 属于有效值，那么 f[i] 可以由 f[i - 2] 或者 f[i - 1] & f[i - 2] 转移过来
            if (10 <= b && b <= 26) f[i] += f[i - 2];
        }
        return f[n];
    }

    int numDecodings1(String s) {
        char[] array = s.toCharArray();
        if (array[0] == '0') return 0; // 第一个就是0
        int pre = 1, curr = 1; //dp[-1] = dp[0] = 1
        for (int i = 1; i < s.length(); i++) {
            int tmp = curr;
            if (array[i] == '0') // *0
                if (array[i - 1] == '1' || array[i - 1] == '2') curr = pre;  // 10,20
                else return 0; // 30,40,50...
            else if (array[i - 1] == '1' || (array[i - 1] == '2' && array[i] >= '1' && array[i] <= '6')) // 1*, 21-26
                curr = curr + pre;
            // 除去以上三种情况外的else，也就是array[i-1]为0,3-9（此时已排除s[i]==0的情况），dp[i]=dp[i-1]。代码中cur相当于dp[i-1],所以直接省略了一个else语句。
            pre = tmp;
        }
        return curr;
    }
}

// 139. 单词拆分 https://leetcode-cn.com/problems/word-break/solution/shou-hui-tu-jie-san-chong-fang-fa-dfs-bfs-dong-tai/
class wordBreak {
    // BFS
    public boolean wordBreak(String s, List<String> wordDict) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);

        int slength = s.length();
        boolean[] visited = new boolean[slength + 1];

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int start = queue.poll().intValue();
                for (String word : wordDict) {
                    int nextStart = start + word.length();
                    if (nextStart > slength || visited[nextStart]) {
                        continue;
                    }
                    if (s.indexOf(word, start) == start) {
                        if (nextStart == slength) {
                            return true;
                        }
                        queue.add(nextStart);
                        visited[nextStart] = true;
                    }
                }
            }
        }

        return false;
    }

    // DFS
    public boolean wordBreak2(String s, List<String> wordDict) {
        boolean[] visited = new boolean[s.length() + 1];
        return dfs(s, 0, wordDict, visited);
    }

    private boolean dfs(String s, int start, List<String> wordDict, boolean[] visited) {
        for (String word : wordDict) {
            int nextStart = start + word.length();
            if (nextStart > s.length() || visited[nextStart]) {
                continue;
            }

            if (s.indexOf(word, start) == start) {
                if (nextStart == s.length() || dfs(s, nextStart, wordDict, visited)) {
                    return true;
                }
                visited[nextStart] = true;
            }
        }
        return false;
    }

    // DP
    // https://leetcode-cn.com/problems/word-break/solution/dan-ci-chai-fen-by-leetcode-solution/
    public boolean wordBreak3(String s, List<String> wordDict) {
        int length = s.length();
        int maxWordLength = 0;
        // 找到字典中最长的单词
        for (String word : wordDict) {
            if (word.length() > maxWordLength) {
                maxWordLength = word.length();
            }
        }
        // dp[i] 表示 s 中索引为 [0,  i - 1] 范围的字符串是否可被 wordDict 拆分
        boolean[] dp = new boolean[length + 1];
        // 空串默认true
        dp[0] = true;
        for (int i = 1; i < dp.length; i++) {

            // dp[i]只需要往前探索到词典里最长的单词即可，不用每次都从起点开始判断
            // for (int j = 0; j < i; j++) {
            for (int j = (Math.max(i - maxWordLength, 0)); j < i; j++) {
                // substring左闭右开
                // [0, i] 的字符串可被拆分，当前仅当任一子串 [0, j] 及 [j, i] 可被拆分
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[length];
    }

}

// 0-1背包问题 416. 分割等和子集 https://leetcode-cn.com/problems/partition-equal-subset-sum/solution/0-1-bei-bao-wen-ti-xiang-jie-zhen-dui-ben-ti-de-yo/
class canPartition {

    // dp[0][0] = false;
    public boolean canPartition(int[] nums) {
        int len = nums.length;
        // 题目已经说非空数组，可以不做非空判断
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        // int sum = Arrays.stream(nums).sum(); 使用流方式求和
        // 特判：如果是奇数，就不符合要求
        if ((sum & 1) == 1) {
            return false;
        }

        int target = sum / 2;
        // 创建二维状态数组，行：物品索引，列：容量（包括 0）
        boolean[][] dp = new boolean[len+1][target + 1];

        for (int i = 1; i <= len; i++) {
            for (int j = 0; j <= target; j++) {
                // 直接从上一行先把结果抄下来，然后再修正
                // 看图解最后的手稿图，nums[i]代表当前行的行名，小于nums[i]的位置全部照抄上一行
                dp[i][j] = dp[i - 1][j];
                // j 恰好等于 nums[i]，即单独 nums[j] 这个数恰好等于此时「背包的容积」 j
                if (nums[i-1] == j) {
                    dp[i][j] = true;
                    continue; // 使用当前几个数字可以装满当前容量为j的背包，继续下一个容量j+1
                }
                if (nums[i-1] < j) { // 如果当前物品质量 < 当前背包容量，看看是不是可以和前面的其他数字装满背包
                    // dp[i - 1][j]:不选择 nums[i]，如果在 [0, i - 1] 这个子区间内已经有一部分元素，使得它们的和为j
                    // dp[i - 1][j - nums[i]]:选择 nums[i]，如果在 [0, i - 1] 这个子区间内就得找到一部分元素，使得它们的和为 j
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i-1]];
                }
            }
            // 由于状态转移方程的特殊性，提前结束，可以认为是剪枝操作
            if (dp[i][target]) {
                return true;
            }
        }
        return dp[len][target];
    }


    // dp空间优化，二维转一维
    public boolean canPartition2(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return true;
        }
        int sum = Arrays.stream(nums).sum();
        if ((sum & 1) == 1) {
            return false;
        }

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            for (int j = target; j >= num; j--) {
                dp[j] = dp[j] || dp[j - num];
            }
            if (dp[target]) {
                return true;
            }
        }
        return dp[target];
    }

}

// 0-1背包问题 474. 一和零 https://leetcode-cn.com/problems/ones-and-zeroes/solution/dong-tai-gui-hua-zhuan-huan-wei-0-1-bei-bao-wen-ti/
class findMaxForm {

    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][][] dp = new int[len + 1][m + 1][n + 1]; // 存放子集的元素个数

        for (int i = 1; i <= len; i++) {
            // 注意：有一位偏移
            int[] count = countZeroAndOne(strs[i - 1]);
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    // 先把上一行抄下来
                    dp[i][j][k] = dp[i - 1][j][k];
                    int zeros = count[0];
                    int ones = count[1];
                    // 当前需背包容量为j个0，k个1，如果只用一个物品strs[i - 1]装不满
                    if (j >= zeros && k >= ones) {
                        // 如果用物品strs[i - 1]，则dp[i - 1][j - zeros][k - ones] + 1 ，因为dp记录的是子集元素数量，用的话就+1
                        // 如果不用物品strs[i - 1]，则dp[i - 1][j][k]
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - zeros][k - ones] + 1);
                    }
                }
            }
        }
        return dp[len][m][n];
    }

    private int[] countZeroAndOne(String str) {
        int[] cnt = new int[2];
        for (char c : str.toCharArray()) {
            cnt[c - '0']++;
        }
        return cnt;
    }

    // dp空间优化，三维变一维，dp倒序更新
    public int findMaxForm1(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;
        for (String s : strs) {
            int[] zeroAndOne = calcZeroAndOne(s);
            int zeros = zeroAndOne[0];
            int ones = zeroAndOne[1];
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
                }
            }
        }
        return dp[m][n];
    }

    private int[] calcZeroAndOne(String str) {
        int[] res = new int[2];
        for (char c : str.toCharArray()) {
            res[c - '0']++;
        }
        return res;
    }

}

// 0-1背包问题 494. 目标和
class findTargetSumWays {
    // ------------------- 解法一：动态规划 -------------------
    // https://leetcode-cn.com/problems/target-sum/solution/dong-tai-gui-hua-si-kao-quan-guo-cheng-by-keepal/
    public int findTargetSumWays(int[] nums, int target) {
        int sum = Arrays.stream(nums).sum();
        if (Math.abs(target) > Math.abs(sum)) return 0;

        int len = nums.length;
        int range = sum * 2 + 1;//因为要包含负数所以要两倍，又要加上0这个中间的那个情况
        int[][] dp = new int[len][range];//这个数组是从总和为-sum开始的
        // 加上sum纯粹是因为下标界限问题，赋第二维的值的时候都要加上sum
        // 初始化   第一个数只能分别组成+-nums[i]的一种情况
        dp[0][sum + nums[0]] += 1;
        dp[0][sum - nums[0]] += 1;
        for (int i = 1; i < len; i++) {
            for (int j = -sum; j <= sum; j++) {
                if ((j + nums[i]) > sum) { //+不成立 加上当前数大于sum   只能减去当前的数
                    dp[i][j + sum] = dp[i - 1][j - nums[i] + sum] + 0;
                } else if ((j - nums[i]) < -sum) { //-不成立  减去当前数小于-sum   只能加上当前的数
                    dp[i][j + sum] = dp[i - 1][j + nums[i] + sum] + 0;
                } else {//+-都可以
                    dp[i][j + sum] = dp[i - 1][j + nums[i] + sum] + dp[i - 1][j - nums[i] + sum];
                }
            }
        }
        return dp[len - 1][target + sum];
    }

    // ------------------- 解法二：动态规划（思维优化+空间优化） 牛逼！！！-------------------
    // https://leetcode-cn.com/problems/target-sum/solution/494-mu-biao-he-dong-tai-gui-hua-zhi-01be-78ll/
    public int findTargetSumWays1(int[] nums, int S) {
        int sum = Arrays.stream(nums).sum();
        if (Math.abs(S) > Math.abs(sum) || (S + sum) % 2 == 1) {
            return 0;
        }
        int target = (S + sum) / 2;
        // dp[i]:装满容量为i的背包有几种办法
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for(int num : nums){
            for (int j = target; j >= num; j--) {
                dp[j] = dp[j] + dp[j - num];
            }
        }
        return dp[target];
    }

    // ------------------- 解法三：DFS -------------------
    // https://leetcode-cn.com/problems/target-sum/solution/gong-shui-san-xie-yi-ti-si-jie-dfs-ji-yi-et5b/
    public int findTargetSumWays2(int[] nums, int target) {
        return dfs(nums, target, 0, 0);
    }
    // u:当前层数
    int dfs(int[] nums, int target, int u, int cur) {
        if (u == nums.length) {
            return cur == target ? 1 : 0;
        }
        int left = dfs(nums, target, u + 1, cur + nums[u]);
        int right = dfs(nums, target, u + 1, cur - nums[u]);
        return left + right;
    }
}

// 完全背包 322. 零钱兑换 https://leetcode-cn.com/problems/coin-change/
class coinChange {

    // 所有做法 https://leetcode-cn.com/problems/coin-change/solution/javadi-gui-ji-yi-hua-sou-suo-dong-tai-gui-hua-by-s/
    public int coinChange(int[] coins, int amount) {
        if (coins.length == 0) {
            return -1;
        }
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE/2);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE/2 ? -1 : dp[amount];


    }
}

// 72. 编辑距离 https://leetcode-cn.com/problems/edit-distance/solution/zi-di-xiang-shang-he-zi-ding-xiang-xia-by-powcai-3/
class minDistance {
    public int minDistance(String word1, String word2) {

        int n1 = word1.length();
        int n2 = word2.length();
        // dp[i][j] 代表 word1 到 i 位置转换成 word2 到 j 位置需要最少步数
        int[][] dp = new int[n1 + 1][n2 + 1];

        // 第一行，当word1为空串时，修改为word2需要的步骤依次递增
        for (int j = 1; j <= n2; j++) {
            dp[0][j] = dp[0][j - 1] + 1;
        }

        // 第一列，当word2为空串时，修改为word1需要的步骤依次递增
        for (int i = 1; i <= n1; i++) {
            dp[i][0] = dp[i - 1][0] + 1;
        }

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                // 如果当前两个字符相同，不需要修改
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // 如果当前两个字符不相同，找之前修改次数（左，上，左上三者之中）最少+1
                else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i][j - 1]), dp[i - 1][j]) + 1;
                }
            }
        }
        return dp[n1][n2];

    }
}

// 583. 两个字符串的删除操作（LCS最长公共子序列，与72类似） https://leetcode-cn.com/problems/delete-operation-for-two-strings/
// 本质是上求两者不相同的部分长度之和 = 两者长度之和 - 2*相同部分长度
class minDistance1 {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        //dp[i][j]表示word1[0...i - 1]与word2[0...j-1]的最大公共子序列长度
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return m + n - 2 * dp[m][n];
    }
}

// 300. 最长递增子序列（不连续） https://leetcode.cn/problems/longest-increasing-subsequence/
class lengthOfLIS {
    public int lengthOfLIS(int[] nums) {
        int length = nums.length;
        int[] dp = new int[length];
        // 最坏情况为自己
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}

// 376. 摆动序列 https://leetcode.cn/problems/wiggle-subsequence/
class wiggleMaxLength {
    // public int wiggleMaxLength(int[] nums) {
    //     int length = nums.length;
    //     if (length <= 2) {
    //         return length;
    //     }
    //     int[][] dp = new int[length][2];
    //     for (int[] ints : dp) {
    //         Arrays.fill(ints, 1);
    //     }
    //     int max = 1;
    //     for (int i = 1; i < length; i++) {
    //         for (int j = 0; j < i; j++) {
    //             if (dp[j][1] != 0) {
    //                 if ((dp[j][1] * (nums[i] - nums[j]) <= 0)) {
    //                     dp[i][1] = nums[i] - nums[j];
    //                     dp[i][0] = Math.max(dp[i][0], dp[j][0] + 1);
    //                 }
    //             }
    //         }
    //         max = Math.max(dp[i][0], max);
    //     }
    //
    //     return max;
    //
    // }

    public int wiggleMaxLength(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }
        int up = 1;
        int down = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                up = down + 1;
            }
            if (nums[i] < nums[i - 1]) {
                down = up + 1;
            }
        }
        return Math.max(up, down);
    }
}

// 674. 最长连续递增序列 https://leetcode.cn/problems/longest-continuous-increasing-subsequence/
class findLengthOfLCIS {
    public int findLengthOfLCIS(int[] nums) {
        int length = nums.length;
        int[] dp = new int[length];
        // 最短的就是一个数
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 1; i < length; i++) {
            if (nums[i] > nums[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            }
            max = Math.max(max, dp[i]);
        }
        return max;

    }
}

// 128.最长连续序列（下标不连续） https://leetcode-cn.com/problems/longest-consecutive-sequence/
class longestConsecutive {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            hashSet.add(num);
        }
        int res = 0;
        for (Integer num : hashSet) {
            // 如果不是子序列最小值，跳过，我们需要从子序列最开始算长度，否则比实际长度短
            if (!hashSet.contains(num - 1)) {
                int currentNum = num;
                int currentLength = 1;
                // 如果还有下一个数
                while (hashSet.contains(currentNum + 1)) {
                    // 更新当前数，以便找到下一个数
                    currentNum++;
                    currentLength++;
                }
                res = Math.max(currentLength, res);
            }
        }
        return res;

    }
}

// 718. 最长重复子数组（连续） https://leetcode.cn/problems/maximum-length-of-repeated-subarray/ 类似583
class findLength {
    public int findLength(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int result = 0;
        int[][] dp = new int[len1+1][len2+1];
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (nums1[i-1] == nums2[j-1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    result = Math.max(result, dp[i][j]);
                }
            }
        }
        return result;
    }
}

// 53. 最大子数组和 https://leetcode-cn.com/problems/maximum-subarray/
class maxSubArray {
    // DP
    public int maxSubArray(int[] nums) {
        int res = nums[0];
        int sum = 0;
        for (int num : nums) {
            // if (sum > 0)
            //     // 如果sum > 0对于后面的子序列是有好处的。
            //     sum += num;
            // else
            //     // 假设sum<=0，那么后面的子序列肯定不包含目前的子序列，所以令sum = num；
            //     sum = num;
            sum = Math.max(sum + num, num);
            // 保证可以找到最大的子序和
            res = Math.max(res, sum);
        }
        return res;
    }
}

// 5. 最长回文子串（动态规划） https://leetcode-cn.com/problems/longest-palindromic-substring/
class longestPalindrome11 {
    public String longestPalindrome(String s) {
        int n = s.length();
        int max = Integer.MIN_VALUE;
        String res = "";

        // dp[i][j] 表示[i,j]的字符是否为回文子串,上三角矩阵
        boolean[][] dp = new boolean[n][n];

        // 注意，外层循环要倒着写，内层循环要正着写
        // 因为要求dp[i][j] 需要知道dp[i+1][j-1]
        for (int i = n - 1; i >= 0; i--) {
            // dp为上三角矩阵，所以j=i
            for (int j = i; j < n; j++) {
                // (s.charAt(i)==s.charAt(j) 时，当元素个数为1,2,3个或者除去i,j剩下的更短的字符串为回文时，其一定为回文子串
                if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    if (j - i + 1 > max) {
                        res = s.substring(i, j + 1);
                        max = j - i + 1;
                    }
                }
            }
        }
        return res;
    }
}





// 650. 只有两个键的键盘 https://leetcode.cn/problems/2-keys-keyboard/
class minSteps {
    public static int INF = Integer.MAX_VALUE/2;

    // -------------------------- 解法一：DP --------------------------
    public int minSteps(int n) {
        // f[i][j] 为经过最后一次操作后，当前记事本上有 i 个字符，粘贴板上有 j 个字符的最小操作次数，所以均为n+1维
        int[][] f = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(f[i], INF);
        }
        // 记事本1个，粘贴板0个，说明没有经过任何操作；记事本1个，粘贴板1个，说明只有一次 copy 操作
        f[1][0] = 0; f[1][1] = 1;
        // f[1]行已经初始化，所以i从2开始遍历
        for (int i = 2; i <= n; i++) {
            int min = INF;
            // 因为f数组肯定是一个非标准的下三角被赋值，j<=i，因为粘贴板的字符数必定 < 记事本字符数
            for (int j = 0; j <= i / 2; j++) {
                // 最后一次操作是 paste 操作：此时粘贴板的字符数量不会发生变化，找到 [记事本有 i-j 个字符，粘贴板有 j 个字符] 的情况，并+1（本次 paste 操作）
                f[i][j] = f[i - j][j] + 1;
                // 最后一次操作是 copy 操作：此时记事本的字符数量不会发生变化，找到本行之前最少的即可
                min = Math.min(min, f[i][j]);
            }
            // 从f[i][j] 也即 f[i][i/2] 到 f[i][i] 只需要一次 copy 操作
            f[i][i] = min + 1;
        }
        // 记事本上有 n 个字符时，找到本行中最少的操作次数
        return Arrays.stream(f[n]).min().getAsInt();
    }


    // -------------------------- 解法二：DFS --------------------------
    public int minSteps1(int n) {
        if (n == 1) return 0;
        return dfs(n, 1, 0);
    }

    // n：固定参数，要达到的目标（输出n个'A'）
    // cur：当前记事本上已输出的'A'的数量
    // paste：当前粘贴板上已有的'A'的数量
    // 返回：在当前cur、paste的情况下，达到目标，所需要的最少操作次数
    private int dfs(int n, int cur, int paste) {
        if (cur == n) return 0; // 当前记事本输出已达目标，无需操作
        if (cur > n) return INF; // 当前记事本输出超过了目标，不可能达到目标，表示之前的DFS尝试方案无效
        // 1）本次操作，选择复制（如果当前粘贴板上'A'数量 != 当前记事本上'A'数量，则可以有此选择，否则，复制操作无意义，不做此选择）：
        int p1 = cur != paste ? 1 + dfs(n, cur, cur) : INF;
        // 2）本次操作，选择粘贴（如果当前粘贴板上'A'数量 > 0，则可以有此选择，否则，粘贴操作无意义，不做此选择）：
        int p2 = paste > 0 ? 1 + dfs(n, cur+paste, paste) : INF;
        return Math.min(p1, p2);
    }

}


// 股票类型题目 https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/solution/bao-li-mei-ju-dong-tai-gui-hua-chai-fen-si-xiang-b/
// 121. 买卖股票的最佳时机 https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
class maxProfit {
    // 记录【今天之前买入的最小值】
    // 计算【今天之前最小值买入，今天卖出的获利】，也即【今天卖出的最大获利】
    // 比较【每天的最大获利】，取最大值即可
    public int maxProfit(int[] prices) {
        if(prices.length <= 1)
            return 0;
        int min = prices[0], max = 0;
        // 从第 2 天开始遍历
        for(int i = 1; i < prices.length; i++) {
            // 记录到此为止的最大收益
            max = Math.max(max, prices[i] - min);
            // 记录到此为止的最低价
            min = Math.min(min, prices[i]);
        }
        return max;
    }

    // -------------------------- 解法二：DP --------------------------
    public int maxProfit1(int[] prices) {
        int len = prices.length;
        // 特殊判断
        if (len < 2) return 0;
        // 记录截止到今天，不持股和持股的最大收益
        int[][] dp = new int[len][2];

        // dp[i][0] 下标为 i 这天结束的时候，不持股的收益
        // dp[i][1] 下标为 i 这天结束的时候，持股的收益

        // 初始化：不持股显然为 0，持股就需要减去第 1 天（下标为 0）的股价
        dp[0][0] = 0; // 第一天不买，收益为0
        dp[0][1] = -prices[0]; // 第一天买了收益是负数

        // 从第 2 天开始遍历
        for (int i = 1; i < len; i++) {
            // 不持股
            // dp[i - 1][0] 今天我也不买
            // dp[i - 1][1] + prices[i]  之前买今天卖，买的那天(假设价格x)到今天都是持有,dp[][1]都为-x，
            // 所以不管哪天买的可以直接用dp[i - 1][1]
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 持股：dp[i - 1][1] 昨天买的持有（原因同上）       -prices[i] 今天才买
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        // 最后一天不持股的收益情况
        return dp[len - 1][0];
    }
}

// 122. 买卖股票的最佳时机 II https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/solution/tan-xin-suan-fa-by-liweiwei1419-2/
class maxProfit1 {
    // 贪心算法：只要后一天比前一天大 就把这两天的差值加一下
    public int maxProfit(int[] prices) {
        int ans=0;
        for (int i = 1; i <= prices.length - 1; i++) {
            if (prices[i] > prices[i - 1]) {
                ans += prices[i] - prices[i - 1];
            }
        }
        return ans;
    }

    // DP
    public int maxProfit1(int[] prices) {
        int len = prices.length;
        if (len < 2) return 0;

        // 0：不持股
        // 1：持股
        int[][] dp = new int[len][2];

        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < len; i++) {
            // 这两行调换顺序也是可以的
            // 不持股：dp[i - 1][0] 今天我也不买      dp[i - 1][1] + prices[i] 昨天买今天卖
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 持股：dp[i - 1][1] 昨天买的持有      dp[i - 1][0] - prices[i] 今天买
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            // dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // dp[i][1] = Math.max(dp[i - 1][1], -prices[i]); 与121的区别在此

        }
        return dp[len - 1][0];
    }
}

// 123. 买卖股票的最佳时机 III https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/

// 309. 最佳买卖股票时机含冷冻期??????? https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
class maxProfit3 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int length = prices.length;
        //由于可以无限次交易，所以只定义两个维度，第一个维度是天数，第二个维度表示是否持有股票，0表示不持有，1表示持有，2表示冷冻期
        int[][] dp = new int[length][3];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[0][2] = 0;
        for (int i = 1; i < length; i++) {
            // 第i天不持有股票的情况有两种
            // a.第i - 1天也不持有股票
            // b.第i - 1天进入冷冻期（i - 1天卖出）
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][2]);
            // 第i天持有股票有两种情况
            // a.第i - 1天也持有股票
            // b.第i - 1天不持有股票，在第i天买入
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
            // 第i天进入冷冻期只有一种情况，第i - 1天持有股票且卖出（今天卖出）
            dp[i][2] = dp[i-1][1] + prices[i];
        }
        // 最后最大利润为最后一天，不持有股票或者进入冷冻期的情况 （今天卖出）
        return Math.max(dp[length-1][0], dp[length-1][2]);
    }
}



// 11. 盛最多水的容器 https://leetcode.cn/problems/container-with-most-water/
class maxArea {
    public int maxArea(int[] height) {
        int ans = 0;
        // 双指针
        int left = 0, right = height.length - 1;
        while (left < right) {
            ans = Math.max(ans, (right - left) * Math.min(height[left], height[right]));
            // 每次移动两者中的短板
            // 因为移动短板之后，面积可能变小，变大，不变。但移动长板，面积只可能不变或变小，不可能变大
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }
        return ans;
    }
}

// 42. 接雨水 https://leetcode.cn/problems/trapping-rain-water/
class trap {

    // 方法一：按列遍历，动态规划
    public int trap(int[] height) {
        int ans = 0;
        int length = height.length;

        // max_left[i] 为 height[i] 之前的最高，不包括自己
        int[] max_left = new int[length];
        for (int i = 1; i < length-1; i++) {
            max_left[i] = Math.max(height[i - 1], max_left[i - 1]);
        }

        // max_right[i] 为 height[i] 之后的最高，不包括自己
        int[] max_right = new int[length];
        for (int i = length-2; i >= 0; i--) {
            max_right[i] = Math.max(max_right[i + 1], height[i + 1]);
        }

        // 最两端的列不用考虑，因为一定不会有水。所以下标从 1 到 length - 2
        for (int i = 1; i < length - 1; i++) {
            //找出两端较小的
            int min = Math.min(max_left[i], max_right[i]);
            //只有当前列的高度小于（左侧最高和右侧最高两者中矮的一个时）才会有水，其他情况不会有水
            if (height[i] < min) {
                ans += (min - height[i]);
            }
        }
        return ans;
    }

}

// 32. 最长有效括号 https://leetcode.cn/problems/longest-valid-parentheses/
class longestValidParentheses {
    public int longestValidParentheses(String s) {
        int n = s.length();
        int[] dp = new int[n];//dp是以i处括号结尾的有效括号长度
        int max_len = 0;
        char[] chars = s.toCharArray();
        //i从1开始，一个是单括号无效，另一个是防i - 1索引越界
        for(int i = 1; i < n; i++) {
            if(chars[i] == ')') { //遇见右括号才开始判断
                if(chars[i-1] == '(') { //上一个是左括号
                    if(i < 2) { //开头处
                        dp[i] = 2;
                    } else { //非开头处
                        dp[i] = dp[i - 2] + 2;
                    }
                }
                else { //上一个也是右括号
                    if(dp[i - 1] > 0) {//上一个括号是有效括号
                        //pre_left为i处右括号对应左括号下标，推导：(i-1)-dp[i-1]+1 - 1
                        int pre_left = i - dp[i - 1] - 1;
                        if(pre_left >= 0 && chars[pre_left] == '(') {//左括号存在且为左括号（滑稽）
                            dp[i] = dp[i - 1] + 2;
                            //左括号前还可能存在有效括号
                            if(pre_left - 1 > 0) {
                                dp[i] = dp[i] + dp[pre_left - 1];
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

// 135. 分发糖果 https://leetcode-cn.com/problems/candy/
class candy {
    public int candy(int[] ratings) {

        int length = ratings.length;
        if (length < 2) {
            return length;
        }
        int[] num = new int[length];
        // 每个孩子至少有一颗糖
        Arrays.fill(num, 1);
        // 从前向后
        for (int i = 1; i < length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                num[i] = num[i - 1] + 1;
            }
        }
        // 从后向前
        for (int i = length - 1; i > 0; i--) {
            if (ratings[i - 1] > ratings[i]) {
                num[i - 1] = Math.max(num[i - 1], num[i] + 1);
            }
        }
        return Arrays.stream(num).sum();
    }
}

// test











