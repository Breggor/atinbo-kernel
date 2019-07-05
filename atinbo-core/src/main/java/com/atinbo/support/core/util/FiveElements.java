package com.atinbo.support.core.util;


import com.google.common.collect.TreeMultimap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 五行工具类
 */
public class FiveElements {
    //地支
    public final static String ELEMENTS_DZ[] = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    final static String JMSHT[] = {"金", "木", "水", "火", "土"};
    //天干
    final static String ELEMENTS_TG[] = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    //属性
    final static String ELEMENTS_TG_VALUES[] = {"木", "木", "火", "火", "土", "土", "金", "金", "水", "水"};
    //天干属性map
    final static Map<String, Object[]> TG_MAP = new HashMap<String, Object[]>();
    //属性
    final static String ELEMENTS_DZ_VALUES[] = {"水", "土", "木", "木", "土", "火", "火", "土", "金", "金", "土", "水"};
    //地支属性map
    final static Map<String, Object[]> DZ_MAP = new HashMap<String, Object[]>();
    //时辰
    final static Integer TIAN_TIME[] = {23, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21};
    final static String TIAN_TIME0[] = {"甲己", "乙庚", "丙辛", "丁壬", "戊癸"};
    final static String TIAN_TIME1[] = {"甲子", "乙丑", "丙寅", "丁卯", "戊辰", "己巳", "庚午", "辛未", "壬申", "癸酉", "甲戌", "乙亥"};
    final static String TIAN_TIME2[] = {"丙子", "丁丑", "戊寅", "己卯", "庚辰", "辛巳", "壬午", "癸未", "甲申", "乙酉", "丙戌", "丁亥"};
    final static String TIAN_TIME3[] = {"戊子", "己丑", "庚寅", "辛卯", "壬辰", "癸巳", "甲午", "乙未", "丙申", "丁酉", "戊戌", "己亥"};
    final static String TIAN_TIME4[] = {"庚子", "辛丑", "壬寅", "癸卯", "甲辰", "乙巳", "丙午", "丁未", "戊申", "己酉", "庚戌", "辛亥"};
    final static String TIAN_TIME5[] = {"壬子", "癸丑", "甲寅", "乙卯", "丙辰", "丁巳", "戊午", "己未", "庚申", "辛酉", "壬戌", "癸亥"};
    final static String TIAN_TIME6[][] = {TIAN_TIME1, TIAN_TIME2, TIAN_TIME3, TIAN_TIME4, TIAN_TIME5};
    private static final Map<String, Integer> elementHash = new HashMap<>();

    static {
        elementHash.put("金", 1);
        elementHash.put("木", 2);
        elementHash.put("水", 3);
        elementHash.put("火", 4);
        elementHash.put("土", 5);
    }

    static {
        int i = 0;
        for (String value : ELEMENTS_TG) {
            TG_MAP.put(value, new Object[]{ELEMENTS_TG_VALUES[i], i});
            i++;
        }
    }

    static {
        int j = 0;
        for (String value : ELEMENTS_DZ) {
            DZ_MAP.put(value, new Object[]{ELEMENTS_DZ_VALUES[j], j});
            j++;
        }
    }

    private static int indexNum(int timeDate) {
        return (timeDate == 23 || timeDate == 0) ? 0 : (timeDate % 2 == 0) ? (timeDate / 2) : (timeDate / 2 + 1);
    }

    /**
     * 天干地支查询
     *
     * @param birthday
     * @return
     */
    public static String[] getZodiac(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        String[] elementDateStr = new String[8];
        calendar.setTime(birthday);
        String elementDates = "";
        try {
            String zodiac = new Lunar(calendar).cyclical();
            int i = 0;
            for (String v : (zodiac).split(",")) {
                elementDateStr[i++] = v;
                elementDates += v;
            }
            //通过天干和时辰查出对应时段“天干地支”计时表示
            int dayTG = (Integer) TG_MAP.get(elementDateStr[4])[1];
            String hoursZodiac = TIAN_TIME6[(dayTG == 4) ? 4 : (dayTG + 1) / 5][indexNum(birthday.getHours())];
            elementDateStr[i++] = hoursZodiac.substring(0, 1);
            elementDates += hoursZodiac.substring(0, 1);
            elementDateStr[i++] = hoursZodiac.substring(1, 2);
            elementDates += hoursZodiac.substring(1, 2);
        } catch (Exception e) {
            System.out.println(e);
        }
//        System.out.println(elementDates);
        return elementDateStr;
    }

