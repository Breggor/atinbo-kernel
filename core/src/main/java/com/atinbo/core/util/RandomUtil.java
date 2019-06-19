package com.atinbo.core.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class RandomUtil {

    private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final static String CHAR_VALUE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String NUMBER_VALUE = "0123456789";

    private static char[] numbersAndLetters = null;
    private static char[] numbersRandom = null;
    private static Random random = new Random();

    static {
        numbersAndLetters = (CHAR_VALUE).toCharArray();
        numbersRandom = (NUMBER_VALUE).toCharArray();

    }

    /**
     * 生成一个指定长度的随机字符串（只包含大小写字母、数字）
     *
     * @param length 长度
     * @return String
     */
    public static String nextString(int length) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    /**
     * 生成一个指定长度的随机字符串（只包含大小写字母）
     *
     * @param length 长度
     * @return String
     */
    public static String nextLetters(int length) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(letterChar.length())));
        }

        return sb.toString();
    }

    /**
     * 生成指定范围内的随机数
     *
     * @param from
     * @param to
     * @return
     */
    public static long nextLong(long from, long to) {
        return from + (long) (Math.random() * (to - from));
    }

    /**
     * 生成完全不重复的随机数
     *
     * @return
     */
    public static int nextInt(int n) {
        SecureRandom random = new SecureRandom();
        return random.nextInt(n);
    }

    /**
     * 生成完全不重复的随机数
     *
     * @return
     */
    public static int nextInt() {
        int MAX_VALUE = 2147483647;
        SecureRandom random = new SecureRandom();
        return random.nextInt(MAX_VALUE);

//		int[] array = {0,1,2,3,4,5,6,7,8,9};
//      Random rand = new Random();
//      for (int i = 10; i > 1; i--) {
//          int index = rand.nextInt(i);
//          int tmp = array[index];
//          array[index] = array[i - 1];
//          array[i - 1] = tmp;
//      }
//      int result = 0;
//      for(int i = 0; i < 6; i++)
//          result = result * 10 + array[i];
    }

    /**
     * 产生随机字符
     *
     * @return
     */
    public static char nextChar() {
        return (char) ('a' + Math.random() * ('z' - 'a' + 1));
    }

    /**
     * @return
     */
    private static int getRandomInt() {
        int r = 0;
        while ((r = random.nextInt(10)) == 0) {

        }
        return r;
    }

    /**
     * 根据用户需要的长度产生随机数
     *
     * @param length 需要的长度
     * @return
     */
    public static String getRandom(int length) {
        long s = (long) (Math.random() * GregorianCalendar.getInstance().getTimeInMillis());
        double maxInt = Math.pow(10, length);
        while (s < maxInt) {
            s = s * getRandomInt();
        }
        return ("" + s).substring(0, length);
    }


    /*
     * 随机生成用户名
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[random.nextInt(35)];
        }
        return new String(randBuffer);
    }


    /*
     * 随机生成六位验证码
     */
    public static final String randomNumberString(int length) {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersRandom[random.nextInt(10)];
        }
        return new String(randBuffer);
    }

    /*
     * 随机生成规定长度长度的数字
     * @param min 范围最小值
     * @param max 范围最大值，生成多少随机数
     * @return
     */
    public static Integer[] randomInt(Integer min, Integer max) {
        Random random = new Random();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < max * 10; i++) {
            Integer num = random.nextInt(max) % (max - min + 1) + min;
            if (!list.contains(num)) {
                list.add(num);
            }
        }
        return list.toArray(new Integer[0]);
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * 随机出来的数不包括最大值和0
     * 如：
     * [1, 4, 3), 指定范围内包含1,2,3三个数字。
     * (0, 4, 3), 指定范围内包含1,2,3三个数字。
     * (0, 3, 3), 指定范围内包含1,2三个数字，且无法满足3个随机数，会导致死循环。
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] randomInt(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

//	private void getNoRepeatedRandom() {
//    int startArray[] = {0,1,2,3,4,5,6,7,8,9};//seed array
//    int N = 10; //随机数个数
//    int resultArray[] = new int [10];//结果存放在里面
//
//    for(int i = 0; i < N; i++) {
//        int seed = random(0, startArray.length - i);//从剩下的随机数里生成
//        resultArray[i] = startArray[seed];//赋值给结果数组
//        startArray[seed] = startArray[startArray.length - i - 1];//把随机数产生过的位置替换为未被选中的值。
//    }
//}
}
