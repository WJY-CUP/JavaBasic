package DataStructure;

import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:59 2022/4/28
 * @E-mail: 15611562852@163.com
 */

// 二叉树节点数据结构
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

// N叉数节点数据结构
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
}

// 104. 二叉树的最大深度 https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
class maxDepth {
    // BFS
    public int maxDepth(TreeNode root) {
        if (root==null) return 0;
        int res = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 每层的节点数
            int size = queue.size();
            res++;
            // 将上一层的节点全部出队
            for (int i = 0; i < size; i++) {
                // 将当前节点出队
                TreeNode node = queue.poll();
                // 将当前节点的所有子节点入队
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return res;
    }

    // 递归
    public int maxDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    // DFS
    int maxLevel = 0;
    public int maxDepth2(TreeNode root) {
        if (root == null) return 0;
        dfs(root, 1);
        return maxLevel;
    }

    public void dfs(TreeNode root, int level) {
        if (root == null) return;
        maxLevel = Math.max(level, maxLevel);
        dfs(root.left, level + 1);
        dfs(root.right, level + 1);
    }

}

// 111. 二叉树的最小深度 https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
class minDepth {
    // BFS
    public int minDepth(TreeNode root) {
        if (root==null) return 0;
        int res = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 每层的节点数
            int size = queue.size();
            res++;
            // 将上一层的节点全部出队
            for (int i = 0; i < size; i++) {
                // 将当前节点出队
                TreeNode node = queue.poll();
                // 如果当前节点为叶子节点，则直接返回
                if (node.left == null&& node.right == null) return res; // 这行是与104最大深度的唯一区别
                // 将当前节点的所有子节点入队
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return res;
    }
}

// 559. N 叉树的最大深度 https://leetcode-cn.com/problems/maximum-depth-of-n-ary-tree/
class maxDepth1 {
    public int maxDepth(Node root) {
        if (root==null) return 0;
        int res = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 每层的节点数
            int size = queue.size();
            res++;
            // 将上一层的节点全部出队
            for (int i = 0; i < size; i++) {
                // 将当前节点出队
                Node node = queue.poll();
                // 将当前节点的所有子节点入队
                if(node.children != null){
                    for(int j = 0; j < node.children.size(); j++){
                        queue.offer(node.children.get(j));
                    }
                }
            }
        }
        return res;
    }
}

// 110. 平衡二叉树 https://leetcode-cn.com/problems/balanced-binary-tree/
class isBalanced {
    public boolean isBalanced(TreeNode root) {
        return recur(root) != -1;
    }

    private int recur(TreeNode root) {
        if (root==null) return 0;
        int left = recur(root.left);
        if (left==-1) return -1;
        int right = recur(root.right);
        if (right==-1) return -1;
        return Math.abs(left - right) < 2 ? Math.max(left, right) + 1 : -1;
    }
}

// 543. 二叉树的直径 https://leetcode-cn.com/problems/diameter-of-binary-tree/
class diameterOfBinaryTree {
    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        //遍历每一个节点,求出此节点作为根的树的深度,那么,左子树深度加右子树深度的最大值即是答案
        dfs(root);
        return max;
    }

    public int dfs(TreeNode root) {
        if (root == null) return 0;
        int right = dfs(root.right);
        int left = dfs(root.left);
        if (right + left > max)
            max = right + left;
        return Math.max(right, left) + 1;
    }
}

// 112. 路径总和（是否有） https://leetcode-cn.com/problems/path-sum/
class hasPathSum {

    // DFS
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        // 如果为叶子节点
        if (root.left == null && root.right == null) {
            return sum - root.val == 0;
        }
        // 如果为非叶子节点，继续
        return hasPathSum(root.left, sum - root.val)
                || hasPathSum(root.right, sum - root.val);
    }

    // BFS
    public boolean hasPathSum1(TreeNode root, int targetSum) {
        if (root==null) return false;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        LinkedList<Integer> sumQueue = new LinkedList<>();
        sumQueue.offer(0);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                Integer sum = sumQueue.poll();
                // 当前节点是叶子节点且加上后为targetSum
                if (poll.left == null && poll.right == null && (poll.val + sum) == targetSum) {
                    return true;
                }
                if (poll.left != null) {
                    queue.offer(poll.left);
                    sumQueue.offer(sum + poll.val);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                    sumQueue.offer(sum + poll.val);
                }
            }
        }
        return false;
    }
}

// 113. 路径总和 II（找出所有的路径） https://leetcode-cn.com/problems/path-sum-ii/
class pathSum {