    /**
     * 五行查询
     *
     * @param birthday
     * @return
     */
    public static String fiveElementValue(Date birthday) {
        String zodiac[] = null;
        zodiac = getZodiac(birthday);
        //统计元素
        StringBuffer elements = new StringBuffer();
        for (int i = 0; i < zodiac.length; i++) {
            if (i % 2 == 0) {
                elements.append(TG_MAP.get(zodiac[i])[0]);
            } else {
                elements.append(DZ_MAP.get(zodiac[i])[0]);
            }
        }
        System.out.println("proc==" + elements);
        TreeMultimap map = TreeMultimap.create();
        for (int j = 0; j < 5; j++) {
            String temp = elements.toString().replace(JMSHT[j], "");
            map.put(elements.length() - temp.length(), JMSHT[j]);
        }
        System.out.println("result==" + map);
        return ((SortedSet) map.asMap().lastEntry().getValue()).last().toString();
    }

    public static void main(String[] args) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(1982, 05, 19, 00, 00);
            System.out.println(cal.getTime().toLocaleString());
            System.out.println(fiveElementValue(cal.getTime()));
//            System.out.println(getZodiac(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Lunar {
    final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    final static long[] lunarInfo = new long[]
            {0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
                    0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
                    0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
                    0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
                    0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
                    0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
                    0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
                    0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
                    0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
                    0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
                    0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
                    0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
                    0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
                    0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
                    0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static Calendar cal = Calendar.getInstance();
    private static int[] mon = {11, 12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private static String[] tian1 = {"丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁"};
    private static String[] tian2 = {"戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己"};
    private static String[] tian3 = {"庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛"};
    private static String[] tian4 = {"壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private static String[] tian5 = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙"};
    private static String[] di = {"寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥", "子", "丑"};
    private static String[] solaritem = {"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏",
            "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
    private static long[] solarTime;
    private static long[] oddSolarTime = new long[12];
    private int year;
    private int month;
    private int day;
    private boolean leap;


    /**
     * 传出y年m月d日对应的农历.
     * yearCyl3:农历年与1864的相差数			  ?
     * monCyl4:从1900年1月31日以来,闰月数
     * dayCyl5:与1900年1月31日相差的天数,再加40	  ?
     *
     * @param cal
     * @return
     */
    public Lunar(Calendar cal) {
        Lunar.cal = cal;
        @SuppressWarnings("unused") int yearCyl, monCyl, dayCyl;
        int leapMonth = 0;
        Date baseDate = null;
        try {
            baseDate = chineseDateFormat.parse("1900-1-31");
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }

        //求出和1900年1月31日相差的天数
        int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000L);
        dayCyl = offset + 40;
        monCyl = 14;

        //用offset减去每农历年的天数
        // 计算当天是农历第几天
        //i最终结果是农历的年份
        //offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
            daysOfYear = yearDays(iYear);
            offset -= daysOfYear;
            monCyl += 12;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
            monCyl -= 12;
        }
        //农历年份
        year = iYear;

        yearCyl = iYear - 1864;
        leapMonth = leapMonth(iYear); //闰哪个月,1-12
        leap = false;

        //用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            //闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
                --iMonth;
                leap = true;
                daysOfMonth = leapDays(year);
            } else
                daysOfMonth = monthDays(year, iMonth);

            offset -= daysOfMonth;
            //解除闰月
            if (leap && iMonth == (leapMonth + 1)) leap = false;
            if (!leap) monCyl++;
        }
        //offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --iMonth;
                --monCyl;
            }
        }
        //offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
            --monCyl;
        }
        month = iMonth;
        day = offset + 1;
