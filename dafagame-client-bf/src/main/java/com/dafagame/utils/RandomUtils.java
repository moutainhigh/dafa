package com.dafagame.utils;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by Administrator on 2016/9/13.
 * @version 1.0
 * 随机类
 */
public class RandomUtils {
//    public final static Random random = new Random();
    public static SecureRandom random;
    private static byte[] arrayBytes = new byte[256];
    static {
        try {
            random = SecureRandom.getInstance("NativePRNGNonBlocking");
            random.nextBytes(arrayBytes);
        } catch (Exception e) {

        }
    }

    /**
     * @param eList 随机的元素列表
     * @param <E> 泛型（返回类型）
     * @return 返回列表中随机的一个元素
     */
    public final static <E>E randomOne(List<E> eList){
        if(eList==null||eList.isEmpty()){
            throw new RuntimeException("eList is empty");
        }
        random.nextBytes(arrayBytes);
        return eList.get(random.nextInt(eList.size()));
    }

    /**
     * @param eArray 随机的元素数组
     * @param <E> 泛型（返回类型）
     * @return 返回数组中的一个元素
     */
    public final static <E>E randOne(E[] eArray){
        if(eArray==null||eArray.length==0){
            throw new RuntimeException("eArray is empty");
        }
        random.nextBytes(arrayBytes);
        return eArray[random.nextInt(eArray.length)];
    }

    /**
     * @param <E> 不删除取出元素随机一个列表
     * @return 返回随机到的列表
     */
    public final static <E>List<E> randListNotRemove(List<E> eList,int size){
        if(eList==null||eList.isEmpty()){
            throw new RuntimeException("eList is empty");
        }
        random.nextBytes(arrayBytes);
        List<E> resultList = new ArrayList<>();
        E randomE = null;
        while(size-->0){
            randomE = eList.get(random.nextInt(eList.size()));
            resultList.add(randomE);
        }
        return resultList;
    }


    /**
     * @param <E> 不删除元素取出一个列表
     * @return 返回随机到的列表
     */
    public final static <E>List<E> randListNotRemove(E[] eArray,int size){
        if(eArray==null||eArray.length==0){
            throw new RuntimeException("eArray is empty");
        }
        random.nextBytes(arrayBytes);
        List<E> resultList = new ArrayList<>();
        E randomE = null;
        while(size-->0){
            randomE = eArray[random.nextInt(eArray.length)];
            resultList.add(randomE);
        }
        return resultList;
    }

    /**
     * @param eArray 数组
     * @param size 大小
     * @param <E> 传入筛选列表的元素
     * @return 返回得到的列表
     */
    public final static <E>List<E> randListRemove(E[] eArray,int size){
        if(eArray==null||eArray.length<size){
            throw new RuntimeException("eArray's ele is not enough");
        }
        if(eArray.length==size){
            return Arrays.asList(eArray);
        }else{
            List<E> shaiList = Arrays.asList(eArray);
            return randListRemoveHandle(shaiList,size);
        }
    }



    /**
     * @param eList 元素列表
     * @param size 大小
     * @param <E> 传入筛选列表的元素
     * @return 返回得到的列表
     */
    public final static <E>List<E> randListRemove(List<E> eList,int size){
        if(eList==null||eList.size()<size){
            throw new RuntimeException("eList's ele is not enough");
        }
        if(eList.size()==size){
            return new ArrayList<>(eList);
        }else{
            return randListRemoveHandle(eList,size);
        }
    }


    /**
     * @param shaiList 筛选的列表
     * @param size 需要筛选出来的大小
     * @param <E> 处理的类型
     * @return 返回筛选的列表
     */
    private final static <E>List<E> randListRemoveHandle(List<E> shaiList,int size){
        List<E> result = new ArrayList<>();
        random.nextBytes(arrayBytes);
        int randSize = shaiList.size(),randIndex = 0;
        E addE = null;
        //size 控制取出大小
        while(size>0){
            randIndex = random.nextInt(randSize);
            addE = shaiList.get(randIndex);
            shaiList.remove(randIndex);
            randSize=shaiList.size();
            result.add(addE);
            size--;
        }
        return result;
    }