    // BFS
    // 根据 TreeNode.val 记录符合条件的路径
    private List<List<Integer>> res = new LinkedList<>();
    // 根据 TreeNode 记录每个节点的父节点，方便回溯路径
    private HashMap<TreeNode, TreeNode> map = new HashMap<>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root==null) return res;
        // 遍历节点队列
        Queue<TreeNode> queueNode = new LinkedList<>();
        // 累加和队列:记录到达该节点前，路径累加和
        Queue<Integer> queueSum = new LinkedList<>();

        queueNode.offer(root);
        queueSum.offer(0);

        while (!queueNode.isEmpty()) {
            int size = queueNode.size(); // 本行可不加：加上可以理解为依次处理每一层的节点
            for (int i = 0; i < size; i++) {  // 本行可不加
                TreeNode poll = queueNode.poll();
                Integer sum = queueSum.poll();
                int temp = poll.val + sum;
                // 该节点是叶子节点，且加上 其所在路径之前的累加和 为目标和
                if (poll.left == null && poll.right == null && temp == targetSum) {
                    getPath(poll);
                }
                if (poll.left != null) {
                    queueNode.offer(poll.left);
                    queueSum.offer(temp);
                    map.put(poll.left, poll);
                }
                if (poll.right != null) {
                    queueNode.offer(poll.right);
                    queueSum.offer(temp);
                    map.put(poll.right, poll);
                }
            }
        }
        return res;
    }

    // 根据叶子节点，逆向回溯记录路径，并保存到全局变量 res
    private void getPath(TreeNode node) {
        LinkedList<Integer> path = new LinkedList<>();
        // 从叶子节点到根节点，逆向记录路径
        while (node != null) {
            path.add(node.val);
            node = map.get(node);
        }
        Collections.reverse(path);
        // 因为 path 需要重复使用，所以 new LinkedList<Integer>(path)，不能直接 res.add(path)
        res.add(new LinkedList<Integer>(path));
    }

    // DFS
    Deque<Integer> path = new LinkedList<>();
    public List<List<Integer>> pathSum1(TreeNode root, int targetSum) {
        dfs(root, targetSum);
        return res;
    }

    private void dfs(TreeNode node, int targetSum) {
        if (node == null) return;
        path.offerLast(node.val);
        // 记录减去此节点值后的值
        targetSum -= node.val;
        // 如果此节点为叶子节点且剩余值为0，说明此路径符合
        if (node.left == null && node.right == null && targetSum == 0) {
            res.add(new LinkedList<>(path));
        }
        // 继续向下探索，因为 Node.val 与 targetSum 有可能是负数
        dfs(node.left, targetSum);
        dfs(node.right, targetSum);
        // 回溯至上一个节点
        path.pollLast();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        System.out.println(x);
        // scanner.nextLine();
        // for (int i = 0; i < x; i++) {
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            System.out.println(s);

        }
    }


}

// 101. 对称二叉树 https://leetcode-cn.com/problems/symmetric-tree/
class isSymmetric {

    // BFS
    public boolean isSymmetric(TreeNode root) {
        if (root==null) return true;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);
        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();
            // 如果对称位置均为null
            if (node1 == null && node2 == null) continue;
            // 如果果对称位置有一个为空节点或者值不相等
            if (node1 == null || node2 == null || node1.val != node2.val) return false;
            // 按照对称位置依次将对称位置的左右子节点加入队列
            queue.offer(node1.left);
            queue.offer(node2.right);
            queue.offer(node1.right);
            queue.offer(node2.left);
        }
        return true;
    }

    // DFS
    public boolean isSymmetric1(TreeNode root) {
        if (root==null) return true;
        return dfs(root.left, root.right);
    }

    // 与BFS类似
    private boolean dfs(TreeNode node1, TreeNode node2) {
        // 如果对称位置均为null
        if (node1 == null && node2 == null) return true;
        // 如果果对称位置有一个为空节点或者值不相等
        if (node1 == null || node2 == null || node1.val != node2.val) return false;
        return dfs(node1.left, node2.right) && dfs(node1.right, node2.left);
    }



}

// 637. 二叉树的层平均值 https://leetcode-cn.com/problems/average-of-levels-in-binary-tree/
class averageOfLevels {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        if (root==null) return res;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        double sum;

        while (!queue.isEmpty()) {
            sum = 0;
            // 每层的节点数
            int size = queue.size();
            // 将上一层的节点全部出队
            for (int i = 0; i < size; i++) {
                // 将当前节点出队
                TreeNode node = queue.poll();
                sum += node.val;
                // 将当前节点的所有子节点入队
                if (node.left!=null) queue.offer(node.left);
                if (node.right!=null) queue.offer(node.right);
            }
            res.add(sum / size);
        }
        return res;
    }
}

