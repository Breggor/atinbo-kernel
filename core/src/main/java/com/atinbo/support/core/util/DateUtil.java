package com.atinbo.support.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author robin on 2016年4月22日
 */
public abstract class DateUtil {

    // 格式化(yyyy-MM-dd)
    public final static SimpleDateFormat GET_YY_MM_DD = new SimpleDateFormat(
            "yyyy-MM-dd");

    // 格式化(yyyyMMdd)
    public final static SimpleDateFormat GETYYMMDD = new SimpleDateFormat(
            "yyyyMMdd");

    // 格式化(yyyy-MM-dd HH:mm:ss)
    public final static SimpleDateFormat GET_YY_MM_DD_HH_MM_SS = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");


    // 格式化(yyyy-MM-dd HH:mm)
    public final static SimpleDateFormat GET_YY_MM_DD_HH_MM = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");

    public final static SimpleDateFormat GET_MM_DD_DOT = new SimpleDateFormat(
            "MM.dd");

    public final static SimpleDateFormat GET_MM_DD_SLASH = new SimpleDateFormat(
            "MM/dd");

    // 格式化(HH:mm:ss)
    public final static SimpleDateFormat GET_HH_MM_SS = new SimpleDateFormat(
            "HH:mm:ss");

    public final static SimpleDateFormat GET_MM_DD_CHINESE = new SimpleDateFormat(
            "MM月dd日");

    public final static SimpleDateFormat GET_YY_WW = new SimpleDateFormat(
            "yyyyWW");

    public final static SimpleDateFormat GET_YY_MM = new SimpleDateFormat(
            "yyyyMM");

    public final static SimpleDateFormat GET_YY_MM_CHINESE = new SimpleDateFormat(
            "yyyy年MM月");

    public final static SimpleDateFormat GET_MM_CHINESE = new SimpleDateFormat(
            "MM月");

    public final static SimpleDateFormat GET_DD = new SimpleDateFormat(
            "dd");


    public static final String[] constellationArr = {"水瓶座", "双鱼座", "白羊座",
            "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

    public static final int[] constellationID = {11, 12, 1, 2, 3, 4, 5, 6, 7,
            8, 9, 10};

    public static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22,
            23, 23, 23, 23, 22, 22};


    /**
     * 将日期类型转换成String类型，yyyy-MM-dd HH:mm:ss格式
     */
    public static String dateToString(Date date) {
        String sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return sDate;
    }

    /**
     * 将String类型转换成日期类型
     */
    public static Date stringToDate(String sDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToStringImage(Date date) {
        String sDate = new SimpleDateFormat("/yyyy/MM/dd").format(date);
        return sDate;
    }

    /**
     * 将日期类型转换成String类型，yyyyMMddHHmmss格式
     */
    public static String dateToStringWithExcel(Date date) {
        String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        return sDate;
    }

    public static String getFormatDays(SimpleDateFormat df, String day)
            throws ParseException {
        if (null == day || "".equals(day))
            return null;
        String days = "2015-01-01";
        if (0 < day.indexOf("-")) {
            days = df.format(GET_YY_MM_DD.parse(day));
        } else {
            days = day.substring(4);
        }
        return days;
    }

    public static String formatDays(Date date1, Date date2, int index) {
        if (null == date1 || null == date2) return null;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        int oldMonth = calendar1.get(Calendar.MONTH);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int newMonth = calendar2.get(Calendar.MONTH);
        if (index == 1 || oldMonth != newMonth) return GET_MM_DD_CHINESE.format(date2);
        return GET_DD.format(date2);
    }

    public static Date getSubtractDays(int type, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(type, day);
        return calendar.getTime();
    }

    public static String getLastDayOfWeek(int type, int day) {
        Calendar calendar = Calendar.getInstance();
        Date subdate = getSubtractDays(type, day);
        calendar.setTime(subdate);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        return GET_YY_MM_DD.format(calendar.getTime());
    }

    public static String getSubtractDayFormat(int type, int day) {
        Calendar calendar = Calendar.getInstance();
        Date subdate = getSubtractDays(type, day);
        calendar.setTime(subdate);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int year = calendar.get(Calendar.YEAR);
        int wom = calendar.get(type);
        String str = "";
        String subtract = GET_YY_MM_DD.format(subdate);
        if (Calendar.DATE != type) {
            if (Calendar.MONTH == type) {
                wom = wom + 1;
            }
            if (wom < 10) {
                str = "0";
            }
            subtract = year + str + wom;
        }
        return subtract;
    }

    public static String getDateWMYFormat(Date date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int wom = calendar.get(type);
        String str = "";
        String subtract = GET_YY_MM_DD.format(date);
        if (Calendar.DATE != type) {
            if (Calendar.MONTH == type) {
                wom = wom + 1;
            }
            if (wom < 10) {
                str = "0";
            }
            subtract = year + str + wom;
        }
        return subtract;
    }

    /**
     * @param birthDay 生日
     * @return :int 年
     * @throws Exception
     * @description:根据出生日期获取年龄
     * @create :2012-7-2
     * @author :laoas
     */
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    // do nothing
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        } else {
            // monthNow<monthBirth
            // donothing
        }

