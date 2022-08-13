import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:04 2022/3/15
 * @E-mail: 15611562852@163.com
 */

// 204. 计数质数 https://leetcode-cn.com/problems/count-primes/solution/ji-shu-zhi-shu-bao-li-fa-ji-you-hua-shai-fa-ji-you/
class CountPrimes {
    // 厄拉多塞筛法:比如说求20以内质数的个数,首先0,1不是质数.2是第一个质数,然后把20以内所有2的倍数划去.
    // 2后面紧跟的数即为下一个质数3,然后把3所有的倍数划去.3后面紧跟的数即为下一个质数5,再把5所有的倍数划去.以此类推.
    public int CountPrimes(int n) {
        if(n < 3)
            return 0;
        int count = 0;
        boolean[] signs = new boolean[n];
        for (int i = 2; i < n; i++)
        {
            // 布尔类型的默认值为假。所以在此处用了逻辑非（！）操作符。
            if (!signs[i])
            {
                count++;
                for (int j = i + i; j * j < n; j += i)
                {
                    //排除不是质数的数
                    signs[j] = true;
                }
            }
        }
        return count;
    }
}

// 504. 七进制数 https://leetcode-cn.com/problems/base-7/
class convertToBase7 {
    public String convertToBase7(int num) {
        // Integer.toString(int par1,int par2),par1表示要转成字符串的数字，par2表示要转成的进制表示
        return Integer.toString(num, 7);
    }

    public String convertToBase71(int num) {
        if (num == 0) return "0";
        // 非线程安全，速度快
        StringBuilder sb = new StringBuilder();
        int n = Math.abs(num);
        while (n > 0) {
            sb.append(n % 7);
            n /= 7;
        }
        if (num < 0) sb.append("-");
        return sb.reverse().toString();
    }
}

// 168. Excel表列名称 https://leetcode-cn.com/problems/excel-sheet-column-title/
class convertToTitle {
    public String convertToTitle(int cn) {
        if (cn <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (cn > 0) {
            cn--;
            sb.append((char)(cn % 26 + 'A'));
            cn /= 26;
        }
        return sb.reverse().toString();
    }
}

// 172. 阶乘后的零 https://leetcode-cn.com/problems/factorial-trailing-zeroes/comments/
class trailingZeroes {
    // 算一下乘法因子里有多少个5就是了
    // 不断除以 5, 是因为每间隔 5 个数有一个数可以被 5 整除, 然后在这些可被 5 整除的数中,
    // 每间隔 5 个数又有一个可以被 25 整除, 故要再除一次, ... 直到结果为 0, 表示没有能继续被 5 整除的数了.
    public int trailingZeroes(int n) {
        int count = 0;
        while(n >= 5) {
            count += n / 5;
            n /= 5;
        }
        return count;
    }

    public int trailingZeroes1(int n) {
        int res = 0;
        // 每一次循环都整除 5，这样就能满足 5、25、125
        for (int i = n; i / 5 > 0; i = i / 5) {
            // 得到每一轮因子 5 的个数
            res += i / 5;
        }
        return res;
    }
}

// 415. 字符串相加 https://leetcode-cn.com/problems/add-strings/
class addStrings {
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;
        // carry > 0 表示两个数的最高位相加有进位的话
        while (i >= 0 || j >= 0 || carry > 0) {
            // carry暂存相加结果
            if (i>=0) carry += num1.charAt(i--) - '0';
            if (j>=0) carry += num2.charAt(j--) - '0';
            // 通过carry得到和与进位
            sb.append(carry % 10);
            carry /= 10;
        }
        return sb.reverse().toString();
    }
}

// 43. 字符串相乘 https://leetcode.cn/problems/multiply-strings/
class multiply {
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        if (num1.equals("1")) {
            return num2;
        }
        if (num2.equals("1")) {
            return num1;
        }
        int len1 = num1.length();
        int len2 = num2.length();
        int[] mul = new int[len1 + len2];
        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                int x = num1.charAt(i) - '0';
                int y = num2.charAt(j) - '0';
                int sum = (mul[i + j + 1] + x * y);
                mul[i + j + 1] = sum % 10;
                mul[i + j] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mul.length; i++) {
            if (i == 0 && mul[i] == 0) {
                continue;
            }
            sb.append(mul[i]);
        }
        return sb.toString();
    }
}


// 67. 二进制求和(同415) https://leetcode.cn/problems/add-binary/
class addBinary {
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, carry = 0;
        // 字符串倒序遍历，字符串的末位=数字最低位
        while (i >= 0 || j >= 0 || carry > 0) {
            if (i>=0) carry += a.charAt(i--) - '0';
            if (j>=0) carry += b.charAt(j--) - '0';
            // 进位对2取余为和的当前位的数字，不断在 sb 后面追加
            sb.append(carry % 2);
            carry /= 2;
        }
        return sb.reverse().toString();
    }
}

// 382. 链表随机节点 https://leetcode-cn.com/problems/linked-list-random-node/solution/lian-biao-sui-ji-jie-dian-by-leetcode-so-x6it/
// 蓄水池抽样算法:当内存无法加载全部数据时，如何从包含未知大小的数据流中随机选取k个数据，并且要保证每个数据被抽取到的概率相等。
class getRandom {
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    ListNode head;
    Random random;

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

// 238. 除自身以外数组的乘积(类似于135分糖果) https://leetcode-cn.com/problems/product-of-array-except-self/
class productExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int left = 1;
        int right = 1;
        int len = nums.length;
        int[] ans = new int[len];

