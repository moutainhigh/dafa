package pers.dafacloud.utils.randomHaoMa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CalculateAC {
	public static ArrayList<List<String>> list = null;	
	
	public static void main(String[] args) {
		/*String[] ss= {"1","2","3","4","5","6","7","8","9","10","11"};
		List<String> list=rx(ss, 5);
		System.out.println(list);
		long len=combinations(list, 2);
		System.out.println(len);*/
		
		int index =5000+ (int) (Math.random() * 1000);
		System.out.println(index);
	}

	/**
	 * 数组里面随机选择1个数据
	 * */
	public static List<String> rx(String[] ss, int n) {
		List<String> ids = new ArrayList<String>();
		
		for (String s : ss) {
			ids.add(s);
		}
		List<String> ret = new ArrayList<String>();

		int index = 0;

		for (int i = 0; i < n; i++) {

			index = (int) (Math.random() * ids.size());
			ret.add(ids.get(index));
			ids.remove(index);
		}
		//排序
		Collections.sort(ret,new Comparator<String>(){
			//满足负数表示第一个串小，0表示两串相等，正数表示第一串较大
			@Override
			public int compare(String arg0, String arg1) {
				if(arg0.length()>arg1.length()) {
					return 1;
				}
				if(arg0.length()<arg1.length()) {
					return -1;
				}else {
					return arg0.compareTo(arg1);
				}
			}
		});
		return ret;

	}
	/**
	 * 组合选择（从列表中选择n个组合）
	 * 
	 * @param dataList 待选列表
	 * @param n        选择个数
	 */
	public static ArrayList<List<String>> combinationSelect(String[] dataList, int n) {
		//System.out.println(String.format("C(%d, %d) = %d", dataList.length, n, combination(dataList.length, n)));
		list = new ArrayList<List<String>>();
		combinationSelect(dataList, 0, new String[n], 0);
		return list;
	}
	/**
	 * 组合选择
	 * 
	 * @param dataList    待选列表
	 * @param dataIndex   待选开始索引
	 * @param resultList  前面（resultIndex-1）个的组合结果
	 * @param resultIndex 选择索引，从0开始
	 */
	private static void combinationSelect(String[] dataList, int dataIndex, String[] resultList, int resultIndex) {
		int resultLen = resultList.length;
		int resultCount = resultIndex + 1;
		if (resultCount > resultLen) { // 全部选择完时，输出组合结果
			//System.out.println(Arrays.asList(resultList)); 
			List<String> list2 =new ArrayList<>();
			for (String s :resultList ) {
				list2.add(s);
			}list.add(list2); //resultList一直指向的是一个地址，所以不能直接添加
			//排序
			Collections.sort(list2,new Comparator<String>(){
				//满足负数表示第一个串小，0表示两串相等，正数表示第一串较大
				@Override
				public int compare(String arg0, String arg1) {
					if(arg0.length()>arg1.length()) {
						return 1;
					}
					if(arg0.length()<arg1.length()) {
						return -1;
					}else {
						return arg0.compareTo(arg1);
					}
				}
			});
			return;
		}

		// 递归选择下一个
		for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {
			resultList[resultIndex] = dataList[i];
			// System.out.println("resultList[resultIndex]:"+resultList[resultIndex]);
			//System.out.print("11:");
			//System.out.println(Arrays.asList(resultList));
			combinationSelect(dataList, i + 1, resultList, resultIndex + 1);
			
		}
		
		//System.out.println("aaa");
	}
	/**
	 * 组合选择（从列表中选择n个组合）
	 * 
	 * @param dataList 待选列表
	 * @param n        选择个数
	 */
	/*public static void combinationSelect(String[] dataList, int n) {
		System.out.println(String.format("C(%d, %d) = %d", dataList.length, n, combination(dataList.length, n)));

	}*/
	/**
	 * 组合选择（从列表中选择n个组合）
	 * 
	 * @param dataList 待选列表
	 * @param n        选择个数
	 */
	public static long combinations(List<String> list, int n) {
		return combination(list.size(), n);

	}
	
	/**
	 * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
	 * 
	 * @param n
	 * @return
	 */
	public static long factorial(int n) {
		return (n > 1) ? n * factorial(n - 1) : 1;
	}
	
	/**
	 * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	public static long combination(int n, int m) {
		return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;
	}

}
