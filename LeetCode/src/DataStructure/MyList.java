package DataStructure;

import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:54 2022/3/21
 * @E-mail: 15611562852@163.com
 */


// 宫水三叶 链表题目汇总  https://leetcode-cn.com/problems/linked-list-random-node/solution/gong-shui-san-xie-xu-shui-chi-chou-yang-1lp9d/

// 单向链表类
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }

// 21. 合并两个有序链表(迭代+递归) https://leetcode-cn.com/problems/merge-two-sorted-lists/
class mergeTwoLists {
    // 解法一：迭代
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode preHead = new ListNode(0);
        ListNode tail = preHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        tail.next = list1 == null ? list2 : list1;
        return preHead.next;
    }


    // 解法二：递归
    public ListNode mergeTwoLists1(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val < list2.val) {
            list1.next = mergeTwoLists1(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists1(list1, list2.next);
            return list2;
        }
    }

}

// 23. 合并K个升序链表(迭代)  https://leetcode-cn.com/problems/merge-k-sorted-lists/
class mergeKLists {
    // 用容量为K的最小堆优先队列，把链表的头结点都放进去，然后出队当前优先队列中最小的，挂上链表，
    // 然后让出队的那个节点的下一个入队，再出队当前优先队列中最小的，直到优先队列为空。
    public ListNode mergeKLists(ListNode[] lists) {

        if (lists == null || lists.length == 0) return null;
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        // 保存头指针
        ListNode preHead = new ListNode(0);
        // 记录尾指针，下一个需要插入的地方
        ListNode tail = preHead;
        // 将所有链表的头结点加入队列
        for (ListNode node : lists) {
            if (node != null)
                queue.add(node);
        }
        // 最小优先队列中保持有k个节点，每次弹出最小的加入结果链表，并压进去最小的节点的下一个节点
        while (!queue.isEmpty()) {
            // 弹出最小的加入结果链表
            ListNode node = queue.poll();
            tail.next = node;
            // 刚加入的节点：tail  下一个要加入的位置：tail.next，
            tail = tail.next;
            // 让出队的那个节点的下一个入队
            if (node.next != null)
                queue.add(node.next);
        }
        return preHead.next;
    }
}

// 206. 反转链表(迭代+递归)  https://leetcode-cn.com/problems/reverse-linked-list
class reverseList {

    // 迭代
    public ListNode reverseList(ListNode head) {
        ListNode prev = null; //前指针节点
        ListNode curr = head; //当前指针节点
        //每次循环，都将当前节点指向它前面的节点，然后当前节点和前节点后移
        while (curr != null) {
            ListNode nextTemp = curr.next; //临时节点，暂存当前节点的下一节点，用于后移
            curr.next = prev; //将当前节点指向它前面的节点
            prev = curr; //前指针后移
            curr = nextTemp; //当前指针后移
        }
        return prev;
    }

    // 递归 https://leetcode.cn/problems/reverse-linked-list-ii/solution/bu-bu-chai-jie-ru-he-di-gui-di-fan-zhuan-lian-biao/
    public ListNode reverseList1(ListNode head) {

        if (head == null || head.next==null) return head;
        ListNode newHead = reverseList1(head.next);
        // 1 -> ...
        // 1 <- ...
        head.next.next = head;
        // null <- 1 <- ...
        head.next = null;
        return newHead;
    }
}

// 92. 反转区间链表 II https://leetcode.cn/problems/reverse-linked-list-ii/
class reverseBetween {

    //比如1->2->3->4->5,m=1,n=5
    //第一次反转：1(head) 2(next) 3 4 5 反转为 2 1 3 4 5
    //第二次反转：2 1(head) 3(next) 4 5 反转为 3 2 1 4 5
    //第三次发转：3 2 1(head) 4(next) 5 反转为 4 3 2 1 5
    //第四次反转：4 3 2 1(head) 5(next) 反转为 5 4 3 2 1

    public ListNode reverseBetween (ListNode head, int m, int n) {
        //设置虚拟头节点
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode pre = preHead;
        for (int i = 0; i < m - 1; i++) {
            pre = pre.next;// 之后pre不动了，指向固定位置1
        }
        // pre指向 m 节点的前一个
        ListNode cur = pre.next;
        ListNode nextTemp ;
        for (int i = 0; i < n - m; i++) {
            // 1→2→3→4→5→NULL, m=2,n=4      1→3→2→4→5→NULL, m=2,n=4     1→3→2→4→5→NULL, m=2,n=4     1→4→3→2→5→NULL, m=2,n=4
            // p c n                        p c n                       p   c n                     p     c n
            // pre一直不动，指向固定位置1; cur指向的val一直没变，但是位置逐渐后移
            nextTemp = cur.next;
            // 2->4                         3->4
            cur.next = nextTemp.next;
            // 3->2
            nextTemp.next = pre.next;
            // 1->3
            pre.next = nextTemp;
        }
        return preHead.next;
    }

