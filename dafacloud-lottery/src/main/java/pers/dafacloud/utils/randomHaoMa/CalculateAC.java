package pers.dafacloud.utils.randomHaoMa;

import java.util.*;

public class CalculateAC {

	public static ArrayList<List<String>> list = null;	
	
	public static void main(String[] args) {
		/*String[] ss= {"1","2","3","4","5","6","7","8","9","10","11"};
		List<String> list=rx(ss, 5);
		System.out.println(list);
		long len=combinations(list, 2);
		System.out.println(len);*/

		/*String[] s =new String[40];
		for (int i = 0; i < 40; i++) {
			s[i] = String.format("%02d",i+1);
			System.out.println(s[i]);
		}
		ArrayList<List<String>> list =  combinationSelect(s,4);
		String[] s2 =new String[40];
		for (int i = 0; i < 40; i++) {
			s2[i] = String.format("%02d",i+1+40);
			System.out.println(s2[i]);
		}
		System.out.println(list.size());
		ArrayList<List<String>> list2 =  combinationSelect(s2,4);*/
//		for (int i = 0; i < 10; i++) {
//			System.out.println((int)(Math.random()*3));
//		}

		String[] xuan5= {"01","02","04","05","06","07","08","09","10","11"};
		List<String> open = new ArrayList<>();
		open.add("01");open.add("07");open.add("04");open.add("02");open.add("11");
		combinationSelect(xuan5,6);
		System.out.println(list.size());
		for (List<String> list1: list) {
			if (list1.containsAll(open)){
				//System.out.println(list1);
				for (String s: list1) {
					System.out.print(s+" ");
				}
				System.out.println();
			}

		}
		System.out.println("=====");
		for (List<String> list1: list) {
			for (String s: list1) {
				System.out.print(s+" ");
			}
			System.out.println();
		}




	}
	public static int[] randomCommon(int min, int max, int n){
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while(count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if(num == result[j]){
					flag = false;
					break;
				}
			}
			if(flag){
				result[count] = num;
				count++;
			}
		}
		return result;
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
	 * @param //dataList1 待选列表
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
