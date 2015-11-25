package cesar.poem.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Preconditions;

public class RandomUtil {

	/**
	 * 在一定范围内生成给定数量的不重复随机数, 如果开始到结束的数量少于num的数量会抛出数组越界的错误，
	 * 如果相等则会返回一个排好序的数组，即从begin到end 否则返回随机数组(list)，不重复，不排序
	 * 
	 * @author Cesar
	 * @param begin
	 *            开始值
	 * @param end
	 *            结束值
	 * @param num
	 *            要求数量
	 * @return
	 */
	public List<Integer> getRandomNum(int begin, int end, int num) {
		int size = end-begin+1;
		int[] all = new int[size];
		Random random = new Random();
		List<Integer> result = new ArrayList<Integer>();
		int x;
		if (size < num) {
			Preconditions.checkArgument(false, "there is no enough number	", num);
		} 
		else {
			for(int i=0;i<size;i++){
				all[i] = begin++;
			}
			for(int i = 0;i<num;i++){
				x = random.nextInt(size);//获得坐标
				result.add(all[x]);
				all[x] = all[size-1];
				size--;
			}
		}
		return result;
	}
	/**
	 * 获得给定范围内一个随机数
	 */
	public int getOneRandom(int begin,int end){
		return (int)(Math.random()*(end-begin))+begin;
	}
}