    /**
     * @param readyWeights 已经计算好权重的列表
     * @return 返回得到列表
     */
    //public final static <V extends ReadyWeight>V randOneReadyWeight(List<V> readyWeights){
    //    if(readyWeights!=null&&!readyWeights.isEmpty()){
    //        int lastWeight = readyWeights.get(readyWeights.size()-1).getLastWeight();
    //        if(lastWeight!=0) {
    //            random.nextBytes(arrayBytes);
    //            int randomInt = random.nextInt(lastWeight);
    //            for (ReadyWeight readyWeight : readyWeights) {
    //                if (randomInt >= readyWeight.getBeforeWeight() && randomInt <= readyWeight.getLastWeight()) {
    //                    return (V) readyWeight;
    //                }
    //            }
    //        }else{
    //            return readyWeights.get(readyWeights.size()-1);
    //        }
    //    }
    //    return null;
    //}

    /**
     * @param readyFloats 概率列表
     * @return 返回得到的下标
     */
    public final static int randIndexByReadyFloat(List<Float> readyFloats){
        int size = readyFloats.size();
        if(size>0) {
            float randFloat = randFloat(), totalProperty = readyFloats.get(size-1);
            //得到（应该得到的）概率
            randFloat *= totalProperty;
            for (int index = 0; index < readyFloats.size(); index++) {
                if (randFloat <= readyFloats.get(index)) {
                    return index;
                }
            }
        }
        return -1;
    }

    /**
     * @return 随机一个列表
     */
    //public final static <V extends ReadyWeight>List<V> randListReadyWeight(List<V> readyWeights,int size){
    //    List<V>  vList = null;
    //    if(readyWeights!=null&&!readyWeights.isEmpty()){
    //        random.nextBytes(arrayBytes);
    //        vList = new ArrayList<>();
    //        int lastWeight = readyWeights.get(readyWeights.size()-1).getLastWeight();
    //        int randomInt = random.nextInt(lastWeight);
    //        while(size>0){
    //            for(ReadyWeight readyWeight:readyWeights){
    //                if(randomInt>=readyWeight.getBeforeWeight()&&randomInt<=readyWeight.getLastWeight()){
    //                    vList.add((V)readyWeight);
    //                }
    //            }
    //            size--;
    //        }
    //    }
    //    return vList;
    //}

    /**
     * @return 返回随机到的int
     */
    public final static int randIntByMax(int max){
        random.nextBytes(arrayBytes);
        return random.nextInt(max);
    }

    /**
     * @param min 最小值
     * @param max 最大值
     * @return 返回随机值
     */
    public final static int getRandByMinAndMax(int min,int max){
        int rate = max-min,result = 0;
        if(min==max){
            result = min;
        }else{
            result = randIntByMax(rate)+min;
        }
        return result;
    }

    /**
     * @return 随机一个浮点型数
     */
    public final static float randFloat(){
        random.nextBytes(arrayBytes);
        return random.nextFloat();
    }

    public final static float randFloat(float max) {
        return randFloat()%max;
    }

    public final static float randFloat(float min,float max) {
        float cha = max - min;
        return randFloat()%cha+min;
    }


    public final static void main(String[] args){
        int i = 0,rand = 0,num = 0;
        Map<Integer,Integer> counter = new HashMap<>();
        while(i < 10000000) {
            try {
                random.nextBytes(arrayBytes);
                rand = random.nextInt(10);
                if (!counter.containsKey(rand)) {
                    counter.put(rand,0);
                }
                num = counter.get(rand);
                counter.put(rand,num+1);
                if (i == 100 || i == 1000
                        || i == 10000 || i == 100000
                        || i == 1000000 || i == 9999999) {
                    System.out.println(String.format("counter:%s counterMap:%s",i,counter));
                }
                i++;
//                Thread.sleep(1);
            } catch (Exception e) {

            }
        }
    }
}