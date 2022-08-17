package DataStructure;

import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 19:30 2022/3/25
 * @E-mail: 15611562852@163.com
 */

// 48. 旋转图像 https://leetcode-cn.com/problems/rotate-image/comments/
class rotate {
    // 先转置(左上-右下对角线对称)，后镜像对称（竖直对称线）
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
}

// 769. 最多能完成排序的块 https://leetcode-cn.com/problems/max-chunks-to-make-sorted/
class maxChunksToSorted {
    public int maxChunksToSorted(int[] arr) {
        //当遍历到第i个位置时，如果可以切分为块，那前i个位置的最大值一定等于i。
        //否则，一定有比i小的数划分到后面的块，那块排序后，一定不满足升序。
        int res = 0, max = 0;
        for (int i = 0; i < arr.length; i++){
            max = Math.max(max, arr[i]);//统计前i个位置的最大元素
            if (max == i) res++;
        }
        return res;
    }
}

// 739. 每日温度 https://leetcode-cn.com/problems/daily-temperatures/comments/
class dailyTemperatures {
    public int[] dailyTemperatures(int[] temperatures) {
        int length = temperatures.length;
        int[] res = new int[length - 1];
        res[length - 1] = 0;
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                if (temperatures[i] < temperatures[j]) {
                    // 不能直接res[i]=1,因为可能i与j不相邻
                    res[i] = j - i;
                    break;
                }
                // 优化操作，没有以下else判断也能AC
                // temperatures[i] >= temperatures[j]
                else {
                    // 判断后面是否也有比temperatures[j]大的
                    // 没有：res[i] = 0；有：二层循环遍历找到比temperatures[i]大的
                    if (res[j] == 0) {
                        res[i] = 0;
                        break;
                    }
                }
            }
        }
        return res;
    }

}

// 149. 直线上最多的点数 https://leetcode-cn.com/problems/max-points-on-a-line/
class maxPoints {
    public int maxPoints(int[][] points) {
        if(points.length <= 2)return points.length;
        int res = 0;
        for(int i = 0; i < points.length - 2; i++){
            for(int j = i + 1; j < points.length - 1; j++){
                // 最少两个点在一条直线上，两点确定一条直线
                int count = 2;
                // 计算第一个点和第二个点的横纵截距
                int x1 = points[i][0] - points[j][0];
                int y1 = points[i][1] - points[j][1];
                for(int k = j + 1; k < points.length; k++){
                    // 计算第一个点和第三个点的横纵截距
                    int x2 = points[i][0] - points[k][0];
                    int y2 = points[i][1] - points[k][1];
                    // 如果两个截距交叉乘积相等说明在一条直线上
                    if(x1 * y2 == x2 * y1) count++;
                }
                // 统计每两个点的斜率上最多的点数
                res = Math.max(res, count);
            }
        }
        return res;
    }
}

// 303. 区域和检索 - 数组不可变 https://leetcode-cn.com/problems/range-sum-query-immutable/
class NumArray {

    // 存储数组前缀和
    private int[] sums;