//        if (null == solarTime) {
//            int i;
//            int j = 0;
//            solarTime = getInstance().JQtest2(cal.get(Calendar.YEAR));
//            for (i = 0; i < solarTime.length; i = i + 2) {
//                oddSolarTime[j] = solarTime[i];
//                j++;
//            }
//        }
    }

    //====== 传回农历 y年的总天数
    final private static int yearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0) sum += 1;
        }
        return (sum + leapDays(y));
    }

    //====== 传回农历 y年闰月的天数
    final private static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    //====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
    final private static int leapMonth(int y) {
        return (int) (lunarInfo[y - 1900] & 0xf);
    }

    //====== 传回农历 y年m月的总天数
    final private static int monthDays(int y, int m) {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }

    //====== 传入 月日的offset 传回干支, 0=甲子
    final private static String cyclicalm(int num) throws ParseException {
        final String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        final String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        String tian = Gan[num % 10];
        int monget = getMon(cal);
        String tinmon = "";
        if (tian.equals("甲") || tian.equals("己")) {
            tinmon = tian1[monget - 1] + "," + di[monget - 1];
        } else if (tian.equals("乙") || tian.equals("庚")) {
            tinmon = tian2[monget - 1] + "," + di[monget - 1];
        } else if (tian.equals("丙") || tian.equals("辛")) {
            tinmon = tian3[monget - 1] + "," + di[monget - 1];
        } else if (tian.equals("丁") || tian.equals("壬")) {
            tinmon = tian4[monget - 1] + "," + di[monget - 1];
        } else if (tian.equals("戊") || tian.equals("癸")) {
            tinmon = tian5[monget - 1] + "," + di[monget - 1];
        }
        int[] day = getDay(cal);
        return (Gan[num % 10] + "," + Zhi[num % 12] + "," + tinmon + "," + Gan[day[0] - 1] + "," + Zhi[day[1] - 1]);
    }

    public static String getChinaDayString(int day) {
        String chineseTen[] = {"初", "十", "廿", "卅"};
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";
        if (day == 10)
            return "初十";
        else
            return chineseTen[day / 10] + chineseNumber[n];
    }

    /**
     *
     */

    public static int getMon(Calendar cal) throws ParseException {
//        updateSolarItem(cal);
//		SolarTerm solar = new SolarTerm();
        Long time = cal.getTime().getTime();
//		long[] solarTime = solar.JQtest(cal.get(Calendar.YEAR));
        if (oddSolarTime[0] > time) {
            return mon[0];
        } else if (oddSolarTime[11] <= time) {
            return mon[12];
        } else {
            for (int i = 0; i < 13; i++) {
                if (oddSolarTime[i] <= time && time < oddSolarTime[i + 1]) {
                    return mon[i + 1];
                }
            }
        }
        return 0;
    }

