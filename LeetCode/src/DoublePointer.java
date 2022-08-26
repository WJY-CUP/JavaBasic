
import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 18:48 2021/9/26
 * @E-mail: 15611562852@163.com
 */

// 1. 两数之和返回两数的数组下标 https://leetcode-cn.com/problems/two-sum/
class twoSum1 {

    // 暴力
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    // Map
    public int[] twoSum1(int[] nums, int target) {
        // key:nums value  value:index
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            // 首先查询哈希表中是否存在 target - x，然后将 x 插入到哈希表中，即可保证不会让 x 和自己匹配
            // 例如[3,2,4],target=6。防止结果为[0,0]
            if (hashMap.containsKey(target - nums[i])) {
                return new int[]{hashMap.get(target - nums[i]), i};
            }
            hashMap.put(nums[i], i);
        }
        return new int[0];
    }

}

// 15. 三数之和 https://leetcode-cn.com/problems/3sum/
class DoublePointer {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length <= 2) return ans;

        Arrays.sort(nums); // O(nlogn)

        // 注意终点为 nums.length - 2
        for (int i = 0; i < nums.length - 2; i++) { // O(n^2)
            if (nums[i] > 0) break; // 第一个数大于 0，后面的数都比它大，肯定不成立了
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去掉重复情况

            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;

            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    ans.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 现在要增加 left，减小 right，但是不能重复，
                    left++; right--; // 首先无论如何先要进行加减操作
                    // 比如: [-2, -1, -1, -1, 3, 3, 3], i = 0, left = 1, right = 6, [-2, -1, 3] 的答案加入后，需要排除重复的 -1 和 3
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (nums[left] + nums[right] < target) {
                    left++;
                } else {  // nums[left] + nums[right] > target
                    right--;
                }
            }
        }
        return ans;
    }

}

// 18. 四数之和 https://leetcode.cn/problems/4sum/
class fourSum {
    public List<List<Integer>> fourSum(int[] nums, int target) { // O(n^3)
        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(nums); // O(nlogn)
        int len = nums.length;
        // 注意终点为 nums.length - 3！
        for (int i = 0; i < len - 3; i++) {   // O(n^3)
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 跳过重复
            }
            // 注意终点为 nums.length - 2！
            for (int j = i + 1; j < len - 2; j++) { // same as threeSum  O(n^2)
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue; // 跳过重复
                }
                int left = j + 1;
                int right = len - 1;
                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        // 现在要增加 left，减小 right，但是不能重复，
                        left++; right--; // 首先无论如何先要进行加减操作
                        while (left < right && nums[left - 1] == nums[left]) left++;
                        while (left < right && nums[right + 1] == nums[right]) right--;
                    } else if (sum > target) {
                        right--;
                    } else {
                        left++;
                    }
                }
            }
        }

        return res;
    }

}

// 16. 最接近的三数之和 https://leetcode.cn/problems/3sum-closest/
class threeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int closestNum = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int l = i + 1, r = nums.length - 1;
            while (l < r){
                int threeSum = nums[l] + nums[r] + nums[i];
                if (Math.abs(threeSum - target) < Math.abs(closestNum - target)) {
                    closestNum = threeSum;
                }
                if (threeSum > target) {
                    r--;
                } else if (threeSum < target) {
                    l++;
                } else {
                    // 如果已经等于target的话, 肯定是最接近的
                    return target;
                }
            }
        }
        return closestNum;
    }
}

// 31. 下一个排列 https://leetcode.cn/problems/next-permutation/solution/xia-yi-ge-pai-lie-suan-fa-xiang-jie-si-lu-tui-dao-/
class nextPermutation {
    public void nextPermutation(int[] nums) {
        int len = nums.length;
        if (len <= 1) {
            return;
        }
        //找到第一对相邻升序
        for (int i = len - 1; i >= 1; i--) {
            if (nums[i] > nums[i - 1]) {
                //找到最右边大于nums[i-1]的数，并交换
                for (int j = len - 1; j >= i; j--) {
                    if (nums[j] > nums[i - 1]) {
                        int tmp = nums[i - 1];
                        nums[i - 1] = nums[j];
                        nums[j] = tmp;
                        break;
                    }
                }
                //将后面降序变为升序
                Arrays.sort(nums, i, len);
                return;
            }
        }
        // 如果没找到相邻升序，说明已经最大，比如4321，直接返回1234即可
        Arrays.sort(nums);
    }
}