    // 递归
    ListNode successor = null;

    public ListNode reverseBetween1 (ListNode head, int m, int n) {
        if (m == 1) {
            return reverseN(head, n);
        }
        head.next = reverseBetween1(head.next, m - 1, n - 1);
        return head;
    }

    public ListNode reverseN (ListNode head, int n) {
        if (n == 1) {
            successor = head.next;
            return head;
        }
        ListNode newHead = reverseN(head.next, n);
        head.next.next = head;
        head.next = successor;
        return newHead;
    }
}

// 25. K 个一组翻转链表 https://leetcode.cn/problems/reverse-nodes-in-k-group/
class reverseKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        // 需要用到四个指针：preHead（用于返回首节点），pre, cur, next
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode pre = preHead;
        ListNode cur = head;

        // 计算链表长度
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        // pre->1→2→3→4→5→6->7->8->NULL, k=3
        //      c
        // 每k个一组翻转，所以用整除
        for (int i = 0; i < length / k; i++) {
            // 以下思路和 92. 反转区间链表相同
            for (int j = 0; j < k - 1; j++) {
                // nextTemp=2 nextTemp=3
                ListNode nextTemp = cur.next;
                // 1->3 1->4
                cur.next = nextTemp.next;
                // 2->1 3->2
                nextTemp.next = pre.next;
                // pre->2 pre->3
                pre.next = nextTemp;
            }
            // pre->3→2→1→4→5→6->7->8->NULL, k=3
            //          c
            pre = cur;
            cur = cur.next;
        }
        return preHead.next;

    }
}

// 61. 旋转链表 https://leetcode.cn/problems/rotate-list/
class rotateRight {
    public ListNode rotateRight(ListNode head, int k) {
        // 此行不可少，一个元素情况直接返回即可
        if (head == null || head.next == null || k == 0) return head;
        ListNode cur = head;
        int length = 1;
        while (cur.next != null) {
            length++;
            cur = cur.next;
        }
        // 此时cur指向最后一个元素  1->2->3->4->5->6   k=2   cur=6
        // 如果旋转长度为原链表长度的倍数，无需旋转
        k %= length;
        if (k == 0) {
            return head;
        }

        // 首尾相连 6->1
        cur.next = head;

        // 找到新的尾节点,cur=1
        cur = head;
        for (int i = 0; i < length - k - 1; i++) {
            cur = cur.next;
        }
        // 1->2->3->4->5->6   k=2 此时cur=4
        ListNode newHead = cur.next;
        // 4->null
        cur.next = null;
        return newHead;

    }
}






// 24. 两两交换链表中的节点(迭代+递归) https://leetcode-cn.com/problems/swap-nodes-in-pairs/
class swapPairs {
    // 递归
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // pre->1->2->3->4->...->null
        //      h  n
        ListNode nextTemp = head.next;
        // 1->swapPairs(3)
        head.next = swapPairs(nextTemp.next);
        // 2->1
        nextTemp.next = head;
        return nextTemp;
    }

    // 迭代
    public ListNode swapPairs1(ListNode head) {
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode temp = pre;
        while (temp.next != null && temp.next.next != null) {
            ListNode start = temp.next;
            ListNode end = temp.next.next;
            temp.next = end;
            start.next = end.next;
            end.next = start;
            temp = start;
        }
        return pre.next;
    }
}

// 160. 相交链表 https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
class getIntersectionNode {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        /**
         定义两个指针, 第一轮让两个到达末尾的节点指向另一个链表的头部, 最后如果相遇则为交点(在第一轮移动中恰好抹除了长度差)
         两个指针等于移动了相同的距离, 有交点就返回, 无交点就是各走了两条指针的长度
         **/
        if(headA == null || headB == null) return null;
        ListNode pA = headA, pB = headB;
        // 在这里第一轮体现在pA和pB第一次到达尾部会移向另一链表的表头, 而第二轮体现在如果pA或pB相交就返回交点, 不相交最后就是null==null
        // 如果长度相同，且没有交点，在循环到第一轮末尾时，pA和pB会同时为null，这时就相等退出了
        // 如果长度不同，没有交点，会在第二轮末尾同时为null，相等退出
        while(pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}

// 234. 回文链表（找到链表中点） https://leetcode-cn.com/problems/palindrome-linked-list/
class isPalindrome {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode slow = head, fast = head;
        // 找到中点slow，不管链表有奇数或者偶数个节点，都能保证slow为后半部分的起点 ()
        // 偶数时，slow=n/2+1; 奇数时,slow=n//2+1
        // 0 1 2 3 4 5 ^; 0 1 2 3 4 5 6 ^
        //       s     f        s     f
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 翻转后半部分链表
        // 翻转示意图
        // 0->1->2->3<-4<-5 ^
        //          p  s  t
        ListNode pre = null;
        while (slow != null) {
            ListNode nextTemp = slow.next;
            slow.next = pre;
            pre = slow;
            slow = nextTemp;
        }
        // 经过翻转之后
        // 0->1->2->3<-4<-5 ^
        // h              p s
        // 0->1->2->3->4<-5<-6 ^
        // h                 p s