// 102. 二叉树的层序遍历 https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
class levelOrder {
    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null) return new LinkedList<>();
        // 对于107题 本行不能使用多态  List = new LinkedList 因为下面要用到Deque的专有的addFirst
        LinkedList<List<Integer>> res = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }
            res.add(list);
            // res.addFirst(list);   本行是与 107. 二叉树的倒序层序遍历 II 唯一有区别的一行

        }
        return res;
    }
}

// 144. 二叉树的前序遍历 https://leetcode-cn.com/problems/binary-tree-preorder-traversal/solution/leetcodesuan-fa-xiu-lian-dong-hua-yan-shi-xbian-2/
class preorderTraversal {

    // DFS
    private ArrayList<Integer> res = new ArrayList<>();

    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) return res;
        dfs(root);
        return res;
    }

    private void dfs(TreeNode root) {
        if (root == null) return;
        res.add(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    // BFS
    public List<Integer> preorderTraversal1(TreeNode root) {
        if (root == null) return res;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val);
            // 先入栈的后出栈
            if (node.right != null) {
                stack.push(node.right);
            }
            // 后入栈的先出栈
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return res;
    }



}

// 94. 二叉树的中序遍历 https://leetcode.cn/problems/binary-tree-inorder-traversal/
class inorderTraversal {

    private ArrayList<Integer> res = new ArrayList<>();

    // DFS
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) return res;
        dfs(root);
        return res;
    }

    private void dfs(TreeNode root) {
        if (root == null) return;
        inorderTraversal(root.left);
        res.add(root.val);
        inorderTraversal(root.right);
    }

    // BFS
    public List<Integer> inorderTraversal1(TreeNode root) {
        if (root == null) return res;
        Stack<TreeNode> stack = new Stack<>();

        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            // 将所有左子树节点入栈
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            // 当前根节点出栈
            TreeNode node = stack.pop();
            res.add(node.val);
            // 当前根节点是否有右子树，有则入栈
            if (node.right != null) {
                cur = node.right;
            }
        }
        return res;
    }
}

// 145. 二叉树的后序遍历 https://leetcode.cn/problems/binary-tree-postorder-traversal/
class postorderTraversal {

    private ArrayList<Integer> res = new ArrayList<>();

    // DFS
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) return res;
        dfs(root);
        return res;
    }

    private void dfs(TreeNode root) {
        if (root == null) return;
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        res.add(root.val);
    }

    // BFS
    public List<Integer> postorderTraversal1(TreeNode root) {
        if (root == null) return res;
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();

        // 类似倒序层序遍历
        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }
        while (!stack2.isEmpty()) {
            res.add(stack2.pop().val);
        }

        return res;
    }
}

// 105 从前序与中序遍历序列构造二叉树 https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/solution/cong-qian-xu-yu-zhong-xu-bian-li-xu-lie-gou-zao-9/
class buildTree {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    private Map<Integer, Integer> map;
    int rootIndex = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTree(0, preorder.length - 1, preorder);
    }
    // 在前序遍历数组中，从前往后依次取到根节点，对应的去中序数组中确定左子树和右子树的范围
    public TreeNode buildTree(int left,int right,int[] preorder){
        if (left > right) {
            return null;
        }
        int rootValue = preorder[rootIndex];
        rootIndex++;
        TreeNode root = new TreeNode(rootValue);
        // 前序遍历的第一个节点为根节点吗，用于划分中序遍历数组
        root.left = buildTree(left, map.get(rootValue) - 1, preorder);
        root.right = buildTree(map.get(rootValue) + 1, right, preorder);
        return root;
    }
}
// 106. 从中序与后序遍历序列构造二叉树 https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
class buildTree1 {
    private Map<Integer, Integer> indexMap = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int i_length = inorder.length;
        int p_length = postorder.length;
        for (int i = 0; i < i_length; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(inorder, postorder, 0, i_length - 1, 0, p_length - 1);
    }

