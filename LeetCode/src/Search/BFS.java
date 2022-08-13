package Search;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 18:49 2021/10/22
 * @E-mail: 15611562852@163.com
 */


/**
 * 深度遍历： 第一步：明确递归参数 第二步：明确递归终止条件 第三步：明确递归函数中的内容 第四步：明确回溯返回值
 *
 * 广度遍历： 第一步：设置队列，添加初始节点 第二步：判断队列是否为空 第三步：迭代操作 弹出队列元素，进行逻辑处理 当前队列元素的下级元素，入队 第四步：在此执行步骤三
 */


// 934. 最短的桥 https://leetcode-cn.com/problems/shortest-bridge/
// DFS+BFS
class shortestBridge1 {
    // 用来转换方向
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
        if (x == 0 || y == 0 || x == grid.length - 1 || y == grid[0].length - 1 ||
                // 判断该点是不是岛的边界，也就是看该点的四个方向有没有一个点是海洋
                grid[x + 1][y] == 0 || grid[x - 1][y] == 0 || grid[x][y + 1] == 0 || grid[x][y - 1] == 0) {
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


}
