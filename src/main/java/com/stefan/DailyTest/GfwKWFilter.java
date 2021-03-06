package com.stefan.DailyTest;

import java.util.*;

public class GfwKWFilter {
	
	public void init(Set<String> words) {
		// 将敏感词库加入到HashMap中
		if (m_kwWordMap != null) {
			return;
		}
		m_kwWordMap = addWordToHashMap(words);
		System.out.println(m_kwWordMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void reInit(Set<String> words) {
		Map wordMap = new HashMap(words.size());
		for (String word : words) {
			Map nowMap = wordMap;
			for (int i = 0; i < word.length(); i++) {
				// 转换成char型
				char keyChar = word.charAt(i);
				// 获取
				Object tempMap = nowMap.get(keyChar);
				// 如果存在该key，直接赋值
				if (tempMap != null) {
					nowMap = (Map) tempMap;
				}
				// 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
				else {
					// 设置标志位
					Map<String, String> newMap = new HashMap<String, String>();
					newMap.put("isEnd", "0");
					// 添加到集合
					nowMap.put(keyChar, newMap);
					nowMap = newMap;
				}
				// 最后一个
				if (i == word.length() - 1) {
					nowMap.put("isEnd", "1");
				}
			}
		}
		m_kwWordMap = wordMap;
	}
	
	/**
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
	 * 中 = { isEnd = 0 国 = {<br>
	 * isEnd = 1 人 = {isEnd = 0 民 = {isEnd = 1} } 男 = { isEnd = 0 人 = { isEnd =
	 * 1 } } } } 五 = { isEnd = 0 星 = { isEnd = 0 红 = { isEnd = 0 旗 = { isEnd = 1
	 * } } } }
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private synchronized Map addWordToHashMap(Set<String> wordSet) {
		if (m_kwWordMap != null){
			return m_kwWordMap;
		}
		Map wordMap = new HashMap(wordSet.size());
		for (String word : wordSet) {
			Map nowMap = wordMap;
			for (int i = 0; i < word.length(); i++) {
				// 转换成char型
				char keyChar = word.charAt(i);
				// 获取
				Object tempMap = nowMap.get(keyChar);
				// 如果存在该key，直接赋值
				if (tempMap != null) {
					nowMap = (Map) tempMap;
				}
				// 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
				else {
					// 设置标志位
					Map<String, Integer> newMap = new HashMap<String, Integer>();
					newMap.put("isEnd", i == word.length() - 1 ? 1 : 0);
					// 添加到集合
					nowMap.put(keyChar, newMap);
					nowMap = newMap;
				}
				// 最后一个
				if (i == word.length() - 1) {
					nowMap.put("isEnd", "1");
				}
			}
		}
		return wordMap;
	}
	
	
	/**
	 * 判断文字是否包含敏感字符
	 * 
	 * @param txt
	 * @param matchType
	 * @return
	 */
	public boolean isHasWord(String txt, int matchType) {
		boolean flag = false;
		for (int i = 0; i < txt.length(); i++) {

			// 判断是否包含敏感字符
			int matchFlag = this.checkWord(txt, i, matchType);

			// 大于0存在，返回true
			if (matchFlag > 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取文字中的敏感词
	 * 
	 * @param txt
	 * @param matchType
	 * @return
	 */
	public Set<String> getWord(String txt, int matchType) {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < txt.length(); i++) {
			// 判断是否包含敏感字符
			int length = checkWord(txt, i, matchType);
			System.out.println("length=" + length);
			// 存在,加入list中
			if (length > 0) {
				set.add(txt.substring(i, i + length));
				// 减1的原因，是因为for会自增
				i = i + length - 1;
				System.out.println(i);
			}
		}
		return set;
	}
	
	/**
	 * 获取文字中的敏感词list
	 * 
	 * @param txt
	 * @param matchType
	 * @return
	 */
	public List<String> getWordList(String txt, int matchType) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < txt.length(); i++) {
			// 判断是否包含敏感字符
			int length = checkWord(txt, i, matchType);
			// 存在,加入list中
			if (length > 0) {
				list.add(txt.substring(i, i + length));
				// 减1的原因，是因为for会自增
				i = i + length - 1;
			}
		}
		return list;
	}
	
	/**
	 * 获取敏感字的map
	 * @param txt
	 * @param matchType
	 * @return
	 */
	public Map<String, Integer> getWordMap(String txt, int matchType) {
		Map<String, Integer> wordMap = new HashMap<String, Integer>();
		List<String> list = getWordList(txt, matchType);
		for (String str: list) {
			if (wordMap.containsKey(str)) {
				int count = wordMap.get(str) + 1;
				wordMap.put(str, count);
			} else {
				wordMap.put(str, 1);
			}
		}
		return wordMap;
	}
	
	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：
	 * 如果存在，则返回敏感词字符的长度，不存在返回0
	 * * 匹配任何带个字符
	 * % 匹配任何多个字符
	 * 
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return
	 */
	public int checkWord(String txt, int beginIndex, int matchType) {
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
					// 结束标志位为true
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

	public static final int MIN_MATCH_TYPE = 1;	// 最小匹配规则
	public static final int MAX_MATCH_TYPE = 2;	// 最大匹配规则
	private Map m_kwWordMap = null;//敏感词Map
}