        // 分别从头尾开始遍历并比较
        while (head != null && pre != null) {
            if (head.val != pre.val) {
                return false;
            }
            head = head.next;
            pre = pre.next;
        }
        return true;
    }
    public boolean isPalindrome1(ListNode head) {
        if (head==null||head.next==null) return true;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode pre = null;
        while (slow != null) {
            ListNode nextTemp = slow.next;
            slow.next = pre;
            pre = slow;
            slow = nextTemp;
        }
        while (pre != null && head != null) {
            if (pre.val != head.val) {
                return false;
            }
            pre = pre.next;
            head = head.next;
        }
        return true;
    }
}

// 83. 删除排序链表中的重复元素（只留一个） https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
class deleteDuplicates {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        // 注意边界条件
        while(cur != null && cur.next != null) {
            if(cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }
}

// 82. 删除排序链表中的重复元素（一个不留） II https://leetcode.cn/problems/remove-duplicates-from-sorted-list/
class deleteDuplicates1 {
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null||head.next==null) return head;
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        // cur：当前遍历位置  tail：应该插入的位置
        ListNode cur = head, tail = preHead;

        while (cur != null) {
            //相等的结点则跳过,走到相同值元素的最后一步
            while (cur.next != null && cur.val == cur.next.val) {
                cur = cur.next;
            }
            // preHead->1->2->3->4->5->6\
            //          t  c
            if (tail.next == cur) {
                tail = tail.next;
            }
            // preHead->1->2->2->2->3->4->5->6\
            //          t        c
            else {
                tail.next = cur.next;
                cur = cur.next;
            }
        }


        return preHead.next;

    }
}



// 328. 奇偶链表 https://leetcode-cn.com/problems/odd-even-linked-list/
class oddEvenList {
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode evenHead = head.next;
        ListNode oddTail = head;
        ListNode evenTail = evenHead;
        // 1->2->3->4
        while (oddTail.next != null && evenTail.next != null) {
            // 1->3
            oddTail.next = evenTail.next;
            // oddTail:1->3
            oddTail = oddTail.next;
            // 2->4
            evenTail.next = oddTail.next;
            // evenTail:2->4
            evenTail = evenTail.next;
        }
        oddTail.next = evenHead;
        return head;
    }
}

// 2. 两数相加 https://leetcode-cn.com/problems/add-two-numbers/
class addTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode curr = root;
        int carry = 0;
        while(l1 != null || l2 != null || carry != 0) {
            int l1Val = l1 != null ? l1.val : 0;
            int l2Val = l2 != null ? l2.val : 0;
            int sumVal = l1Val + l2Val + carry;
            carry = sumVal / 10;

            ListNode sumNode = new ListNode(sumVal % 10);
            curr.next = sumNode;
            curr = sumNode;

            if(l1 != null) l1 = l1.next;
            if(l2 != null) l2 = l2.next;
        }

        return root.next;
    }
}

// 19. 删除链表的倒数第 N 个结点 https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
class removeNthFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head, slow = head, pre = null;
        // 1->2->3->4->5->6->null  n=2
        // s     f
        for(int i = 0; i < n; i++) {
            fast = fast.next;
        }
        // 1->2->3->4->5->6->null  n=2
        //             s     f
        // 当fast为null时，slow才是倒数第N个
        while(fast != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next;
        }
        //   ^ 1->2->^  n=2
        //   p s     f
        //   ^ 1->^  n=1
        //   p s  f
        // 如果删除的是第一个节点
        if (pre == null) {
            return head.next;
        }
        // pre:删除节点的前一个  slow:待删除节点   fast:null
        pre.next = slow.next;
        return head;
    }
}

