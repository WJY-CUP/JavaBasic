/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:52 2022/3/22
 * @E-mail: 15611562852@163.com
 */

// 461. 汉明距离 https://leetcode-cn.com/problems/hamming-distance/
class hammingDistance {
    public int hammingDistance(int x, int y) {
        if (x == y) return 0;
        int count = 0;
        while (x > 0 || y > 0) {
            if (x % 2 != y % 2) {
                count++;
            }
            x /= 2;
            y /= 2;
        }
        return count;
    }

    int hammingDistance1(int x, int y){
        int z = x ^ y;
        int sum = 0;
        while (z>0){
            sum += z & 1;
            z = z>>1;
        }
        return sum;
    }
}

// 190. 颠倒二进制位 https://leetcode-cn.com/problems/reverse-bits/
class reverseBits {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int ret = 0;
        int count = 32;
        while (count-- > 0) {
            int lastBit = n & 1; //获取 n 最后一位
            ret = ret << 1 | lastBit; //将结果左移一位后再添加 n 的最后一位
            n = n >> 1; // n 右移来减少一位
        }
        return ret;
    }
}

// 318. 最大单词长度乘积 https://leetcode-cn.com/problems/maximum-product-of-word-lengths/
class maxProduct {
    public int maxProduct(String[] words) {
        /**
         全是小写字母, 可以用一个32为整数表示一个word中出现的字母,
         hash[i]存放第i个单词出现过的字母, a对应32位整数的最后一位,
         b对应整数的倒数第二位, 依次类推. 时间复杂度O(N^2)
         判断两两单词按位与的结果, 如果结果为0且长度积大于最大积则更新
         **/
        int n = words.length;
        int[] hash = new int[n];
        int max = 0;
        for(int i = 0; i < n; ++i) {
            for(char c : words[i].toCharArray())
                hash[i] |= 1 << (c-'a');
        }

        for(int i = 0; i < n-1; ++i) {
            for(int j = i+1; j < n; ++j) {
                if((hash[i] & hash[j]) == 0)
                    max = Math.max(words[i].length() * words[j].length(), max);
            }
        }
        return max;
    }
}

// 338. 比特位计数 https://leetcode-cn.com/problems/counting-bits/comments/
class countBits {
    public int[] countBits(int n) {
        int[] res = new int[n + 1];
        for(int i = 0;i<= n;i++){
            res[i] = res[i >> 1] + (i & 1);  //注意i&1需要加括号
        }
        return res;
    }
}