package Search;


import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 12:08 2021/8/22
 * @E-mail: 15611562852@163.com
 */



// 46. 全排列 https://leetcode-cn.com/problems/permutations/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
//     不含重复数字的数组 nums ，返回其 所有可能的全排列
class permute {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();            //排列组合结果
        List<Integer> path = new ArrayList<>();                     //单个排列
        dfs(res,nums,path);
        return res;
    }

    // 使用 if (path.contains(num)) 判断状态
    private void dfs(List<List<Integer>> res, int[] nums, List<Integer> path){
        if (path.size() == nums.length) {
            //对于每次添加的单个排列，应该都是不同的引用对象
            res.add(new ArrayList<>(path));
            return;
        }

        for (int num : nums) {
            // 当前层中，已添加的数不再考虑
            if (path.contains(num)) {
                continue;
            }
            // 未添加的数则存放
            path.add(num);
            // 进入下一层（递归）
            dfs(res, nums, path);
            // 从深层节点向浅层节点回溯
            // 如果path使用LinkedList，出栈可使用 path.removeLast();
            path.remove(path.size() - 1);
        }
    }

}

// 47. 全排列 II https://leetcode-cn.com/problems/permutations-ii/
// 包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
class permuteUnique {

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();            //排列组合结果
        List<Integer> path = new ArrayList<>();                     //单个排列

        boolean[] visited = new boolean[nums.length];
        Arrays.sort(nums);
        dfs(nums, path, visited, res);
        return res;
    }

    private void dfs(int[] nums, List<Integer> path, boolean[] visited, List<List<Integer>> res){
        if (path.size() == nums.length) {
            //对于每次添加的单个排列，应该都是不同的引用对象
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // 如果当前数被使用了 或者 与前一个数相同且前一个数被使用过且撤销了（剪枝）
            if (visited[i] || (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1])) {
                continue;
            }
            path.add(nums[i]);
            visited[i] = true;
            dfs(nums, path, visited, res);

            path.remove(path.size() - 1);
            visited[i] = false;
        }
    }
}

// 39. 组合总和 https://leetcode-cn.com/problems/combination-sum/  candidates 中的 同一个 数字可以 无限制重复被选取，无重复
class combinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;
        List<List<Integer>> res = new ArrayList<>();
        // 排序是剪枝的前提
        Arrays.sort(candidates);
        Deque<Integer> path = new LinkedList<>();
        dfs(candidates, target, 0, path, res);
        return res;
    }

    private void dfs(int[] candidates, int target, int begin, Deque<Integer> path, List<List<Integer>> res) {
        int length = candidates.length;
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        // 重点理解这里从 begin 开始搜索的语意
        for (int i = begin; i < length; i++) {
            // 剪枝：减去 candidates[i] 小于 0，减去后面的 candidates[i + 1]、candidates[i + 2] 肯定也小于 0，因此用 break
            if (target < candidates[i]) {
                break;
            }
            path.addLast(candidates[i]);
            // 注意：由于每一个元素可以重复使用，下一轮搜索的起点依然是 i，这里非常容易弄错
            dfs(candidates,target - candidates[i], i, path, res);
            // 状态重置
            path.removeLast();

        }
    }

}

// 40. 组合总和 II https://leetcode-cn.com/problems/combination-sum-ii/  candidates 中的每个数字在每个组合中只能使用 一次，有重复
class combinationSum2 {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        int len = candidates.length;
        List<List<Integer>> res = new ArrayList<>();

        // 剪枝的关键步骤
        Arrays.sort(candidates);
        Deque<Integer> path = new ArrayDeque<>(len);
        dfs(candidates, target, 0, path, res);
        return res;
    }

    private void dfs(int[] candidates, int target, int begin, Deque<Integer> path, List<List<Integer>> res) {
        int length = candidates.length;
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = begin; i < length; i++) {
            // 大剪枝：减去 candidates[i] 小于 0，减去后面的 candidates[i + 1]、candidates[i + 2] 肯定也小于 0，因此用 break
            if (target < candidates[i]) {
                break;
            }

            // 小剪枝：同一层相同数值的结点，从第 2 个开始，候选数更少，结果一定发生重复，因此跳过，用 continue
            if (i > begin && candidates[i] == candidates[i - 1]) {
                continue;
            }

            path.addLast(candidates[i]);
            // 因为元素不可以重复使用，这里递归传递下去的是 i + 1 而不是 i
            dfs(candidates, target - candidates[i], i + 1, path, res);
            path.removeLast();
        }
    }
}

// 77. 组合III https://leetcode-cn.com/problems/combinations/ 类似39
class combine {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (k <= 0 || n < k) {
            return res;
        }
        // 从 1 开始是题目的设定
        Deque<Integer> path = new ArrayDeque<>();
        dfs(n, k, 1, path, res);
        return res;
    }

    private void dfs(int n, int k, int begin, Deque<Integer> path, List<List<Integer>> res) {
        // 递归终止条件是：path 的长度等于 k
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        // 遍历可能的搜索起点
        for (int i = begin; i <= n; i++) {
            // 向路径变量里添加一个数
            path.addLast(i);
            // 下一轮搜索，设置的搜索起点要加 1，因为组合数理不允许出现重复的元素
            dfs(n, k, i + 1, path, res);
            // 重点理解这里：深度优先遍历有回头的过程，因此递归之前做了什么，递归之后需要做相同操作的逆向操作
            path.removeLast();
        }
    }
}