        return age;
    }

    /**
     * 根据日期获取星座
     *
     * @param time
     * @return
     */
    public static String date2Constellation(Calendar time) {
        int month = time.get(Calendar.MONTH);
        int day = time.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        // default to return 魔羯
        return constellationArr[11];
    }

    /**
     * 根据日期获取星座ID
     *
     * @return
     */
    public static int getConstellationID(Date date) {
        if (date == null) {
            return constellationID[11];
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationID[month];
        }
        // default to return 魔羯
        return constellationID[11];
    }

    public static boolean getDateCompare(String startTime, String endTime,
                                         String currentTime) {
        try {
            Date sTime = GET_YY_MM_DD.parse(startTime);
            Date eTime = GET_YY_MM_DD.parse(endTime);
            Date cTime = GET_YY_MM_DD.parse(currentTime);
            if (cTime.getTime() >= sTime.getTime()
                    && cTime.getTime() <= eTime.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int daysBetween(String startTime, String endTime)
            throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(GET_YY_MM_DD.parse(startTime));
        long time1 = cal.getTimeInMillis();
        cal.setTime(GET_YY_MM_DD.parse(endTime));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date getDayOfWeek(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + day);
        return calendar.getTime();
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return w;
    }

    public static Date getWeekOfMonth(Date date, int bymoy, int week, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//		calendar.set(Calendar.YEAR, 2015);
//		calendar.set(Calendar.MONTH, 10);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.set(bymoy, week);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + day);
        return calendar.getTime();
    }

    public static String getLastDayOfWeek(Date date, int index) {
        String dateTxt = GET_DD.format(getDayOfWeek(date, 6));
        if (index == 1)
            dateTxt = GET_MM_DD_CHINESE.format(getDayOfWeek(date, 6));
        return dateTxt;
    }

    public static Calendar lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal;
    }

    public static int getWeeksByMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        if (Calendar.SUNDAY == lastDayOfMonth(date).get(Calendar.DAY_OF_WEEK)) {
            return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        }
        return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) - 1;
    }

    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    public static String formatWeekOfYear(Date date) {
        String sub = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        if (week <= 9) {
            sub = "0";
        }
        return calendar.get(Calendar.YEAR) + sub + week;
    }

    public static String formatMonthOfYear(Date date, int month) {
        String sub = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (month <= 9) {
            sub = "0";
        }
        return calendar.get(Calendar.YEAR) + sub + month;
    }

    public static String formatYearMonth(String str, int index) {
        String yearMonth = str.substring(4) + "月";
        if (index == 1) yearMonth = str.substring(0, 4) + "年" + str.substring(4) + "月";
        return yearMonth;
    }


    public static Date getDailyStartTime() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime();
    }

    public static Date getDailyStartTime(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime();
    }

    public static Date getDailyEndTime() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 999);
        return now.getTime();
    }

    public static Date getDailyEndTime(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 999);
        return now.getTime();
    }

    public static Date getWeeklyStartTime() {
        Calendar now = Calendar.getInstance();
        now.setFirstDayOfWeek(Calendar.MONDAY);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return now.getTime();
    }

    public static Date getWeeklyEndTime() {
        Calendar now = Calendar.getInstance();
        now.setFirstDayOfWeek(Calendar.MONDAY);
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 999);
        now.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return now.getTime();
    }

    public static Date getMonthlyStartTime() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        now.set(Calendar.DAY_OF_MONTH, 1);
        return now.getTime();
    }

    public static Date getMonthlyEndTime() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 999);
        now.set(Calendar.DATE, now.getActualMaximum(Calendar.DATE));
        return now.getTime();
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(DateUtil.GET_YY_MM_DD_HH_MM_SS.format(getMonthlyStartTime()));
        System.out.println(DateUtil.GET_YY_MM_DD_HH_MM_SS.format(getMonthlyEndTime()));
//        System.out.println(DateUtil.GET_YY_MM_DD_HH_MM_SS.format(getWeeklyStartTime()));
//        System.out.println(DateUtil.GET_YY_MM_DD_HH_MM_SS.format(getWeeklyEndTime()));
//        System.out.println(DateUtil.GET_YY_MM_DD_HH_MM_SS.format(getDailyStartTime()));
//        System.out.println(DateUtil.GET_YY_MM_DD_HH_MM_SS.format(getDailyEndTime()));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(DateUtil.getWeekOfMonth(new Date(), Calendar.WEEK_OF_MONTH, 1, 6));
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);

//		System.out.println(getDateCompare("2015-11-16 14:02:59.0",
//				"2016-05-17 14:03:17.0", "2015-11-17"));
//		System.out.println(daysBetween("2015-11-17", "2016-05-17 14:03:17.0"));
//		System.out.println(GET_YY_MM_DD.format(new Date()));
//		System.out.println(getWeekOfMonth(new Date(),Calendar.WEEK_OF_MONTH,1,6));
//		System.out.println(getWeeksByMonth(new Date()));
//		System.out.println(getDayOfWeek(new Date(),0));
//		System.out.println(lastDayOfMonth(new Date()));
//		System.out.println(GET_YY_WW.format(calendar.getTime()));
//		System.out.println(GET_YY_WW.format(DateUtil.getWeekOfMonth(new Date(),Calendar.WEEK_OF_MONTH,1,6)));
//		System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
//		System.out.println(formatMonthOfYear(new Date(),11));
//		System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
//		System.out.println(formatYearMonth("201506",2));
//        System.out.println(getSubtractDayFormat(Calendar.WEEK_OF_YEAR, 0));
    }
}