// 203. 移除链表元素 https://leetcode.cn/problems/remove-linked-list-elements/
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        // 最后一个不同的节点
        ListNode prev = preHead;
        //确保当前结点后还有结点
        while(prev.next!=null){
            // 如果节点一直等于val，则prev.next一直在被修改
            // 1->2->2->2->2->2->3->4->null  val=2
            // p
            if(prev.next.val==val){
                prev.next=prev.next.next;
            }else{
                prev=prev.next;
            }
        }
        return preHead.next;
    }
}


// 148. 排序链表 https://leetcode-cn.com/problems/sort-list/
// O(n log n) 时间复杂度和常数级空间复杂度
class sortList {
    public ListNode sortList(ListNode head) {
        // 1、递归结束条件
        if (head == null || head.next == null) {
            return head;
        }

        // 2、找到链表中间节点并断开链表 & 递归下探
        // 0 1 2 3 4 5 ^;   0 1 2 3 4 5 6 ^
        //     m                m
        ListNode midNode = middleNode(head);
        ListNode rightHead = midNode.next;
        // 偶数：0->1->2->null  3->4->5->null;
        // 奇数：0->1->2->null  3->4->5->6->null
        midNode.next = null;

        ListNode left = sortList(head);
        ListNode right = sortList(rightHead);

        // 3、当前层业务操作（合并有序链表）
        return mergeTwoLists(left, right);
    }

    //  找到链表中间节点（876. 链表的中间结点）
    private ListNode middleNode(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 因为本题需要断开链表，所以需要找到中间节点的前一个节点
        // 0 1 2 3 4 5 ^;   0 1 2 3 4 5 6 ^
        // s   f            s   f
        ListNode slow = head, fast = head.next.next;

        // 找到中点的前一个 ，不管链表有奇数或者偶数个节点，都能保证slow.next为后半部分的起点
        // 0 1 2 3 4 5 ^;   0 1 2 3 4 5 6 ^
        //     s       f        s       f
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // 合并两个有序链表（21. 合并两个有序链表）
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode preHead = new ListNode(0);
        ListNode tail = preHead;

        // 只有有一个链表空了，就可以直接将剩下的连接到尾部，所以用&&，而非||
        while(l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }

            tail = tail.next;
        }

        tail.next = l1 != null ? l1 : l2;
        return preHead.next;
    }
}

// 382. 链表随机节点 https://leetcode-cn.com/problems/linked-list-random-node/solution/lian-biao-sui-ji-jie-dian-by-leetcode-so-x6it/
// 蓄水池抽样算法
class getRandom {
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    List<Integer> list = new ArrayList<>();
    Random random = new Random();
    // public getRandom(ListNode head) {
    //     while (head != null) {
    //         list.add(head.val);
    //         head = head.next;
    //     }
    // }
    // public int getRandom() {
    //     int idx = random.nextInt(list.size());
    //     return list.get(idx);
    // }

    ListNode head;
    public getRandom(ListNode head) {
        this.head = head;
        this.random = new Random();
    }
    public int getRandom() {
        int i = 1, ans = 0;
        ListNode node = head;
        while (node != null) {
            if (random.nextInt(i) == 0) { // 1/i 的概率选中（替换为答案）
                ans = node.val;
            }
            ++i;
            node = node.next;
        }
        return ans;
    }
}

// 146. LRU 缓存 https://leetcode.cn/problems/lru-cache/
class LRUCache {
    private class Node {
        private Node prev, next;
        private int key, value;

        private Node(int k, int v) {
            this.key = k;
            this.value = v;
        }
    }

    private class DoubleList {
        // 头尾虚节点，并不是LRU链表的一部分
        private Node head;
        private Node tail;
        private int size;

        private DoubleList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        private void addFirst(Node n) {
            Node headNext = head.next;
            head.next = n;
            headNext.prev = n;
            n.prev = head;
            n.next = headNext;
            size++;
        }

        private void remove(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
            size--;
        }

        private Node removeLast() {
            Node last = tail.prev;
            remove(last);
            return last;
        }

        private int size() {
            return size;
        }

    }

    // key -> Node(key, val)
    private Map<Integer, Node> map;
    // node(k1, v1) <-> Node(k2, v2)...
    private DoubleList cache;
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        cache = new DoubleList();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        int val = map.get(key).value;
        put(key, val);// 最新适用，将该数据保存在LRU头部
        return val;
    }

    public void put(int key, int value) {
        Node n = new Node(key, value);
        if (map.containsKey(key)) {
            cache.remove(map.get(key));
        } else {
            if (cache.size() == capacity) {
                // delete last element in list
                Node last = cache.removeLast();
                map.remove(last.key);
            }
        }
        // 无论如何都将此节点加入LRU链表
        cache.addFirst(n);
        map.put(key, n);
    }
}