    public TreeNode myBuildTree(int[] inorder, int[] postorder, int i_left, int i_right, int p_left, int p_right) {
        // 如果区间不合法
        if (p_left > p_right || i_left > i_right) {
            return null;
        }
        // 后序遍历中子区间的最后一个节点是根节点
        int p_root = p_right;
        // 根据后序遍历根节点值找到其在中序遍历中的索引
        int i_root = indexMap.get(postorder[p_root]);
        // 建立根节点
        TreeNode root = new TreeNode(postorder[p_root]);
        // 根据中序遍历计算左子树长度
        int left_len = i_root - i_left;

        /*
            左子树子区间的范围
            中序：i_left（起点），i_root - 1（终点）
            后序：p_left（起点），p_left + left_len - 1（终点）
         */
        root.left = myBuildTree(inorder, postorder, i_left, i_root - 1, p_left, p_left + left_len - 1);

        /*
            右子树子区间的范围
            中序：i_root + 1（起点），i_right（终点）
            后序：p_left + left_len（起点），p_right - 1（终点）
         */
        root.right = myBuildTree(inorder, postorder, i_root + 1, i_right, p_left + left_len, p_right - 1);
        return root;
    }

}

// 99. 恢复二叉搜索树 https://leetcode.cn/problems/recover-binary-search-tree/
class recoverTree {
    private List<TreeNode> list = new ArrayList<>();
    // 后面需要修改节点值，所以需要存储 TreeNode 而非 TreeNode.val
    public void recoverTree(TreeNode root) {
        dfs(root);
        TreeNode x = null;
        TreeNode y = null;
        // 扫面遍历的结果，找出可能存在错误交换的节点x和y
        for (int i = 0; i < list.size() - 1; ++i) {
            // 1 2 3 7 5 6 4    y=5 x=7 --> y=4 x=7
            if (list.get(i).val > list.get(i + 1).val) {
                y = list.get(i + 1);
                if (x == null) {
                    x = list.get(i);
                }
            }
        }
        // 交换这两个节点值，恢复二叉搜索树
        int tmp = x.val;
        x.val = y.val;
        y.val = tmp;
    }

    //中序遍历二叉树，并将遍历的结果保存到list中
    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.left);
        list.add(node);
        dfs(node.right);
    }
}

// 669. 修剪二叉搜索树 https://leetcode.cn/problems/trim-a-binary-search-tree/
class trimBST {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return null;
        }
        if (root.val < low) {
            //因为是二叉搜索树,节点.left < 节点 < 节点.right
            //节点数字比low小,就把左节点全部裁掉.
            root = root.right;
            //裁掉之后,继续看右节点的剪裁情况.剪裁后重新赋值给root.
            root = trimBST(root, low, high);
        } else if (root.val > high) {
            //如果数字比high大,就把右节点全部裁掉.
            root = root.left;
            //裁掉之后,继续看左节点的剪裁情况
            root = trimBST(root, low, high);
        } else {
            //如果数字在区间内,就去裁剪左右子节点.
            root.left = trimBST(root.left, low, high);
            root.right = trimBST(root.right, low, high);
        }
        return root;
    }
}

// 226. 翻转二叉树 https://leetcode.cn/problems/invert-binary-tree/
class invertTree {

    // 1.层序遍历
    public TreeNode invertTree(TreeNode root) {
        // 层次遍历--直接左右交换即可
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode rightTree = node.right;
            node.right = node.left;
            node.left = rightTree;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }

    // 2.先序遍历，从根到叶依次交换
    public TreeNode invertTree1(TreeNode root) {
        if (root==null) return null;
        TreeNode right = root.right;
        root.right = invertTree(root.left);
        root.left = invertTree(right);
        return root;
    }

    // 2.中序遍历
    public TreeNode invertTree2(TreeNode root) {
        if (root == null) return null;
        invertTree(root.left); // 递归找到左节点
        TreeNode rightNode= root.right; // 保存右节点
        root.right = root.left;
        root.left = rightNode;
        // 递归找到右节点 继续交换 : 因为此时左右节点已经交换了,所以此时的右节点为root.left
        invertTree(root.left);
        return root;
    }

    // 3.后序遍历，从叶到根依次交换
    public TreeNode invertTree3(TreeNode root) {
        if (root == null) return null;
        TreeNode leftNode = invertTree(root.left);
        TreeNode rightNode = invertTree(root.right);
        root.right = leftNode;
        root.left = rightNode;
        return root;
    }




}

// 617. 合并二叉树 https://leetcode.cn/problems/merge-two-binary-trees/
class mergeTrees {

