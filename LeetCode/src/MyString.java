
import java.util.*;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:19 2022/4/15
 * @E-mail: 15611562852@163.com
 */


// 242. 有效的字母异位词（判断两个字符串中每个字符出现的次数是否都相同） https://leetcode-cn.com/problems/valid-anagram/
class isAnagram {
    // 方法一：字符串内部排序并比较
    public boolean isAnagram1(String s, String t) {
        if (s.length() != t.length()) return false;
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        return Arrays.equals(sChars, tChars);
    }

    // 方法二：
    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (char ch : t.toCharArray()) {
            Integer count = map.get(ch);
            // s有但t没有这个字符
            if (count == null) {
                return false;
            } else if (count > 1) {
                map.put(ch, count - 1);
            } else { // 互相抵消都为0平局了
                map.remove(ch);
            }
        }
        return map.isEmpty();
    }

    // 统计各个字符出现次数
    public boolean isAnagram3(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] sCounts = new int[26];
        int[] tCounts = new int[26];
        for (char ch : s.toCharArray()) {
            sCounts[ch - 'a']++;
        }
        for (char ch : t.toCharArray()) {
            tCounts[ch - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (sCounts[i] != tCounts[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagram4(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] counts = new int[26];
        t.chars().forEach(tc -> counts[tc - 'a']++);
        s.chars().forEach(cs -> counts[cs - 'a']--);
        return Arrays.stream(counts).allMatch(c -> c == 0);
    }

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        return Arrays.equals(s.chars().sorted().toArray(), t.chars().sorted().toArray());
    }
}

// 205. 同构字符串 https://leetcode-cn.com/problems/isomorphic-strings/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-42/
class isIsomorphic {

    // 方法一：map一一映射
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        return isIsomorphicHelper(s, t) && isIsomorphicHelper(t, s);
    }

    private boolean isIsomorphicHelper(String s, String t) {
        HashMap<Character, Character> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char sChar = s.charAt(i);
            char tChar = t.charAt(i);

            // 如果当前 sChar 有之前匹配好的字符
            if (map.containsKey(sChar)) {
                // 如果当前 sChar 匹配好的字符与 tChar 不相等，说明不同构
                if (map.get(sChar) != tChar) {
                    return false;
                }
            } else {
                // 如果当前 sChar 没有与之匹配的字符
                map.put(sChar, tChar);
            }
        }
        return true;
    }


    // 方法二：第三方语言
    public boolean isIsomorphic1(String s, String t) {
        return isIsomorphicHelper(s).equals(isIsomorphicHelper(t));
    }

    private String isIsomorphicHelper(String s) {
        // 记录各个字符的出现排名
        int[] map = new int[128];
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            // 当前字母第一次出现,赋值其出现排名
            if (map[c] == 0) {
                map[c] = count;
                count++;
            }
            // 追加当前字符出现排名
            sb.append(map[c]);
        }
        return sb.toString();
    }


}

// 647. 统计回文子串数目（动态规划） https://leetcode-cn.com/problems/palindromic-substrings/
class countSubstrings {
    public int countSubstrings(String s) {
        int res = 0;
        int n = s.length();

        // dp[i][j] 表示[i,j]的字符是否为回文子串,上三角矩阵
        boolean[][] dp = new boolean[n][n];

        // 注意，外层循环要倒着写，内层循环要正着写
        // 因为要求dp[i][j] 需要知道dp[i+1][j-1]
        for (int i = n - 1; i >= 0; i--) {
            // dp为上三角矩阵，所以j=i
            for (int j = i; j < n; j++) {
                // (s.charAt(i)==s.charAt(j) 时，当元素个数为1,2,3个或者除去i,j剩下的更短的字符串为回文时，其一定为回文子串
                if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    res++;
                }
            }
        }
        return res;
    }
}

// 5. 最长回文子串（动态规划） https://leetcode-cn.com/problems/longest-palindromic-substring/
class longestPalindrome {
    public String longestPalindrome(String s) {
        int n = s.length();
        int max = Integer.MIN_VALUE;
        String res = "";

        // dp[i][j] 表示[i,j]的字符是否为回文子串,上三角矩阵
        boolean[][] dp = new boolean[n][n];

        // 注意，外层循环要倒着写，内层循环要正着写
        // 因为要求dp[i][j] 需要知道dp[i+1][j-1]
        for (int i = n - 1; i >= 0; i--) {
            // dp为上三角矩阵，所以j=i
            for (int j = i; j < n; j++) {
                // (s.charAt(i)==s.charAt(j) 时，当元素个数为1,2,3个或者除去i,j剩下的更短的字符串为回文时，其一定为回文子串
                if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    if (j - i + 1 > max) {
                        res = s.substring(i, j + 1);
                        max = j - i + 1;
                    }
                }
            }
        }
        return res;
    }
}

// 409. 最长回文串 https://leetcode-cn.com/problems/longest-palindrome/
class longestPalindrome1 {
    public int longestPalindrome(String s) {
        int[] cnt = new int[256];
        for (char c : s.toCharArray()) {
            cnt[c - 'A']++;
        }
        int ans = 0;
        for (int x: cnt) {
            // 字符出现偶数次就全用上，奇数次就少用一次
            ans += x - (x & 1);
        }
        // 如果最终的长度小于原字符串的长度，说明里面某个字符出现了奇数次，那么那个字符可以放在回文串的中间，所以额外再加一。
        return ans < s.length() ? ans + 1 : ans;
    }
}

