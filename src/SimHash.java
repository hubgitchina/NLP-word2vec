package com.huafa.job.task;

import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @ClassName: SimHash
 * @Description: TODO
 * @Author wangpeng
 * @Date 2019-8-23 19:20
 * @Version 1.0
 */
public class SimHash {

	private String tokens;
	private BigInteger strSimHash;
	private int hashbits = 128;

	/**
	 * @Author：sks
	 * @Description 构造函数： @tokens：
	 *              特征值字符串，括号内的数值是权重，媒体(56),发展(31),技术(15),传播(12),新闻(11),用户(10),信息(10),生产(9)
	 */
	public SimHash(String tokens) {

		this.tokens = tokens;
		this.strSimHash = this.simHash();
	}

	public SimHash(String tokens, int hashbits) {

		this.tokens = tokens;
		this.hashbits = hashbits;
		this.strSimHash = this.simHash();
	}

	public String getSimHash() {

		return this.strSimHash.toString();
	}

	private BigInteger simHash() {

		int[] v = new int[this.hashbits];
		StringTokenizer stringTokens = new StringTokenizer(this.tokens, ",");
		while (stringTokens.hasMoreTokens()) {
			// 媒体(56)
			String temp = stringTokens.nextToken();
			String token = temp.substring(0, temp.indexOf("("));
			int weight = Integer.parseInt(temp.substring(temp.indexOf("(") + 1, temp.indexOf(")")));
			BigInteger t = this.hash(token);
			for (int i = 0; i < this.hashbits; i++) {
				BigInteger bitmask = new BigInteger("1").shiftLeft(i);
				if (t.and(bitmask).signum() != 0) {
					v[i] += 1 * weight;// 加权
				} else {
					v[i] -= 1 * weight;
				}
			}
		}
		BigInteger fingerprint = new BigInteger("0");
		for (int i = 0; i < this.hashbits; i++) {
			if (v[i] >= 0) {
				fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
			}
		}
		return fingerprint;
	}

	private BigInteger hash(String source) {

		if (source == null || source.length() == 0) {
			return new BigInteger("0");
		} else {
			char[] sourceArray = source.toCharArray();
			BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
			BigInteger m = new BigInteger("1000003");
			BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
			for (char item : sourceArray) {
				BigInteger temp = BigInteger.valueOf((long) item);
				x = x.multiply(m).xor(temp).and(mask);
			}
			x = x.xor(new BigInteger(String.valueOf(source.length())));
			if (x.equals(new BigInteger("-1"))) {
				x = new BigInteger("-2");
			}
			return x;
		}
	}

	/**
	 * @Author：sks
	 * @Description：计算海明距离
	 * @leftSimHash,rightSimHash:要比较的信息指纹 @hashbits：128
	 */
	public static int hammingDistance(String leftSimHash, String rightSimHash, int hashbits) {

		BigInteger m = new BigInteger("1").shiftLeft(hashbits).subtract(new BigInteger("1"));
		BigInteger left = new BigInteger(leftSimHash);
		BigInteger right = new BigInteger(rightSimHash);
		BigInteger x = left.xor(right).and(m);
		int count = 0;
		while (x.signum() != 0) {
			count += 1;
			x = x.and(x.subtract(new BigInteger("1")));
		}
		return count;
	}

	public static void main(String[] args) {

		String s = "你，知道,里约,奥运会,媒体,玩出,了,哪些,新,花样";
		SimHash hash1 = new SimHash(s, 128);
//		System.out.println(hash1.getSimHash() + "  " + hash1.getSimHash().bitCount());
		s = "我，不，知道,里约,奥运会,媒体,玩出,了,哪些,新,花样";
		SimHash hash2 = new SimHash(s, 128);
//		System.out.println(hash2.getSimHash() + "  " + hash2.getSimHash().bitCount());
		s = "视频,直播,全球,知名,媒体,的,战略,转移";
		SimHash hash3 = new SimHash(s, 128);
//		System.out.println(hash3.getSimHash() + "  " + hash3.getSimHash().bitCount());
		System.out.println("============================");
		System.out.println(SimHash.hammingDistance(hash1.getSimHash(), hash2.getSimHash(), 128));

		System.out.println(SimHash.hammingDistance(hash1.getSimHash(), hash3.getSimHash(), 128));

		System.out.println(SimHash.hammingDistance(hash2.getSimHash(), hash3.getSimHash(), 128));

	}

}
