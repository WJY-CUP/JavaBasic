package DataStructure;

import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 13:19 2022/5/20
 * @E-mail: 15611562852@163.com
 */

// 785. 判断二分图 https://leetcode.cn/problems/is-graph-bipartite/
class isBipartite {

    // 1.BFS
    public boolean isBipartite(int[][] graph) {
        // 定义 visited 数组，初始值为 0 表示未被访问，赋值为 1 或者 -1 表示两种不同的颜色。
        int[] visited = new int[graph.length];
        Queue<Integer> queue = new LinkedList<>();
        // 因为图中可能含有多个连通域，所以我们需要判断是否存在顶点未被访问，若存在则从它开始再进行一轮 bfs 染色。
        for (int i = 0; i < graph.length; i++) {
            // 如果已被访问过，跳过
            if (visited[i] != 0) {
                continue;
            }
            // 每出队一个顶点，将其所有邻接点染成相反的颜色并入队。
            queue.offer(i);
            visited[i] = 1;
            while (!queue.isEmpty()) {
                int v = queue.poll();
                for (int w: graph[v]) {
                    // 如果当前顶点的某个邻接点已经被染过色了，且颜色和当前顶点相同，说明此无向图无法被正确染色，返回 false。
                    if (visited[w] == visited[v]) {
                        return false;
                    }
                    // 如果未被访问过，将其染成相反的颜色并入队。
                    if (visited[w] == 0) {
                        visited[w] = -visited[v];
                        queue.offer(w);
                    }
                }
            }
        }
        return true;
    }

    // 2.DFS
    public boolean isBipartite1(int[][] graph) {
        // 定义 visited 数组，初始值为 0 表示未被访问，赋值为 1 或者 -1 表示两种不同的颜色。
        int[] visited = new int[graph.length];
        // 因为图中可能含有多个连通域，所以我们需要判断是否存在顶点未被访问，若存在则从它开始再进行一轮 dfs 染色。
        for (int i = 0; i < graph.length; i++) {
            if (visited[i] == 0 && !dfs(graph, i, 1, visited)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph, int v, int color, int[] visited) {
        // 如果要对某顶点染色时，发现它已经被染色了，则判断它的颜色是否与本次要染的颜色相同，如果矛盾，说明此无向图无法被正确染色，返回 false。
        if (visited[v] != 0) {
            return visited[v] == color;
        }

        // 对当前顶点进行染色，并将当前顶点的所有邻接点染成相反的颜色。
        visited[v] = color;
        for (int w: graph[v]) {
            if (!dfs(graph, w, -color, visited)) {
                return false;
            }
        }
        return true;
    }

}

// 207. 课程表 https://leetcode.cn/problems/course-schedule/
class canFinish {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 1.课号和对应的入度
        int[] inDegree = new int[numCourses];
        // 2.依赖关系, 依赖当前课程的后序课程
        List<Integer>[] adj = new ArrayList[numCourses];

        // 初始化入度和依赖关系
        for (int[] relate : prerequisites) {
            // (3,0), 想学3号课程要先完成0号课程, 更新3号课程的入度和0号课程的依赖(邻接表)
            int next = relate[0];
            int pre = relate[1];
            // 1.更新入度
            inDegree[next]++;
            // 2.当前节点的邻接表
            if(adj[pre] == null){
                adj[pre] = new ArrayList<>();
            }
            adj[pre].add(next);
        }

        // 3.BFS, 将入度为0的课程放入队列, 队列中的课程就是没有先修, 可以学的课程
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                q.offer(i);
            }
        }
        // 取出一个节点, 对应学习这门课程.
        // 遍历当前邻接表, 更新其入度; 更新之后查看入度, 如果为0, 加入到队列
        while (!q.isEmpty()) {
            int cur = q.poll();
            // 遍历当前课程的邻接表, 更新后继节点的入度
            if (adj[cur] == null) {
                continue;
            }

            for (int k : adj[cur]) {
                inDegree[k]--;
                if (inDegree[k] == 0) {
                    q.offer(k);
                }
            }
        }

        // 4.遍历入队, 如果还有课程的入度不为0, 返回fasle
        for (int value : inDegree) {
            if (value != 0) {
                return false;
            }
        }
        return true;
        // 效率低
        // return Arrays.stream(inDegree).sum()==0;
    }
}

// 210. 课程表 II https://leetcode.cn/problems/course-schedule-ii/
class findOrder {
    public int[] findOrder(int numCourses, int[][] prerequisites) {

        //存储某个节点能够到达的其他节点集合
        List<Integer>[] adj = new ArrayList[numCourses];
        //记录某个节点的入度
        int[] inDegree = new int[numCourses];

        for(int[] p : prerequisites){
            int next = p[0];
            int pre = p[1];
            inDegree[next]++;
            if(adj[pre] == null){
                adj[pre] = new ArrayList<>();
            }
            adj[pre].add(next);
        }

        Queue<Integer> queue = new LinkedList<>();
        //找到入度为 0 的节点并入队
        for(int i = 0; i < numCourses; i++){
            if(inDegree[i] == 0){
                queue.add(i);
            }
        }

        //记录遍历的课程顺序
        int[] res = new int[numCourses];
        int index = 0;

        while(!queue.isEmpty()){
            /*
            首先我们应该明确这么一点，在队列中的元素都是 0 入度的课程，即没有需要前修的课程就可以直接学习
            那么我们遍历到该课程，假设学习完，那么它指向的课程入度都需要 -1，当它指向的某个课程入度为 0 的时候，同时也需要将该课程添加到队列中
            */
            int p = queue.poll();
            // 记录节点 p 到结果数组
            res[index++] = p;
            // 如果节点 p 没有下一个节点，继续
            if (adj[p] == null) {
                continue;
            }
            // 将节点 p 能到达的所有节点入度-1，假装已经删除节点 p
            for(int val : adj[p]){
                inDegree[val]--;
                // 若入度-1后为0，则入队
                if (inDegree[val] == 0) {
                    queue.add(val);
                }
            }
        }
        //index == numCourses 意味着全部课程都访问过了，即最终都能够满足 0 入度的条件，即全部能够学习完成
        return index == numCourses ? res : new int[0];
    }
}