        // 从左到右，保存前缀和 ans[2] = nums[0] * nums[1]
        for (int i = 0; i < len; i++) {
            ans[i] = left;
            left *= nums[i];
        }
        // 从右到左，ans[n-1] = nums[0] * nums[1] *...* nums[n-2] * 1
        // ans[n-2] = nums[0] * nums[1] *...* nums[n-3] * nums[n-1]
        for (int j = len - 1; j >= 0; j--) {
            ans[j] *= right;
            right *= nums[j];
        }
        return ans;
    }
}

// 求两个数的最小公倍数和最大公约数
class MyMath {

    public static void main(String[] args) {
        getGcdAndLcm(12,20);
    }

    public static void getGcdAndLcm(int x, int y) {
        int min = Math.min(x, y);
        int max = Math.max(x, y);
        // 辗转相除法
        while (min != 0) {
            // 如果两数相等，则最大公约数为其本身
            if (max == min) {
                break;
            } else {
                int temp = max % min;
                max = min;
                min = temp;
            }
        }
        // 根据最大公约数求最小公倍数
        int lcm = x * y / max;
        System.out.println("最大公约数为：" + max + "，最小公倍数为：" + lcm);
    }
}

// 9. 回文数 https://leetcode-cn.com/problems/palindrome-number/
class isPalindrome {

    // 方法一：只需遍历x到一半即可判断
    public boolean isPalindrome(int x) {
        // 0 是回文数
        if (x == 0) return true;
        // 负数和除 0 以外以 0 结尾的数都不是回文数
        if (x < 0 || x % 10 == 0) return false;
        // 记录 x 后一半的翻转，如 x = 4334，reversed = 43；x = 54345，reversed = 54
        int reversed = 0;
        while (x > reversed) {
            reversed = reversed * 10 + x % 10;
            x /= 10;
        }
        // x有偶数位和奇数位两种情况
        return reversed == x || reversed / 10 == x;
    }

    // 方法二：使用工具类StringBuffer
    public boolean isPalindrome1(int x) {
        if(x < 0) return false;
        StringBuilder sb = new StringBuilder(String.valueOf(x));
        return sb.reverse().toString().equals(String.valueOf(x));
    }

    // 方法三：双指针
    public boolean isPalindrome2(int x) {
        String str = String.valueOf(x);
        int n = str.length();
        if (n < 1 || str.charAt(0) != str.charAt(n - 1)) {
            return false;
        }
        int left = 0;
        int right = n - 1;
        for (int i = 0; i < n; i++) {
            if (left < right && str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }


}

// 343. 整数拆分 数学题！ https://leetcode-cn.com/problems/integer-break/
// 求函数y=(n/x)^x(x>0)的最大值，可得x=e时y最大，但只能分解成整数，故x取2或3，由于6=2+2+2=3+3，显然2^3=8<9=3^2,故应分解为多个3
class integerBreak {
    public int integerBreak(int n) {
        if(n == 2)
            return 1;
        if(n == 3)
            return 2;
        int a = 1;
        while(n > 4){
            a = a * 3;
            n = n - 3;
        }
        return a * n;
    }
}

// 7. 整数反转 https://leetcode.cn/problems/reverse-integer/
class reverse {
    public static int reverse(int x) {
        long n = 0;
        while (x != 0) {
            n = n * 10 + x % 10;
            x = x / 10;
            System.out.println(n);
        }
        // x=1534236469,不进行溢出处理会输出为1056389759
        return (int) n == n ? (int) n : 0;
    }

    // 2^31-1=2147483647,-2^31=-2147483648
    public static void main(String[] args) {
        // reverse(-2147483677);
        long a = 9034236469L;
        int b = (int) a;
        System.out.println(a);
        System.out.println(b);
    }
}


class intToRoman {
    public String intToRoman(int num) {
        int[] values={1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] reps={"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder sb = new StringBuilder();
        // values：先用大的，再用小的
        for (int i = 0; i < 13; i++) {
            while (num >= values[i]) {
                num -= values[i];
                sb.append(reps[i]);
            }
        }
        return sb.toString();
    }
}

// 29. 两数相除 https://leetcode.cn/problems/divide-two-integers/
class divide {
    public int divide(int dividend, int divisor) {

        if (dividend == 0) {
            return 0;
        }
        if (divisor == 1) {
            return dividend;
        }
        if (divisor == -1) {
            // int 范围 -2147483648 至 2147483647（-231至 231-1），如果为-2147483648，返回 2147483648超出int范围
            if (dividend > Integer.MIN_VALUE) {
                return -dividend;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        int result = 0;

        // 判断是否异号
        boolean negative = (dividend ^ divisor) < 0;


        long x = Math.abs((long) dividend);
        long y = Math.abs((long) divisor);


        // 商 = 被除数中有多少个除数
        for (int i = 31; i >=0 ; i--) {
            if (x >= y << i) {
                result += 1 << i;
                x -= y << i;
            }
        }

        return negative ? -result : result;

    }

    public static void main(String[] args) {
        divide divide = new divide();
        divide.divide(2, 2);
    }

}

// 剑指 Offer 10- I. 斐波那契数列 https://leetcode.cn/problems/fei-bo-na-qi-shu-lie-lcof/
class fib {
    public int fib(int n) {
        if(n == 0) return 0;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            dp[i] = dp[i-1] + dp[i-2];
            dp[i] %= 1000000007;
        }
        return dp[n];
    }
}