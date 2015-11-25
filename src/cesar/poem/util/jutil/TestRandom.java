package cesar.poem.util.jutil;


import java.util.List;

import cesar.poem.util.RandomUtil;

import junit.framework.TestCase;

public class TestRandom extends TestCase {

	public void test1() {
		RandomUtil randomUtil = new RandomUtil();
		List<Integer> re = randomUtil.getRandomNum(600, 1000, 401);
		for(int i:re){
			System.out.println(i);
		}
		System.out.println(re.size());
	}
}
