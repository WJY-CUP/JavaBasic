import com.sun.jmx.snmp.ThreadContext;

import java.util.*;
import java.util.function.Consumer;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 19:47 2021/7/3
 * @E-mail: 15611562852@163.com
 */

public class MySort {

    public void quickSort(int[] nums, int left, int right) {
        if (left + 1 >= right) {
            return;
        }
        int l = left, r = right - 1, index = nums[left];
        while (l < r) {
            while (l < r && nums[r] > index) {
                r--;
            }
            nums[l] = nums[r];
            while (l < r && nums[l] < index) {
                l++;
            }
            nums[r] = nums[l];
        }
        nums[l] = index;
        quickSort(nums, left, l);
        quickSort(nums, l+1,right);
    }

    // 求第K大数字
    public static int findKthLargest(int[] nums, int k) {
        int right = nums.length - 1;
        int index = nums.length - k;
        int left = 0;
        return quickSelect(nums, left, right, index);
    }

    public static int quickSelect(int[] nums, int left, int right, int index) {
        int q = randomPartition(nums, left, right);
        if (q == index) {
            return nums[q];
        } else {
            return q < index ? quickSelect(nums, q + 1, right, index)
                    : quickSelect(nums, left, q - 1, index);
        }
    }

    public static int randomPartition(int[] nums, int l, int r) {
        Random random = new Random();
        int randomNum = random.nextInt(r - l + 1) + l;
        // int picked = (int) (Math.random() * (end - start + 1)) + start;
        swap(nums,randomNum,r);

        int x = nums[r];
        int i = l - 1;
        for (int j = l; j < r; j++) {
            if (nums[j] < x) {
                swap(nums,++i,j);
            }
        }
        swap(nums, i + 1, r);
        return i + 1;
    }

    private static void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{6, 4, 5, 8, 9, 1, 2, 3, 12, 45};
        int[] nums1 = new int[]{1,2,3,4,5,6,7,8,9,10};

        System.out.println(findKthLargest(nums1,3));
        double random = Math.random();


    }
}


// 求前k高频率数字
class topKFrequentK {

    public List<Integer> topKFrequent01(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // 解法一：优先队列
        // PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));
        // for (int key : map.keySet()) {
        //     queue.offer(key);
        //     if (queue.size() > k) {
        //         queue.poll();
        //     }
        // }
        // int[] res = new int[k];
        // for (int i = 0; i < k; i++) {
        //     res[i] = queue.poll();
        // }
        // return res;

        // 解法二：桶排序
        // 将频率作为数组下标，对于出现频率不同的数字集合，存入对应的数组下标
        List<Integer>[] list = new List[nums.length+1];
        for(int key : map.keySet()){
            // 获取出现的次数作为下标
            int i = map.get(key);
            if(list[i] == null){
                list[i] = new ArrayList();
            }
            list[i].add(key);
        }

        List<Integer> res = new ArrayList();
        // 倒序遍历数组获取出现顺序从大到小的排列
        for(int i = list.length - 1;i >= 0 && res.size() < k;i--){
            if(list[i] == null) continue;
            res.addAll(list[i]);
        }
        return res;
    }
}

// 451. 根据字符出现频率排序 https://leetcode.cn/problems/sort-characters-by-frequency/
class frequencySort {
    public String frequencySort(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : chars) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        ArrayList<Character> list = new ArrayList<>(map.keySet());
        Collections.sort(list, (o1, o2) -> map.get(o2) - map.get(o1));

        StringBuffer res = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Character c = list.get(i);
            for (int j = 0; j < map.get(c); j++) {
                res.append(c);
            }
        }
        return res.toString();
    }
}

// 75. 颜色分类 https://leetcode-cn.com/problems/sort-colors/solution/yan-se-fen-lei-by-leetcode-solution/
class sortColors {

