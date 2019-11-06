/**
 *
 */
package com.atinbo.common.number;

import java.math.BigDecimal;

/**
 * @author :
 * @description:
 * @create :2015年10月29日
 * @name     :NumberUtil.java
 * <p>
 * 上海创乐人企业发展有限公司
 */
public class Numbers {

    public static BigDecimal parseNumber(Double nb, int rex) {
        BigDecimal bd = new BigDecimal(nb);
        return bd.setScale(rex, BigDecimal.ROUND_HALF_UP);
    }

    public static Number getParseNumber(Double nb, int rex) {
        Float bd = parseNumber(nb, rex).floatValue();
        if (nb.compareTo(0D) != 0) {
            if (bd.compareTo(0F) == 0) {
                bd = (float) (1 / Math.pow(10, rex));
            }
            if (bd.compareTo((float) bd.intValue()) == 0) {
                return bd.intValue();
            }
        } else {
            return 0;
        }
        return bd;
    }


    /**
     * 格式化小数 如果小数为0 则只取整数部分
     *
     * @param value 小数
     * @return 格式化后字符串
     */
    public static String formatDecimal(BigDecimal value) {

        if (value == null) {
            return "";
        }

        String valueTxt = value.toString();
        String[] split = valueTxt.split("\\.");
        if (split.length > 1) {
            Integer integer = Integer.valueOf(split[1]);
            if (integer == 0) {
                valueTxt = split[0];
            }
            ;
        }
        return valueTxt;
    }

    public static void main(String[] args) {
        Double d = 0.00001;
        System.out.println(Numbers.getParseNumber(d, 0));
    }
}