    public NumArray(int[] nums) {
        sums = new int[nums.length];
        if (nums.length == 0) {
            return;
        }
        // 防止数组越界
        sums[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sums[i] = sums[i - 1] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        // 注意left=0的情况，否则sums[left - 1]数组越界
        if (left == 0) {
            return sums[right];
        } else {
            return sums[right] - sums[left - 1];
        }
    }
}

// 304. 二维区域和检索 - 矩阵不可变 https://leetcode-cn.com/problems/range-sum-query-2d-immutable/solution/ru-he-qiu-er-wei-de-qian-zhui-he-yi-ji-y-6c21/
class NumMatrix {
    // 二维前缀和解法
    // 1. 定义二维前缀和 sums[i, j]: 第i行第j列格子左上部分所有元素之和(包含第i行第j列格子元素)
    // 2. 前缀和计算公式 sums[i][j] = sums[i - 1][j] + sums[i][j - 1] - sums[i - 1][j - 1] + sums[i][j]
    // 3. 以(x1, y1)为左上角, (x2, y2)为右下角的子矩阵的和为：
    // sums[x2, y2] - sums[x1 - 1, y2] - sums[x2, y1 - 1] + sums[x1 - 1, y1 - 1]
    private int[][] sums;
    public NumMatrix(int[][] matrix) {

        // 处理输入数组为空集
        if (matrix.length == 0)
            return;
        int row = matrix.length;
        int col = matrix[0].length;

        // 存储二维前缀和
        sums = new int[row + 1][col + 1];

        // 利用前缀和公式计算前缀和 sum[1][1] = 0+0-0+matrix[0][0] = matrix[0][0]
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                sums[i][j] = sums[i - 1][j] + sums[i][j - 1] - sums[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }
    }

    // 前缀和公式计算 - 根据原来公式需要做一定变换
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sums[row2 + 1][col2 + 1] - sums[row2 + 1][col1] - sums[row1][col2 + 1] + sums[row1][col1];
    }
}

// 566. 重塑矩阵 https://leetcode-cn.com/problems/reshape-the-matrix/
class matrixReshape {
    public int[][] matrixReshape(int[][] mat, int r, int c) {
        int height = mat.length;
        int width = mat[0].length;
        if (height * width != r * c) {
            return mat;
        }
        int[][] res = new int[r][c];
        for (int i = 0; i < height * width; i++) {
            res[i / c][i % c] = mat[i / width][i % width];
        }
        return res;
    }
}

// 剑指 Offer 04. 二维数组中的查找 https://leetcode.cn/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/
class findNumberIn2DArray {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        // 我们可以从右上角开始查找，若当前值大于待搜索值，我们向左移动一位；
        // 若当前值小于待搜索值，我们向下移动一位。
        // 如果最终移动到左下角时仍不等于待搜索值，则说明待搜索值不存在于矩阵中

        int row = matrix.length;
        int column = matrix[0].length;
        // 右上角开始
        int i = 0, j = column - 1;
        while (i < row && j >= 0) {
            int num = matrix[i][j];
            // 如果 target 比当前数小,向下一行
            if (target < num) {
                j--;
            }
            // 如果 target 比当前数大,向左一列
            else if (target > num) {
                i++;
            } else {
                return true;
            }
        }
        return false;
    }
}

// 36. 有效的数独 https://leetcode.cn/problems/valid-sudoku/
class isValidSudoku {

    public boolean isValidSudoku(char[][] board) {

        // 记录某行，某位数字是否已经被摆放
        boolean[][] row = new boolean[9][9];
        // 记录某列，某位数字是否已经被摆放
        boolean[][] col = new boolean[9][9];
        // 记录某 3x3 宫格内，某位数字是否已经被摆放
        boolean[][] block = new boolean[9][9];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    int blockIndex = i / 3 * 3 + j / 3;
                    if (row[i][num] || col[j][num] || block[blockIndex][num]) {
                        return false;
                    }
                    row[i][num] = true;
                    col[j][num] = true;
                    block[blockIndex][num] = true;
                }
            }
        }
        return true;
    }
}

// 41. 缺失的第一个正数 https://leetcode.cn/problems/first-missing-positive/
class firstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        int[] temp = new int[nums.length];
        for (int num : nums) {
            if (num >= 1 && num <= nums.length) {
                temp[num - 1] = num;
            }
        }

        int i;
        for (i = 0; i < nums.length; i++) {
            if (temp[i] != (i + 1)) {
                break;
            }
        }

        return i + 1;
    }
}

// 448. 找到所有数组中消失的数字 https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array/
class findDisappearedNumbers {
    // 将所有正数作为数组下标，置对应数组值为负值。那么，仍为正数的位置即为（未出现过）消失的数字
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            if (nums[index] > 0) {
                nums[index] = - nums[index];
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                results.add(i + 1);
            }
        }
        return results;
    }
}