    // 1.BFS
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return root1 == null ? root2 : root1;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root1);
        queue.offer(root2);
        while (!queue.isEmpty()) {
            TreeNode n1 = queue.poll();
            TreeNode n2 = queue.poll();
            n1.val += n2.val;
            if (n1.left != null && n2.left != null) {
                queue.offer(n1.left);
                queue.offer(n2.left);
            } else if (n1.left == null) {
                n1.left = n2.left;
            }
            if (n1.right != null && n2.right != null) {
                queue.offer(n1.right);
                queue.offer(n2.right);
            } else if (n1.right == null) {
                n1.right = n2.right;
            }
        }
        return root1;
    }

    // 2.DFS
    public TreeNode mergeTrees1(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return root1 == null ? root2 : root1;
        }
        return dfs(root1, root2);
    }

    private TreeNode dfs(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return root1 == null ? root2 : root1;
        }
        root1.val += root2.val;
        root1.left = dfs(root1.left, root2.left);
        root1.right = dfs(root1.right, root2.right);
        return root1;
    }
}

// 572. 另一棵树的子树 https://leetcode.cn/problems/subtree-of-another-tree/

// 235. 二叉搜索树的最近公共祖先 https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/
class lowestCommonAncestor {

    // 非递归（推荐）
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 如果p,q为根节点，则公共祖先为根节点
        if(root == null || root == p || root == q) return root;
        //如果根节点和p,q的差相乘是正数，说明这两个差值要么都是正数要么都是负数，也就是说
        //他们肯定都位于根节点的同一侧，就继续往下找
        while ((root.val - p.val) * (root.val - q.val) > 0)
            root = p.val < root.val ? root.left : root.right;
        //如果相乘的结果是负数，说明p和q位于根节点的两侧，如果等于0，说明至少有一个就是根节点
        return root;
    }

    // 递归
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        //如果小于等于0，说明p和q位于root的两侧，直接返回即可
        if ((root.val - p.val) * (root.val - q.val) <= 0)
            return root;
        //否则，p和q位于root的同一侧，就继续往下找
        return lowestCommonAncestor(p.val < root.val ? root.left : root.right, p, q);
    }
}

// 236. 二叉树的最近公共祖先 https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
class lowestCommonAncestor1 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 如果p,q为根节点，则公共祖先为根节点
        if(root == null || root == p || root == q) return root;
        // 如果p,q在左子树，则公共祖先在左子树查找
        if (find(root.left, p) && find(root.left, q)) {
            return lowestCommonAncestor(root.left, p, q);
        }
        // 如果p,q在右子树，则公共祖先在右子树查找
        if (find(root.right, p) && find(root.right, q)) {
            return lowestCommonAncestor(root.right, p, q);
        }
        // 如果p,q分属两侧，则公共祖先为根节点
        return root;
    }

    // 查找节点 c 是否在 root为根节点的树中
    private boolean find(TreeNode root, TreeNode c) {
        if (root == null) return false;
        if (root.val == c.val) return true;
        return find(root.left, c) || find(root.right, c);
    }
}

// 208. 实现 Trie (前缀树) https://leetcode.cn/problems/implement-trie-prefix-tree/
class Trie {

    class TireNode {
        private boolean isEnd;
        TireNode[] next;

        public TireNode() {
            isEnd = false;
            next = new TireNode[26];
        }
    }

    private TireNode root;

    public Trie() {
        root = new TireNode();
    }

    public void insert(String word) {
        TireNode node = root;
        for (char c : word.toCharArray()) {
            if (node.next[c - 'a'] == null) {
                node.next[c - 'a'] = new TireNode();
            }
            node = node.next[c - 'a'];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        TireNode node = root;
        for (char c : word.toCharArray()) {
            node = node.next[c - 'a'];
            if (node == null) {
                return false;
            }
        }
        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        TireNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.next[c - 'a'];
            if (node == null) {
                return false;
            }
        }
        return true;
    }
}

// 648. 单词替换 https://leetcode.cn/problems/replace-words/solution/by-ac_oier-jecf/
class replaceWords {

    class TreeNode {
        boolean isEnd;
        TreeNode[] next = new TreeNode[26];
    }
    TreeNode root = new TreeNode();

    void add(String s) {
        TreeNode node = root;
        for (char c : s.toCharArray()) {
            if (node.next[c - 'a'] == null) {
                node.next[c - 'a'] = new TreeNode();
            }
            node = node.next[c - 'a'];
        }
        node.isEnd = true;
    }

    String query(String s) {
        TreeNode node = root;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            TreeNode nextNode = node.next[chars[i] - 'a'];
            if (nextNode == null) {
                break;
            }
            if (nextNode.isEnd) {
                return s.substring(0, i+1);
            }
            node = nextNode;
        }
        return s;
    }


    public String replaceWords(List<String> ds, String s) {
        for (String str : ds) add(str);
        StringBuilder sb = new StringBuilder();
        for (String str : s.split(" ")) sb.append(query(str)).append(" ");
        return sb.substring(0, sb.length() - 1);
    }
}




















