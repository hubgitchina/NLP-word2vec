package com.huafa.job.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import org.apache.commons.io.FileUtils;

/**
 * @ClassName: SimilarityUtil
 * @Description: TODO
 * @Author wangpeng
 * @Date 2019-8-22 18:54
 * @Version 1.0
 */
public class SimilarityUtil {

	static {
		CustomDictionary.add("子类");
		CustomDictionary.add("父类");
	}

	private SimilarityUtil() {

	}

	public static double getSimilarity(String sentence1, String sentence2) {

		List<String> sent1Words = getSplitWords(sentence1);
		List<String> sent2Words = getSplitWords(sentence2);
		List<String> allWords = mergeList(sent1Words, sent2Words);

		int[] statistic1 = statistic(allWords, sent1Words);
		int[] statistic2 = statistic(allWords, sent2Words);

		double dividend = 0;
		double divisor1 = 0;
		double divisor2 = 0;
		for (int i = 0; i < statistic1.length; i++) {
			dividend += statistic1[i] * statistic2[i];
			divisor1 += Math.pow(statistic1[i], 2);
			divisor2 += Math.pow(statistic2[i], 2);
		}

		return dividend / (Math.sqrt(divisor1) * Math.sqrt(divisor2));
	}

	private static int[] statistic(List<String> allWords, List<String> sentWords) {

		int[] result = new int[allWords.size()];
		for (int i = 0; i < allWords.size(); i++) {
			result[i] = Collections.frequency(sentWords, allWords.get(i));
		}
		return result;
	}

	private static List<String> mergeList(List<String> list1, List<String> list2) {

		List<String> result = new ArrayList<>();
		result.addAll(list1);
		result.addAll(list2);
		return result.stream().distinct().collect(Collectors.toList());
	}

	private static List<String> getSplitWords(String sentence) {

		// 标点符号会被单独分为一个Term，去除之
		return HanLP.segment(sentence).stream().map(a -> a.word).filter(
				s -> !"`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？".contains(s))
				.collect(Collectors.toList());
	}

    public static void main(String[] args) throws IOException {

		long l3 = System.currentTimeMillis();

//        String s1 = "子类可以覆盖父类的成员方法，也可以覆盖父类的成员变量";
//        String s2 = "子类不可以覆盖父类的成员方法，也不可以覆盖父类的成员变量";

		String s1 = "他去吃饭了";
		String s2 = "她在看电影";
        System.out.println("\"" + s1 + "\"" + "与" + "\"" + s2 + "\"" + "的相似度是：" + SimilarityUtil.getSimilarity(s1, s2));


        String s7 = "我们把香蕉给猴子，因为它们饿了";
        String s8 = "我们把香蕉给猴子，因为它们熟透了";
        System.out.println("\"" + s7 + "\"" + "与" + "\"" + s8  + "\""+ "的相似度是：" + SimilarityUtil.getSimilarity(s7, s8));

		String s3 = "世界你好";
		String s4 = "你好世界";
		System.out.println("\"" + s3 + "\"" + "与" + "\"" + s4  + "\""+ "的相似度是：" + SimilarityUtil.getSimilarity(s3, s4));

		long l4 = System.currentTimeMillis();
		System.out.println(l4 - l3);
    }
}
