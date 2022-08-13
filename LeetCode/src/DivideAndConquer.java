import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 16:37 2022/3/11
 * @E-mail: 15611562852@163.com
 */

// 241. 为运算表达式设计优先级 https://leetcode-cn.com/problems/different-ways-to-add-parentheses/
class diffWaysToCompute {
    // 分治法，碰到运算符号，递归求解前一半的值和后一半的值，然后根据运算符号，计算两者构成的值。
    // map记录已经计算出来的字符串对应的值，避免重复计算。
    public Map<String, List<Integer>> map = new HashMap<>();

    public List<Integer> diffWaysToCompute(String input) {
        if(map.containsKey(input)) return map.get(input);
        // list记录该字符串可能的计算值
        List<Integer> list = new ArrayList<>();
        int len = input.length();
        for(int i = 0; i < len; i++) {
            char c = input.charAt(i);
            if(c == '+' || c == '-' || c == '*') {  // 出现运算符号，递归求解前半段和后半段。
                List<Integer> left = diffWaysToCompute(input.substring(0, i));
                List<Integer> right = diffWaysToCompute(input.substring(i+1, input.length()));
                // -1   =>  left:[[0]]  right:[[1]]
                // 依次遍历左侧结果和右侧结果，计算并记录不同运算符下的计算结果
                for(int l : left) {
                    for(int r : right) {
                        switch(c) {
                            case '+':
                                list.add(l + r);
                                break;
                            case '-':
                                list.add(l - r);
                                break;
                            case '*':
                                list.add(l * r);
                                break;
                        }
                    }
                }
            }
        }
        // 单独一个数字的情况 (可能出现多位数)
        if(list.size() == 0) list.add(Integer.valueOf(input));
        // 全局map记录该字符串可能的所有结果
        map.put(input, list);
        // 返回该字符串可能的所有结果，用于递归
        return list;
    }
}