// 54. 螺旋矩阵 https://leetcode.cn/problems/spiral-matrix/
class spiralOrder {
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;
        ArrayList<Integer> ans = new ArrayList<>();
        // 注意要加上=
        while (top <= bottom && left <= right) {
            // 上
            for (int i = left; i <= right; i++) {
                ans.add(matrix[top][i]);
            }
            top++;
            // 右
            for (int i = top; i <= bottom; i++) {
                ans.add(matrix[i][right]);
            }
            right--;
            // 下
            // 注意需要多判断 top <= bottom
            for (int i = right; i >= left && top <= bottom; i--) {
                ans.add(matrix[bottom][i]);
            }
            bottom--;
            // 左
            // 注意需要多判断 left <= right
            for (int i = bottom; i >= top && left <= right; i--) {
                ans.add(matrix[i][left]);
            }
            left++;
        }
        return ans;
    }
}

// 59. 螺旋矩阵 II https://leetcode.cn/problems/spiral-matrix-ii/
class generateMatrix {
    // 思路类似54
    public int[][] generateMatrix(int n) {
        int[][] ans = new int[n][n];
        int top = 0, bottom = n - 1;
        int left = 0, right = n - 1;
        int num = 1;
        while (top <= bottom && left <= right) {
            // 上
            for (int i = left; i <= right; i++) {
                ans[top][i] = num++;
            }
            top++;
            for (int i = top; i <= bottom; i++) {
                ans[i][right] = num++;
            }
            right--;
            // 下
            for (int i = right; i >= left && top <= bottom; i--) {
                ans[bottom][i] = num++;
            }
            bottom--;
            // 左
            for (int i = bottom; i >= top && left <= right; i--) {
                ans[i][left] = num++;
            }
            left++;
        }
        return ans;

    }
}

// 56. 合并区间 https://leetcode.cn/problems/merge-intervals/
class merge {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return null;
        }
        // 将所有区间按照左边界递增排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        ArrayList<int[]> list = new ArrayList<>();
        int length = intervals.length;
        int index = 0;
        // 遍历所有排序后的区间
        while (index < length) {
            int left = intervals[index][0];
            int right = intervals[index][1];
            // 如果当前区间右边界 >= 下一个区间的左边界（注意必须有=，否则两个紧挨的区间不合并）
            while (index < length - 1 && right >= intervals[index + 1][0]) {
                // 更新当前有边界
                right = Math.max(right, intervals[index + 1][1]);
                index++;
            }
            list.add(new int[]{left, right});
            index++;
        }
        // list 转 array
        // 注意此处不能使用 (int [])list.toArray()
        return list.toArray(new int[list.size()][2]);
    }

}

// 57. 插入区间 https://leetcode.cn/problems/insert-interval/
class insert {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        ArrayList<int[]> list = new ArrayList<>();
        int length = intervals.length;
        int index = 0;
        // 添加所有与新区间左边界不重叠的区间（区间右边界 < 新区间左边界），不需要=，否则需要合并区间
        while (index < length && intervals[index][1] < newInterval[0]) {
            list.add(intervals[index]);
            index++;
        }
        // 左端取左端的较小者，右端取右端的较大者，不断更新给新区间，需要=，此处需合并区间
        while (index < length && intervals[index][0] <= newInterval[1]) {
            newInterval[0] = Math.min(intervals[index][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[index][1], newInterval[1]);
            index++;
        }
        list.add(newInterval);
        // 添加右侧不重叠区间（区间左边界 > 新区间右边界），不需要=，否则需要合并区间
        while (index < length && intervals[index][0] > newInterval[1]) {
            list.add(intervals[index]);
            index++;
        }
        return list.toArray(new int[list.size()][2]);
    }
}
























