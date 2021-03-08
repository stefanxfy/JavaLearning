package com.stefan.DailyTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test131 {
    public static void main(String[] args) {
        String content= "产品经理工作内容包含需求收集，需求分析，需求落地，项目跟踪，项目上线，数据跟踪以及对业务人员进行培训，协助运营、销售、客服等开展工作。";
        Set<String> wordSet = new HashSet<String>();
        wordSet.add("产品经理");
        wordSet.add("产品总监");
        wordSet.add("程序员");
        init(wordSet);
        System.out.println("wordMap=" + m_kwWordMap);
        Set<String> haveWords = getWord(content, MIN_MATCH_TYPE);
        System.out.println("haveWords=" + haveWords);

    }

    /**
     * 初始化DFA关键词容器
     * @param words
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void init(Set<String> words) {
        // 预先 设置初始容量，以免扩容影响性能。
        Map wordMap = new HashMap(words.size());
        for (String word : words) {
            Map nowMap = wordMap;
            for (int i = 0; i < word.length(); i++) {
                // 转换成char型
                char keyChar = word.charAt(i);
                // 判断是否已经有一个map树，只有在一个词的首字符有用
                Object tempMap = nowMap.get(keyChar);
                if (tempMap != null) {
                    // 存在，则共享一个map树根
                    nowMap = (Map) tempMap;
                }
                // 不存在则构建一个map树，
                else {
                    // 设置状态位
                    Map<String, String> newMap = new HashMap<String, String>();
                    // 判断是设置 0还是1
                    newMap.put("isEnd", i == word.length() - 1 ? "1" : "0");
                    // 给keyChar该字符设置状态位
                    nowMap.put(keyChar, newMap);
                    // 将状态位map赋值给nowMap，表示下一个字符的指针和状态位在同一个map里。
                    nowMap = newMap;
                }
            }
        }
        // 上面始终修改的是nowMap，最后形成的是wordMap，原因是，预先wordMap赋值给了nowMap，
        // 使得wordMap和nowMap中的map地址值共享，更新了nowMap中的map就是更新了wordMap。
        m_kwWordMap = wordMap;
    }

    /**
     * 检索关键词
     * @param txt 被检索的文本
     * @param beginIndex  被检索文本的开始位置
     * @param matchType 匹配类型
     * @return 返回检索到的关键词长度，用于从文本中截取
     */
    public static int checkWord(String txt, int beginIndex, int matchType) {
        // 匹配标识数默认为0
        Map nowMap = m_kwWordMap;
        int matchFlag = 0;
        int matchMaxFlag = 0;
        for (int i = beginIndex; i < txt.length(); i++) {
            char word = txt.charAt(i);
            // 获取指定key
            nowMap = (Map) nowMap.get(word);
            // 存在，则判断是否为最后一个
            if (nowMap != null) {
                // 找到相应key，匹配标识+1
                matchFlag++;
                // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {
                    // 将matchFlag赋值给matchMaxFlag，为的是，
                    // 后面要是继续按最大匹配原则匹配时，匹配不到则按最小匹配原则的结果为准。
                    matchMaxFlag = matchFlag;
                    // 最小规则，直接返回,最大规则还需继续查找
                    if (MIN_MATCH_TYPE == matchType) {
                        break;
                    }
                }
            }
            // 不存在，直接返回
            else {
                break;
            }
        }
        return matchMaxFlag;
    }

    /**
     * 从文本中检索出关键词
     * @param txt 被检索的文本
     * @param matchType 匹配类型
     * @return
     */
    public static Set<String> getWord(String txt, int matchType) {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < txt.length(); i++) {
            // 判断是否包含关键词，length > 0 有，且是关键词长度
            int length = checkWord(txt, i, matchType);
            // 存在,加入set中
            if (length > 0) {
                // 从原文中截取 并放入set
                set.add(txt.substring(i, i + length));
                // 减1的原因，因为for会自增
                i = i + length - 1;
            }
        }
        return set;
    }

    private static Map m_kwWordMap = null;
    // 最小匹配原则,如果关键词中有中国和中国人，文本内容为“我是中国人”，最小匹配原则匹配出中国
    public static final int MIN_MATCH_TYPE = 1;
    // 最大匹配原则,如果关键词中有中国和中国人，文本内容为“我是中国人”，最大匹配原则匹配出中国人
    public static final int MAX_MATCH_TYPE = 2;
}