    // 单指针（该放的位置），先把所有0放到前面该放的位置，再把1放到前面该放的位置
    public void sortColors01(int[] nums) {
        int ptr = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                swap(nums, i, ptr);
                ptr++;
            }
        }
        for (int i = ptr; i < nums.length; i++) {
            if (nums[i] == 1) {
                swap(nums, i, ptr);
                ptr++;
            }
        }
    }

    // 桶排序(two iterations)
    public void sortColors03(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int a : nums) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < map.get(i); j++) {
                nums[index++] = i;
            }
        }
    }

    // 三指针
    public void sortColors04(int[] nums) {
        // zero:0该放的位置，i：当前遍历位置也即1该放的位置，two：2该放的位置
        int zero = 0, i = 0, two = nums.length-1;
        // 因为有可能全是0或者全是2，所以需要 “=”
        while (i <= two) {
            if (nums[i] == 0) {
                swap(nums, i, zero);
                // i之前的肯定都是0、1，所以交换之后无需判断位置i的数字，肯定是1，所以直接 i++
                i++;
                zero++;
            } else if (nums[i] == 1) {
                i++;
            } else {
                swap(nums, i, two);
                two--;
                // 因为i后的序列无序，交换过来的可能是0，所以不能 i++
            }
        }
    }


    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}

// 十大排序算法，默认从小到大  https://www.runoob.com/w3cnote/ten-sorting-algorithm.html
class Sort {