// 28. 实现 strStr() https://leetcode-cn.com/problems/implement-strstr/
class strStr {
    public int strStr(String haystack, String needle) {
        int hLen = haystack.length();  // 原字符串
        int nLen = needle.length();  // 目标字符串
        if (nLen == 0) return 0;
        char[] h = haystack.toCharArray();
        char[] n = needle.toCharArray();

        // 从原字符串的起始位置到 hLen - nLen 位置依次开始比较
        for (int i = 0; i <= hLen - nLen; i++) {
            // 双指针，x 指向原字符串当前位置，y 指向目标字符串当前位置
            int x = i, y = 0;
            while (y < nLen && h[x] == n[y]) {
                x++;
                y++;
            }
            // 如果目标字符串已遍历到末尾，说明所有字符串均有匹配，返回原字符串起始位置 i
            if (y == nLen) return i;
        }
        return -1;
    }
}

// 3. 无重复字符的最长子串 https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
class lengthOfLongestSubstring {
    // 滑动窗口
    public int lengthOfLongestSubstring(String s) {
        // 记录每个字符最新出现的位置
        HashMap<Character, Integer> map = new HashMap<>();
        int maxLen = 0;
        // 记录滑动窗口左端
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 如果遇到出现过的字符，
            if (map.containsKey(c)) {
                // 如果该字符前一次出现位置还在当前窗口内：left = map.get(c) + 1;
                // 如果该字符前一次出现位置不在当前窗口内：left = left;
                // 综上，取两者中最大为滑动窗口左端
                left = Math.max(left, map.get(c) + 1);
            }
            // 遇到新字符，添加并记录出现的位置
            map.put(c, i);
            maxLen = Math.max(maxLen, i - left + 1);
        }
        return maxLen;
    }
}

// 6. Z 字形变换 https://leetcode.cn/problems/zigzag-conversion/solution/zzi-xing-bian-huan-by-jyd/
class convert {
    public String convert(String s, int numRows) {
        if(numRows < 2) return s;
        List<StringBuilder> rows = new ArrayList<>();
        // 每一行对应一个 StringBuilder
        for(int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }

        // i:行索引  flag:行遍历方向
        int i = 0, flag = -1;
        // 遍历每一个字符
        for(char c : s.toCharArray()) {
            rows.get(i).append(c);
            // 如果是第一行或最后一行，折返
            if(i == 0 || i == numRows -1) flag = - flag;
            i += flag;
        }

        // 将所有 StringBuilder 拼接到一起
        StringBuilder res = new StringBuilder();
        for(StringBuilder row : rows) res.append(row);
        return res.toString();
    }
}

// 14. 最长公共前缀 https://leetcode.cn/problems/longest-common-prefix/
class longestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        // 公共前缀肯定比任何一个字符串短，随便选一个
        String s = strs[0];
        for (String str : strs) {
            // 如果不是当前字符串的前缀，则将s长度不断缩短，直到s为当前字符串的前缀
            while (!str.startsWith(s)) {
                if (s.length() == 0) {
                    return "";
                }
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }
}

// 58. 最后一个单词的长度 https://leetcode.cn/problems/length-of-last-word/
class lengthOfLastWord {
    public int lengthOfLastWord(String s) {
        int length = 0;
        for (int i = s.length()-1; i >= 0; i--) {
            // 如果当前字符不是空格
            if (s.charAt(i) != ' ') {
                length++;
            // 如果当前字符是空格且长度不为0，说明最后一个单词已经遍历完成
            } else if (length != 0) {
                return length;
            }
            // 省略了“如果当前字符是空格且长度为0，说明当前是末尾的空格”
        }
        return length;
    }
}

// 71. 简化路径 https://leetcode.cn/problems/simplify-path/
class simplifyPath {
    public String simplifyPath(String path) {
        // 储存path中有效目录名或者文件名（以后简称"name"）
        Deque<String> deque = new ArrayDeque<>();
        int length = path.length();

        // 取出path中除"/"外的name并入栈
        for (int i = 0; i < length;) {

            // 如果是"/"，则跳过
            if (path.charAt(i) == '/') {
                i++;
                continue;
            }

            // 此时已经跳过前面的所有"/"，此时的i为name开始，需要找到当前name的结尾
            int end = i + 1;
            // 注意边界，并且第一个判断条件一定是"end < length"
            while (end < length && path.charAt(end) != '/') {
                end++;
            }
            String name = path.substring(i, end);

            // 如果是返回上一级目录，弹出栈中的最后一个目录，表示返回上一级
            if (name.equals("..")) {
                if (!deque.isEmpty()) {
                    deque.pollLast();
                }
            // 如果不是"."，就入栈，因为"."代表当前目录，无实际意义
            } else if (!name.equals(".")) {
                deque.offerLast(name);
            }
            i = end;
        }

        StringBuilder sb = new StringBuilder();
        // 将name队列从头开始弹出，从左到右构建结果字符串
        while (!deque.isEmpty()) {
            sb.append("/").append(deque.pollFirst());
        }
        return sb.length() == 0 ? "/" : sb.toString();
    }
}


class test1 {
    public static void main(String[] args) {
        // String s1 = new String("hello");
        // String s2 = new String("hello");
        // char[] c = new char[]{'h', 'e', 'l', 'l', 'o'};
        // System.out.println(s1 == s2);
        // System.out.println(s1.equals(s2));
        // System.out.println(s1.equals(c));
        String[] str = new String[10];
        System.out.println(str[0]);
        System.out.println(str[10]);
        System.out.println(str.length);
    }
}