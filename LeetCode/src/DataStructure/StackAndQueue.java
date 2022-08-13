package DataStructure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 11:06 2022/3/28
 * @E-mail: 15611562852@163.com
 */
// LinkedList实现了List和Deque接口。作为List使用时,采用add/get，作为Queue使用时,采用offer/poll


// 232. 用栈实现队列 https://leetcode-cn.com/problems/implement-queue-using-stacks/
class MyQueue {

    // 假设a在右，b在左，两个栈的开口均向左
    private Stack<Integer> a;
    private Stack<Integer> b;

    public MyQueue() {
        a = new Stack<>();
        b = new Stack<>();
    }

    public void push(int x) {
        a.push(x);
    }

    public int pop() {
        if (b.isEmpty()) {
            while (!a.isEmpty()) {
                b.push(a.pop());
            }
        }
        return b.pop();
    }

    public int peek() {
        if (b.isEmpty()) {
            while (!a.isEmpty()) {
                b.push(a.pop());
            }
        }
        return b.peek();
    }

    public boolean empty() {
        return a.isEmpty() && b.isEmpty();
    }
}

// 225. 用队列实现栈 https://leetcode-cn.com/problems/implement-stack-using-queues/
class MyStack {

    private Queue<Integer> a;//输入队列
    private Queue<Integer> b;//输出队列

    public MyStack() {
        // 假设a在左，b在右，队列方向向左，右进左出
        a = new LinkedList<>();
        b = new LinkedList<>();
    }

    public void push(int x) {
        // LinkedList实现了List和Deque接口。作为List使用时,采用add/get，作为Queue使用时,采用offer/poll
        a.offer(x);
        // 将b队列中元素全部转给a队列
        while(!b.isEmpty())
            a.offer(b.poll());
        // 交换a和b,使得a队列没有在push()的时候始终为空队列
        Queue temp = a;
        a = b;
        b = temp;
    }

    public int pop() {
        return b.poll();
    }

    public int top() {
        return b.peek();
    }

    public boolean empty() {
        return b.isEmpty();
    }
}

// 20. 有效的括号 https://leetcode-cn.com/problems/valid-parentheses/
class isValid {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c == '{') stack.push('}');
            else if (c == '[') stack.push(']');
            else if (c == '(') stack.push(')');
            // 预防 "]" 情况
            // 如果当前字符为 }])，在占中
            else if (stack.isEmpty() || c != stack.pop()) return false;
        }
        return stack.isEmpty();
    }

    public boolean isValid1(String s) {
        int length = s.length() / 2;
        // 循环字符串长度一半的次数
        for (int i = 0; i < length; i++) {
            s = s.replace("()", "").replace("{}", "").replace("[]", "");
        }
        return s.length() == 0;
    }
}

// 739. 每日温度 https://leetcode-cn.com/problems/daily-temperatures/comments/
class dailyTemperatures1 {
    // 单调栈（类似汉诺塔）中存储的是数组元素下标，并且对应元素值的规律是从栈底到栈顶单调递减
    // 一旦当前数字大于栈顶元素对应值，那么依次出栈比较，找到所有小于当前元素的值，计算下标距离
    public int[] dailyTemperatures(int[] T) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[T.length];
        for (int i = 0; i < T.length; i++) {
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                int temp = stack.pop();
                res[temp] = i - temp;
            }
            stack.push(i);
        }
        return res;
    }
}

// 503. 下一个更大元素 II （每日温度变种）https://leetcode-cn.com/problems/next-greater-element-ii/solution/dong-hua-jiang-jie-dan-diao-zhan-by-fuxu-4z2g/
class nextGreaterElements {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res,-1);
        // 从栈底到栈顶单调递减栈
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 2 * n; i++) {
            while (!stack.isEmpty() && nums[i % n] > nums[stack.peek()]) {
                res[stack.pop()] = nums[i % n];
            }
            stack.push(i % n);
        }
        return res;
    }
}