    // 冒泡排序
    public int[] bubbleSort(int[] nums) {
        int length = nums.length;
        // 当剩下最小的数时，不用交换了，总共要经过 N-1 轮比较
        for (int i = 0; i < length - 1; i++) {
            // 设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
            boolean flag = true;
            // 默认从小到大，每轮都能确定未排序数字中最大的并将其放到最后，所以比较次数 j 越来越少
            for (int j = 0; j < length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    // 如果发生了交换，说明排序未结束
                    flag = false;
                }
            }
            // 剪枝：如果flag未被改变过，说明剩余待排部分已有序，排序结束
            if (flag) {
                break;
            }
        }
        return nums;
    }

    // 选择排序：先定义一个记录最小元素的下标，然后循环一次后面的，找到最小的元素，最后将他放到前面排序好的序列。
    public int[] selectSort(int[] nums) {
        int length = nums.length;
        // 当剩下最大的数时，不用交换了，总共要经过 N-1 轮比较
        for (int i = 0; i < length - 1; i++) {
            // 标记当前数为待比较的数
            int minIndex = i;
            // 每轮需要比较的次数 N-i
            for (int j = i + 1; j < length; j++) {
                // 找到未排序部分中最小的数，并记录其下标
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            // 将找到的最小值和i位置所在的值进行交换
            int temp = nums[i];
            nums[i] = nums[minIndex];
            nums[minIndex] = temp;
        }
        return nums;
    }

    // 插入排序：类似打扑克挨个调整牌序
    public int[] insertSort(int[] nums) {
        // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        for (int i = 1; i < nums.length; i++) {

            // 记录要插入的数据
            int temp = nums[i];

            // 从已经排序的序列最右边的开始比较，找到比其小的数
            int j = i;
            while (j > 0 && temp < nums[j - 1]) {
                nums[j] = nums[j - 1];
                j--;
            }

            // 将要插入的数据插入空出来的位置
            nums[j] = temp;
        }        
        return nums;
    }

    // 希尔排序：插入排序+分治
    public int[] shellSort(int[] nums) {
        int length = nums.length;

        // 步长每轮减半
        for (int step = length / 2; step >= 1; step /= 2) {

            // 对各个子序列进行插入排序
            for (int i = step; i < length; i++) {

                // 记录要插入的数据
                int temp = nums[i];
                // 从已经排序的序列最右边的开始比较，找到比其小的数
                int j = i - step;
                while (j >= 0 && nums[j] > temp) {
                    nums[j + step] = nums[j];
                    j -= step;
                }
                nums[j + step] = temp;
            }
        }        
        return nums;
    }

    // 快速排序：冒泡排序+分治+递归
    public void quickSort(int[] nums, int low, int high) {
        if (low > high) {
            return;
        }
        int i = low;
        int j = high;

        //基准位,low=length时，会报异常，java.lang.ArrayIndexOutOfBoundsException: 4 ，所以必须在if判断后面,就跳出方法。
        int temp = nums[low];
        while (i < j) {
            //先从右边开始往左递减，找到比temp小的值才停止
            while (temp <= nums[j] && i < j) {
                j--;
            }
            //再看左边开始往右递增，找到比temp大的值才停止
            while (temp >= nums[i] && i < j) {
                i++;
            }
            //满足 i<j 就交换,继续循环while(i<j),右小与左大交换
            if (i < j) {
                int t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
            }
        }

        // num[i]与num[low]进行交换,此时i=j
        nums[low] = nums[i];
        nums[i] = temp;


        //左递归
        quickSort(nums, low, j-1);
        //右递归
        quickSort(nums, j+1, high);

    }

    // 归并排序：分治+递归
    public void mergeSort(int[] nums, int left, int right, int[] temp) {
        //分解
        if (left < right) {
            int mid = (left + right) / 2;
            //向左递归进行分解
            mergeSort(nums, left, mid, temp);
            //向右递归进行分解
            mergeSort(nums, mid + 1, right, temp);
            //每分解一次便合并一次
            merge(nums, left, right, mid, temp);
        }
    }
    /**
     *
     * @param nums  待排序的数组
     * @param left  左边有序序列的初始索引
     * @param right 右边有序序列的初始索引
     * @param mid	中间索引
     * @param temp	做中转的数组
     */
    private void merge(int[] nums, int left, int right, int mid, int[] temp) {
        int i = left; //初始i，左边有序序列的初始索引
        int j = mid + 1;//初始化j，右边有序序列的初始索引（右边有序序列的初始位置即中间位置的后一位置）
        int t = 0;//指向temp数组的当前索引，初始为0

        //先把左右两边的数据（已经有序）按规则填充到temp数组
        //直到左右两边的有序序列，有一边处理完成为止
        while (i <= mid && j <= right) {
            //如果左边有序序列的当前元素小于或等于右边的有序序列的当前元素，就将左边的元素填充到temp数组中
            if (nums[i] <= nums[j]) {
                temp[t] = nums[i];
                t++;//索引向后移
                i++;//i后移
            } else {
                //反之，将右边有序序列的当前元素填充到temp数组中
                temp[t] = nums[j];
                t++;//索引向后移
                j++;//j后移
            }
        }

        //把剩余数据的一边的元素填充到temp中
        while (i <= mid) {
            //此时说明左边序列还有剩余元素
            //全部填充到temp数组
            temp[t] = nums[i];
            t++;
            i++;
        }
        while (j <= right) {
            //此时说明左边序列还有剩余元素
            //全部填充到temp数组
            temp[t] = nums[j];
            t++;
            j++;
        }

        //将temp数组的元素复制到原数组
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            nums[tempLeft] = temp[t];
            t++;
            tempLeft++;
        }
    }




    public static void main(String[] args) {
        int[] nums = new int[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48,5,100,4,143,5};
        Sort sort = new Sort();
        
        int[] nums1 = Arrays.copyOf(nums, nums.length);
        Arrays.sort(nums1);

        System.out.println("Java排序 = " + Arrays.toString(nums1));
        // System.out.println("冒泡排序 = " + Arrays.toString(sort.bubbleSort(nums)));
        // System.out.println("选择排序 = " + Arrays.toString(sort.selectSort(nums)));
        // System.out.println("插入排序 = " + Arrays.toString(sort.insertSort(nums)));
        // System.out.println("希尔排序 = " + Arrays.toString(sort.shellSort(nums)));
        // sort.quickSort(nums, 0, nums.length - 1);
        // System.out.println("快速排序 = " + Arrays.toString(nums));

        int temp[] = new int[nums.length];
        sort.mergeSort(nums, 0, nums.length - 1, temp);
        System.out.println("归并排序 = " + Arrays.toString(nums));

    }


}

// 33. 搜索旋转排序数组 https://leetcode.cn/problems/search-in-rotated-sorted-array/
class search {
    public int search(int[] nums, int target) {
        int len = nums.length;
        if(len == 0) return -1;
        int left = 0, right = len - 1;
        // 1. 首先明白，旋转数组后，从中间划分，一定有一边是有序的。
        // 2. 由于一定有一边是有序的，所以根据有序的两个边界值来判断目标值在有序一边还是无序一边
        // 3. 这题找目标值，遇到目标值即返回
        // 4. 注意：由于有序的一边的边界值可能等于目标值，所以判断目标值是否在有序的那边时应该加个等号(在二分查找某个具体值得时候如果把握不好边界值，可以再每次查找前判断下边界值，也就是while循环里面的两个if注释)
        while(left <= right){

            int mid = left + (right - left) / 2;
            if(nums[mid] == target) return mid;

            // 右边有序
            if(nums[mid] < nums[right]){
                // 目标值在右边  特例：8 9 1 2 3 4 5 6 7  target=8，所以需要加上 "&& target <= nums[right]"
                if(nums[mid] < target && target <= nums[right]){
                    left = mid + 1;
                    // 目标值在左边
                }else{
                    right = mid - 1;
                }
                // 左边有序
            }else{
                // 目标值在左边  特例：3 4 5 6 7 8 9 1 2   target=1，所以需要加上 "&& nums[left] <= target"
                if(nums[left] <= target && target < nums[mid]){
                    right = mid - 1;
                    // 目标值在右边
                }else{
                    left = mid + 1;
                }
            }
        }
        return -1;
    }
}