// 413. 等差数列个数 https://leetcode-cn.com/problems/arithmetic-slices/
class numberOfArithmeticSlices {
    /**
     * 从小的例子出发,仔细观察,会发现当整个数组为(1, 2, 3, 4, 5, 6)的时候,我们先取出前三个,(1, 2, 3)的等差数列的个数为1,(1, 2, 3, 4)的等差数列的个数为3,
     * (1, 2, 3, 4, 5)的等差数列的个数为6,(1, 2, 3, 4, 5, 6)的等差数列个数为10,
     * 以此类推我们可以很容易的发现在一个等差数列中加入一个数字,如果还保持着等差数列的特性,每次的增量都会加1,
     * 如果刚加进来的数字与原先的序列构不成等差数列,就将增量置为0,接下来继续循环,执行以上的逻辑即可.可以发现,这道题只要找到规律还是相当的简单的
     */
    //--------------------------解法一：找规律--------------------------
    public int numberOfArithmeticSlices1(int[] nums) {
        if (nums == null || nums.length <= 2)
            return 0;
        int res = 0;
        int add = 0;
        for (int i = 2; i < nums.length; i++)
            if (nums[i - 1] - nums[i] == nums[i - 2] - nums[i - 1])
                res += ++add;
            else
                add = 0;
        return res;
    }

    //--------------------------解法二：双指针（滑动窗口）+等差数列求和--------------------------
    public int numberOfArithmeticSlices2(int[] nums) {
        int n = nums.length;
        int res = 0;
        for (int i = 0; i < n - 2; ) {
            int j = i, d = nums[i + 1] - nums[i];
            // 尾指针找到该子等差数列最右项
            while (j + 1 < n && nums[j + 1] - nums[j] == d) j++;
            int len = j - i + 1;
            // a1：长度为 len 的子数组数量；an：长度为 3 的子数组数量
            int a1 = 1, an = len - 3 + 1;
            // 符合条件（长度大于等于3）的子数组的数量为「差值数列求和」结果
            // (首项+末项) * 项数 / 2
            // 项数 = an-a1+1 = an
            int cnt = (a1 + an) * an / 2;
            res += cnt;
            i = j;
        }
        return res;
    }

    // --------------------------解法三：最简单：纯双指针（滑动窗口）--------------------------
    public int numberOfnumsrithmeticSlices3(int[] nums) {
        int N = nums.length;
        int res = 0;
        for (int i = 0; i < N - 2; i++) {
            int d = nums[i + 1] - nums[i];
            for (int j = i + 1; j < N - 1; j++) {
                if (nums[j + 1] - nums[j] == d)
                    res ++;
                else
                    break;
            }
        }
        return res;
    }






}

// 76. 最小覆盖子串 https://leetcode.cn/problems/minimum-window-substring/
class minWindow {
    public String minWindow(String s, String t) {
        if (s == null || s.length() == 0 || t == null || t.length() == 0){
            return "";
        }
        int[] need = new int[128];
        //记录需要的字符的个数
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }
        //l是当前左边界，r是当前右边界，size记录窗口大小，count是需求的字符个数，start是最小覆盖串开始的index
        int l = 0, r = 0, size = Integer.MAX_VALUE, count = t.length(), start = 0;
        //遍历所有字符
        while (r < s.length()) {
            char c = s.charAt(r);
            if (need[c] > 0) {//需要字符c
                count--;
            }
            need[c]--;//把右边的字符加入窗口
            if (count == 0) {//窗口中已经包含所有字符
                while (l < r && need[s.charAt(l)] < 0) {
                    need[s.charAt(l)]++;//释放右边移动出窗口的字符
                    l++;//指针右移
                }
                if (r - l + 1 < size) {//不能右移时候挑战最小窗口大小，更新最小窗口开始的start
                    size = r - l + 1;
                    start = l;//记录下最小值时候的开始位置，最后返回覆盖串时候会用到
                }
            }
            r++;
        }
        return size == Integer.MAX_VALUE ? "" : s.substring(start, start + size);

    }
}