// 239. 滑动窗口最大值 https://leetcode-cn.com/problems/sliding-window-maximum/
class maxSlidingWindow {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        // 为了方便判断堆顶元素与滑动窗口的位置关系，我们可以在优先队列中存储二元组 (num,index)，表示元素num在数组中的下标为index
        // 使用大根堆
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] pair1, int[] pair2) {
                return pair1[0] != pair2[0] ? pair2[0] - pair1[0] : pair2[1] - pair1[1];
            }
        });
        // 将最开始的k个数字入队，初始化窗口
        for (int i = 0; i < k; ++i) {
            pq.offer(new int[]{nums[i], i});
        }
        // 滑动窗口大小为k，则有n-k+1个窗口
        int[] ans = new int[n - k + 1];
        // 最开始的窗口内的最大值为pq.peek()[0]
        ans[0] = pq.peek()[0];
        // 从第k个数开始遍历
        for (int i = k; i < n; ++i) {
            // 将当前遍历值入队
            pq.offer(new int[]{nums[i], i});
            // 如果当前最大值不在窗口范围内，将其出队
            while (pq.peek()[1] <= i - k) {
                pq.poll();
            }
            // 记录当前窗口内最大值
            ans[i - k + 1] = pq.peek()[0];
        }
        return ans;
    }
}

// 26. 删除有序数组中的重复项 https://leetcode.cn/problems/remove-duplicates-from-sorted-array/
class removeDuplicates {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // left:不重复序列的最后一个，right:当前遍历位置
        int left = 0, right = 1;
        // 如果遍历没有结束
        while (right < nums.length) {
            // 如果不重复序列的最后一个与当前遍历位置的两个数字不相等
            if (nums[left] != nums[right]) {
                // 并且两个数字不挨着，否则nums[right]原地复制没必要
                if (right - left > 1) {
                    // 将 当前遍历位置数字 放到 不重复序列最后位置的下一个
                    nums[left + 1] = nums[right];
                }
                // 该放的位置+1
                left++;
            }
            // 遍历位置+1
            right++;
        }
        return left + 1;
    }
}

// 27. 移除元素 https://leetcode.cn/problems/remove-element/
class removeElement {
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // left:不重复序列的最后一个，right:当前遍历位置
        int left = 0, right = 0;
        // 如果遍历没有结束
        while (right < nums.length) {
            // 如果val与当前遍历位置不相等
            if (val != nums[right]) {
                // 将 当前遍历位置数字 放到 不重复序列最后位置
                nums[left] = nums[right];
                // 该放的位置+1
                left++;
            }
            // 遍历位置+1
            right++;
        }
        return left;
    }
}

// 217. 判断数组中是否存在重复元素 https://leetcode-cn.com/problems/contains-duplicate/solution/chao-xiang-xi-kuai-lai-miao-dong-ru-he-p-sf6e/
class containsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num: nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }
        return false;
    }
}

// 剑指 Offer 03. 找出数组中重复的那个数字 https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof/
class findRepeatNumber {

    // //方法1：排序，时间O(nlogn)，空间O(logn)，修改了原数据
    // public int findRepeatNumber(int[] nums) {
    //     Arrays.sort(nums);
    //     for(int i = 0 ; i < nums.length-1 ;i++) {
    //         if(nums[i]==nums[i+1]) return nums[i];
    //     }
    //     return -1;
    // }

    //方法2：hash表，时间O(n)，空间O(n)，不修改原数据
    // public int findRepeatNumber(int[] nums) {
    //     HashSet<Integer> set = new HashSet<>();
    //     for(int num:nums){
    //         if(set.contains(num)) return num;
    //         set.add(num);
    //     }
    //     return -1;
    // }

    // //方法3：利用辅助数组，与方法2类似，时间O(n)，空间O(n)，不修改原数据
    // public int findRepeatNumber(int[] nums) {
    //     boolean[] isExist = new boolean[nums.length];
    //     for(int num : nums){
    //         if(isExist[num]) return num;
    //         isExist[num] = true;
    //     }
    //     return -1;
    // }

    // //方法4：利用索引与数字的关系，时间O(n)，空间O(1)，修改了原数据
    public int findRepeatNumber(int[] nums) {
        int i = 0;
        while(i < nums.length) {
            // 当前值与其索引对应，在正确位置上
            if(nums[i] == i) {
                i++;
                continue;
            }
            // 当前值与其索引不对应，在错误位置上
            // 且当前值与其正确位置上的数字相等，则重复
            if (nums[i] == nums[nums[i]]) {
                return nums[i];
            } else {
                // 当前值与其索引不对应，在错误位置上
                // 但是其正确位置上的数字与当前值不相等，交换其到正确位置上
                int tmp = nums[i];
                nums[i] = nums[nums[i]];
                // 将 num[i] 放到其正确位置上
                nums[tmp] = tmp;
            }
        }
        return -1;
    }