class search1 {
    public int search(int[] nums, int target) {
        int len = nums.length;
        if(len == 0) return -1;
        int left = 0, right = len - 1;
        // 1. 首先明白，旋转数组后，从中间划分，一定有一边是有序的。
        // 2. 由于一定有一边是有序的，所以根据有序的两个边界值来判断目标值在有序一边还是无序一边
        // 3. 这题找目标值，遇到目标值即返回
        // 4. 注意：由于有序的一边的边界值可能等于目标值，所以判断目标值是否在有序的那边时应该加个等号(在二分查找某个具体值得时候如果把握不好边界值，可以再每次查找前判断下边界值，也就是while循环里面的两个if注释)
        while(left <= right){

            int mid = left + (right - left) / 2;
            if(nums[mid] == target) return mid;
            // 分不清是否有序，所以从两端开始去除重复值即可（以下两个if判断是与33的主要区别）
            // 注意这里不能用while， 原因有二，一是用while，如果全是一样的数字时间复杂度退化为o(n)了。
            // 二是如果一直left++，right--，最终会影响mid停留的位置与二分法计算的不符（这里需里自己调试理解）,
            // 导致下面的二分查找法失效，结果出错！所以必须，每移动一次就跳出循环，重新计算mid的值。
            if(nums[left] == nums[mid]) {
                left++;
                continue;
            }
            if (nums[right] == nums[mid]) {
                right--;
                continue;
            }

            // 右边有序
            if(nums[mid] < nums[right]){
                // 目标值在右边  特例：8 9 1 2 3 4 5 6 7  target=8，所以需要加上 "&& target <= nums[right]"
                if(nums[mid] < target && target <= nums[right]){
                    left = mid + 1;
                    // 目标值在左边
                }else{
                    right = mid - 1;
                }
                // 左边有序
            }else{
                // 目标值在左边  特例：3 4 5 6 7 8 9 1 2   target=1，所以需要加上 "&& nums[left] <= target"
                if(nums[left] <= target && target < nums[mid]){
                    right = mid - 1;
                    // 目标值在右边
                }else{
                    left = mid + 1;
                }
            }
        }
        return -1;
    }
}





// 剑指 Offer 11. 旋转数组的最小数字 https://leetcode.cn/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof/
class minArray {
    public int minArray(int[] numbers) {
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            int mid = ((r - l) >> 1) + l;
            //只要右边比中间大，那右边一定是有序数组
            if (numbers[r] > numbers[mid]) {
                r = mid;
            } else if (numbers[r] < numbers[mid]) {
                l = mid + 1;
                //去重
            } else r--;
        }
        return numbers[l];
    }
}

// 34. 在排序数组中查找元素的第一个和最后一个位置 https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/comments/
class searchRange {
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        res[0] = binarySearch(nums, target, true);
        res[1] = binarySearch(nums, target, false);
        return res;
    }

    public int binarySearch(int[] nums, int target, boolean leftOrRight) {
        int res = -1;
        int left = 0, right = nums.length - 1, mid;
        while (left <= right) {
            mid = (right - left) / 2 + left;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                res = mid;
                // 如果寻找左边界，向左扩展
                if (leftOrRight) {
                    right = mid - 1;
                // 如果寻找右边界，向右扩展
                } else {
                    left = mid + 1;
                }
            }
        }
        return res;
    }

}

// 35. 搜索插入位置 https://leetcode.cn/problems/search-insert-position/
class searchInsert {
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid;
        while (left <= right) {
            mid = (right - left) / 2 + left;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return right + 1;
    }
}






