//    public static void updateSolarItem(Calendar cal) {
//        Calendar cal2 = Calendar.getInstance();
//        cal2.setTime(cal.getTime());
//        cal2.set(Calendar.DAY_OF_YEAR, -1);
////        if (cal2.get(Calendar.YEAR) != cal.get(Calendar.YEAR) || null == solarTime) {
////            int i;
////            int j = 0;
//////            solarTime = getInstance().JQtest2(cal.get(Calendar.YEAR));
////            for (i = 0; i < solarTime.length; i = i + 2) {
////                oddSolarTime[j] = solarTime[i];
////                j++;
////            }
////        }
//    }

    //获取当前节气
    public static Map<String, Object> getSolar(Calendar cal) throws ParseException {
//        updateSolarItem(cal);
//		SolarTerm solar = new SolarTerm();
        Map<String, Object> map = new HashMap<String, Object>();
        Long time = chineseDateFormat.parse(chineseDateFormat.format(cal.getTime())).getTime();
//		long[] solarTime = solar.JQtest2(cal.get(Calendar.YEAR));
        String currTime = chineseDateFormat.format(System.currentTimeMillis());
        Calendar cal2 = Calendar.getInstance();
        int total;
        if (solarTime[0] > time) {
            cal2.setTimeInMillis(solarTime[23]);
            cal2.set(Calendar.YEAR, cal2.get(Calendar.YEAR) - 1);
            String begin = chineseDateFormat.format(cal2.getTime().getTime());
            map.put("currBegin", begin);
            map.put("currEnd", chineseDateFormat.format(solarTime[0]));
            map.put("solarItem", solaritem[23]);
            map.put("currTime", currTime);
            total = (int) ((solarTime[0] - chineseDateFormat.parse(begin).getTime()) / (1000 * 60 * 60 * 24));
            if (time.equals(chineseDateFormat.parse(begin).getTime())) {
                map.put("dayOfSolar", 1);
            } else {
                map.put("dayOfSolar", ((int) (chineseDateFormat.parse(currTime).getTime() - chineseDateFormat.parse(begin).getTime()) / (1000 * 60 * 60 * 24)) + 1);
            }
            map.put("total", total);
            return map;
        } else if (solarTime[23] <= time) {
            cal2.setTimeInMillis(solarTime[0]);
            cal2.set(Calendar.YEAR, cal2.get(Calendar.YEAR) + 1);
            String end = chineseDateFormat.format(cal2.getTime().getTime());
            map.put("currBegin", chineseDateFormat.format(solarTime[23]));
            map.put("currEnd", cal.get(Calendar.YEAR) + "-12-31");
            map.put("solarItem", solaritem[23]);
            map.put("currTime", currTime);
            total = (int) ((chineseDateFormat.parse(end).getTime() - solarTime[23]) / (1000 * 60 * 60 * 24));
            if (time.equals(solarTime[23])) {
                map.put("dayOfSolar", 1);
            } else if (time.equals(chineseDateFormat.parse(end).getTime())) {
                map.put("dayOfSolar", total);
            } else {
                map.put("dayOfSolar", ((int) (chineseDateFormat.parse(currTime).getTime() - solarTime[23]) / (1000 * 60 * 60 * 24)) + 1);
            }
            map.put("total", total);
            return map;
        } else {
            for (int i = 0; i < 25; i++) {
                if (solarTime[i] <= time && time < solarTime[i + 1]) {
                    map.put("currBegin", chineseDateFormat.format(solarTime[i]));
                    map.put("currEnd", chineseDateFormat.format(solarTime[i + 1] - (24 * 60 * 60 * 1000)));
                    map.put("solarItem", solaritem[i]);
                    map.put("currTime", currTime);
                    total = (int) ((solarTime[i + 1] - solarTime[i]) / (1000 * 60 * 60 * 24));
                    if (time.equals(solarTime[i])) {
                        map.put("dayOfSolar", 1);
                    } else if (time.equals(solarTime[i + 1])) {
                        map.put("dayOfSolar", total);
                    } else {
                        map.put("dayOfSolar", ((int) (chineseDateFormat.parse(currTime).getTime() - solarTime[i]) / (1000 * 60 * 60 * 24)) + 1);
                    }
                    map.put("total", total);
                    return map;
                }
            }
        }
        return map;
    }

    /**
     * @param cal
     * @return :String
     * @throws ParseException
     * @description:获取当前节气名称
     * @create :2014-5-14
     * @author :shanlingyun
     */
    public static String getSolarTerm(Calendar cal) throws ParseException {
//        updateSolarItem(cal);
        Long time = chineseDateFormat.parse(chineseDateFormat.format(cal.getTime())).getTime();
        Calendar cal2 = Calendar.getInstance();
        if (solarTime[0] > time) {
            cal2.setTimeInMillis(solarTime[23]);
            cal2.set(Calendar.YEAR, cal2.get(Calendar.YEAR) - 1);
            return solaritem[23];
        } else if (solarTime[23] <= time) {
            cal2.setTimeInMillis(solarTime[0]);
            cal2.set(Calendar.YEAR, cal2.get(Calendar.YEAR) + 1);
            return solaritem[23];
        } else {
            for (int i = 0; i < 25; i++) {
                if (solarTime[i] <= time && time < solarTime[i + 1]) {
                    return solaritem[i];
                }
            }
        }
        return null;
    }

    public static int[] getDay(Calendar cal) {
        int y;
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);
        int c;
        int[] day = new int[2];
        if (m == 1) {
            m = 13;
            cal.add(Calendar.YEAR, -1);
            y = Integer.valueOf(String.valueOf(cal.get(Calendar.YEAR)).substring(2));
            c = Integer.valueOf(String.valueOf(cal.get(Calendar.YEAR)).substring(0, 2));
        } else if (m == 2) {
            m = 14;
            cal.add(Calendar.YEAR, -1);
            y = Integer.valueOf(String.valueOf(cal.get(Calendar.YEAR)).substring(2));
            c = Integer.valueOf(String.valueOf(cal.get(Calendar.YEAR)).substring(0, 2));
        } else {
            y = Integer.valueOf(String.valueOf(cal.get(Calendar.YEAR)).substring(2));
            c = Integer.valueOf(String.valueOf(cal.get(Calendar.YEAR)).substring(0, 2));
        }
        int g = (4 * c + (c / 4) + 5 * y + (y / 4) + ((3 * (m + 1)) / 5) + d - 3) % 10;
        int z = (8 * c + (c / 4) + 5 * y + (y / 4) + ((3 * (m + 1)) / 5) + d + 7 + (m / 2 == 0 ? 0 : 6)) % 12;
        day[0] = g == 0 ? 10 : g;
        day[1] = z == 0 ? 12 : z;
        return day;
    }

    public static void main(String[] args) throws ParseException {
        Calendar today = Calendar.getInstance();
//		today.setTime(chineseDateFormat.parse("1900-1-1 00:00:00"));
//		Lunar lunar = new Lunar(today);
//		System.out.println("北京时间：" + chineseDateFormat.format(today.getTime()) + "　农历" + lunar);
////		System.out.println(Lunar.getSolar(cal));
////		System.out.println(lunar.year);
////		System.out.println("----------------------------------------------------");
////	   System.out.println(lunar.getMon(today));
////		System.out.println(lunar.getSolar(today));
////		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
////		Calendar cal = Calendar.getInstance();
////		Calendar cal2 = Calendar.getInstance();
////		cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-1);
////		System.out.println(cal2.get(Calendar.YEAR));
////		System.out.println(cal.get(Calendar.YEAR));
////		Calendar today = Calendar.getInstance();
        today.setTime(chineseDateFormat.parse("1983-12-06"));
//    	Lunar lunar = new Lunar(today);
//		System.out.println(lunar.cyclical());
////		System.out.println(lunarInfo[0]);


    }

    //====== 传回农历 y年的生肖
    final public String animalsYear() {
        final String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
        if (cal.get(Calendar.MONTH) == 1 && cal.get(Calendar.DAY_OF_MONTH) < 4) {
            return Animals[(cal.get(Calendar.YEAR) - 4 - 1) % 12];
        }
        return Animals[(cal.get(Calendar.YEAR) - 4) % 12];
    }

    //====== 传入 offset 传回干支, 0=甲子
    final public String cyclical() throws ParseException {
        if (cal.get(Calendar.MONTH) == 1 && cal.get(Calendar.DAY_OF_MONTH) < 4) {
            return cyclicalm(year - 1 - 1900 + 36);
        }
        return (cyclicalm(year - 1900 + 36));
    }

    public String toString() {
        return year + "年" + (leap ? "闰" : "") + chineseNumber[month - 1] + "月" + getChinaDayString(day);
    }
}