// 78. 子集 https://leetcode-cn.com/problems/subsets/
class subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        dfs(nums, res, path, 0);
        return res;
    }

    private void dfs(int[] nums, List<List<Integer>> res, Deque<Integer> path, int begin) {
        res.add(new ArrayList<>(path));
        for (int i = begin; i < nums.length; i++) {
            path.add(nums[i]);
            dfs(nums, res, path, i+1);
            path.removeLast();
        }
    }
}

// 90. 子集 II https://leetcode-cn.com/problems/subsets-ii/
class subsetsWithDup {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        Arrays.sort(nums);
        dfs(nums, res, path, 0);
        return res;
    }

    private void dfs(int[] nums, List<List<Integer>> res, Deque<Integer> path, int begin) {
        res.add(new ArrayList<>(path));
        for (int i = begin; i < nums.length; i++) {
            if (i > begin && nums[i] == nums[i-1]) {
                continue;
            }
            path.add(nums[i]);
            dfs(nums, res, path, i+1);
            path.removeLast();
        }
    }



}

// 60. 排列序列 https://leetcode-cn.com/problems/permutation-sequence/
class getPermutation {
    public String getPermutation(int n, int k) {

        StringBuilder path = new StringBuilder();
        boolean[] used = new boolean[n + 1];

        // 计算0-n的阶乘并保存，后续要用
        int[] factorial = new int[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }
        // index 层数
        dfs(n, k, 0, factorial, path, used);
        return path.toString();
    }

    /**
     * @param index 在这一步之前已经选择了几个数字，其值恰好等于这一步需要确定的下标位置
     */
    private void dfs(int n, int k, int index, int[] factorial, StringBuilder path, boolean[] used){
        // 如果当前层数==n,说明已到达叶子节点
        if (index == n) {
            return;
        }

        // 计算还未确定的数字的全排列的个数，第 1 次进入的时候是 n - 1
        int cnt = factorial[n - 1 - index];
        for (int i = 1; i <= n; i++) {
            // 如果当前数字使用过
            if (used[i]) {
                continue;
            }
            // 如果分支的全排列的个数 < k,说明第k个数肯定不在当前分支i
            if (cnt < k) {
                k -= cnt;
            // 如果分支的全排列的个数 > k,说明第k个数肯定在当前分支i，将i加入path
            } else {
                path.append(i);
                used[i] = true;
                dfs(n, k, index + 1, factorial, path, used);
                // 注意 1：不可以回溯（重置变量），算法设计是「一下子来到叶子结点」，没有回头的过程
                // 注意 2：这里要加 return，后面的数没有必要遍历去尝试了，剪枝更彻底
                return;
            }

        }
    }






    //--------------------解法二：循环替代回溯，降低空间复杂度-----------------------
    public String getPermutation1(int n, int k) {
        final int[] arr = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};
        boolean[] visited = new boolean[n + 1];
        StringBuilder permutation = new StringBuilder();
        for (int i = n - 1; i >= 0; i--) {
            int cnt = arr[i];
            for (int j = 1; j <= n; j++) {
                if (visited[j]) {
                    continue;
                }
                if (k > cnt) {
                    k -= cnt;
                    continue;
                }
                visited[j] = true;
                permutation.append(j);
                break;
            }
        }
        return permutation.toString();
    }

}

// 17. 电话号码的字母组合 https://leetcode.cn/problems/letter-combinations-of-a-phone-number/
class letterCombinations {
    // 前两个空串负责占位，因为abc对应的是2
    String[] letter_map = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    public List<String> letterCombinations(String digits) {
        //注意边界条件
        if(digits==null || digits.length()==0) {
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        dfs(digits, res, new StringBuilder(), 0);
        return res;
    }

    private void dfs(String str, List<String> res, StringBuilder path, int index) {
        // 递归的终止条件
        if(index == str.length()) {
            res.add(path.toString());
            return;
        }
        // 获取index位置的数字
        char c = str.charAt(index);
        // 根据该数字获得对应的所有字母
        String map_string = letter_map[c - '0'];
        // 依次该数字对应的所有字母
        for (int i = 0; i < map_string.length(); i++) {
            path.append(map_string.charAt(i));
            dfs(str, res, path, index + 1);
            path.deleteCharAt(path.length() - 1);  // 回溯操作
        }
    }
}

// 22. 括号生成 https://leetcode.cn/problems/generate-parentheses/
class generateParenthesis {
    List<String> res = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        dfs(n, n, "");
        return res;
    }

    private void dfs(int left, int right, String curStr) {
        if (left == 0 && right == 0) { // 左右括号都不剩余了，递归终止
            res.add(curStr);
            return;
        }

        if (left > 0) { // 如果左括号还剩余的话，可以拼接左括号
            dfs(left - 1, right, curStr + "(");
        }
        if (right > left) { // 如果右括号剩余多于左括号剩余的话，可以拼接右括号
            dfs(left, right - 1, curStr + ")");
        }
    }

}