    // //方法5：对0到n-1进行二分查找，时间O(nlogn)，空间O(1)，不修改原数据，用时间换空间
    // 该方法需要数字一定有重复的才行，因此如果题目修改在长度为n，数字在1到n-1的情况下，此时数组中至少有一个数字是重复的，该方法可以通过。
    // public int findRepeatNumber(int[] nums) {
    //     //统计nums中元素位于0到m的数量，如果数量大于这个值，那么重复的元素肯定是位于0到m的
    //     int min = 0 ;
    //     int max = nums.length-1;
    //     while(min<max){
    //         int mid = (max+min)>>1;
    //         int count = countRange(nums,min,mid);
    //         if(count > mid-min+1) {
    //             max = mid;
    //         }else{
    //             min = mid+1;
    //         }
    //     }
    //     最后min=max
    //     return min;
    // }

    // //统计范围内元素数量,时间O(n)
    // private int countRange(int[] nums,int min,int max){
    //     int count = 0 ;
    //     for(int num:nums){
    //         if(num>=min && num<=max){
    //             count++;
    //         }
    //     }
    //     return count;
    // }


}

// 215. 数组中的第K个最大元素 https://leetcode.cn/problems/kth-largest-element-in-an-array/
class findKthLargest {

    // 方法一：容量为K的优先队列
    public int findKthLargest(int[] nums, int k) {
        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int num : nums) {
            heap.offer(num);
            //如果加入之后堆的大小 大于k，那么就弹出堆顶
            //这样的话每次加入之后最大的都在下面，弹出的都是小的数
            if (heap.size() > k) {
                heap.poll();
            }
        }
        //堆顶的数就是第k大的
        return heap.peek();
    }

    // 方法二：容量为n的优先队列，更好理解一点
    public int findKthLargest1(int[] nums, int k) {
        int len = nums.length;
        // 使用一个含有 len 个元素的最小堆，默认是最小堆，可以不写 lambda 表达式：(a, b) -> a - b
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(len, (a, b) -> a - b);
        // 将所有的元素添加到小根堆中
        for (int i = 0; i < len; i++) {
            minHeap.add(nums[i]);
        }
        // 例如有10个元素，第3大 = 第8小，弹出前 10-3=7个最小的
        for (int i = 0; i < len - k; i++) {
            minHeap.poll();
        }
        // 弹出第8小也即第3大
        return minHeap.peek();
    }
}


class CyclicArrayQueue<E> {
    // 队列头部，指向队列头的第一个位置。默认值为：0
    private int front;
    // 队列尾，指向队列尾的位置。默认值为：0
    private int rear;
    // 数据
    private E[] data;
    // 容量
    private int maxSize;

    /**
     * 构造函数
     * @param capacity
     */
    public CyclicArrayQueue(int capacity) {
        this.front = 0;
        this.rear = 0;
        // 预留一个判断是否队满。
        this.maxSize = capacity + 1;
        this.data = (E[])new Object[maxSize];
    }
    /**
     * 判断是否已经满
     * @return
     */
    public boolean isFull(){
        return (rear  + 1) % maxSize == front;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return rear == front;
    }

    /**
     * 获取大小
     * @return
     */
    public int size(){
        return (rear - front + maxSize) % maxSize;
    }

    /**
     * 添加元素
     * @param element
     * @return
     */
    public boolean add(E element){
        if(element == null){
            throw new NullPointerException("can not add element be null");
        }
        // 队列已经满
        if(isFull()){
            throw new IllegalStateException("Queue full");
        }
        data[rear] = element;
        rear = (rear + 1) % maxSize;
        return true;
    }

    /**
     * 返回队列中的第一个元素
     * @return
     */
    public E get(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        E element = data[front];
        front = (front + 1) % maxSize ;
        return element;
    }

    /**
     * 返回队列中的第一个元素
     * @return
     */
    public E peek(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        return data[front];
    }

    /**
     * 删除此队列的头并返回
     * @return
     */
    public E take(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        E takeValue = data[front];
        front = (front + 1) % maxSize ;
        return takeValue;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if(isEmpty()){
            return buffer.append("[]").toString();
        }

        buffer.append("[");
        for (int i = front; i < front + size(); i++) {
            buffer.append(data[i%maxSize]).append(",");
        }
        // 删除最后一个逗号
        buffer.deleteCharAt(buffer.length()-1);
        buffer.append("]");

        return buffer.toString();
    }
}


class reversePrint {
    public int[] reversePrint(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        while (head != null) {
            stack.push(head.val);
            head = head.next;
        }
        int[] res = new int[stack.size()];
        System.out.println(stack.size());
        System.out.println(res.length);
        for (int i = 0; i < stack.size(); i++) {
            res[i] = stack.pop();
        }
        return res;
    }

}






























