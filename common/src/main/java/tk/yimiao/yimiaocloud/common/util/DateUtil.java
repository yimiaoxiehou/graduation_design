/**
 * @Package tk.yimiao.yimiaocloud.common.util
 * @Description: 时间工具
 * @author yimiao
 * @date 2019-03-07 14:57
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    /**
     * 毫秒
     */
    public final static long MS = 1;
    /**
     * 每秒钟的毫秒数
     */
    public final static long SECOND_MS = MS * 1000;
    /**
     * 每分钟的毫秒数
     */
    public final static long MINUTE_MS = SECOND_MS * 60;
    /**
     * 每小时的毫秒数
     */
    public final static long HOUR_MS = MINUTE_MS * 60;
    /**
     * 每天的毫秒数
     */
    public final static long DAY_MS = HOUR_MS * 24;
    /**
     * 标准日期格式
     */
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 标准时间格式
     */
    public final static String HH_MM_SS = "HH:mm:ss";
    /**
     * 标准日期时间格式（去掉秒）
     */
    public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * 标准日期时间格式
     */
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * HTTP头中日期时间格式
     */
    public final static String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    /**
     * 标准日期（不含时间）格式化器
     */
    private final static SimpleDateFormat NORM_DATE_FORMAT = new SimpleDateFormat(YYYY_MM_DD);
    /**
     * 标准时间格式化器
     */
    private final static SimpleDateFormat NORM_TIME_FORMAT = new SimpleDateFormat(HH_MM_SS);
    /**
     * 标准日期时间格式化器
     */
    private final static SimpleDateFormat NORM_DATETIME_FORMAT = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    /**
     * HTTP日期时间格式化器
     */
    private final static SimpleDateFormat HTTP_DATETIME_FORMAT = new SimpleDateFormat(HTTP_DATETIME_PATTERN, Locale.US);
    private static Logger log = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        return formatDateTime(new Date());
    }

    /**
     * 当前日期，格式 yyyy-MM-dd
     *
     * @return 当前日期的标准形式字符串
     */
    public static String today() {
        return formatDate(new Date());
    }

    // ------------------------------------ Format start ----------------------------------------------

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date 被格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDateTime(Date date) {
//		return format(d, "yyyy-MM-dd HH:mm:ss");
        return NORM_DATETIME_FORMAT.format(date);
    }

    /**
     * 格式化为Http的标准日期格式
     *
     * @param date 被格式化的日期
     * @return HTTP标准形式日期字符串
     */
    public static String formatHttpDate(Date date) {
//		return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US).format(date);
        return HTTP_DATETIME_FORMAT.format(date);
    }

    /**
     * 格式 yyyy-MM-dd
     *
     * @param date 被格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
//		return format(d, "yyyy-MM-dd");
        return NORM_DATE_FORMAT.format(date);
    }
    // ------------------------------------ Format end ----------------------------------------------

    // ------------------------------------ Parse start ----------------------------------------------

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateString 特定格式的日期
     * @param format     格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static Date parse(String dateString, String format) {
        try {
            return (new SimpleDateFormat(format)).parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format " + format + " error!", e);
        }
        return null;
    }

    /**
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static Date parseDateTime(String dateString) {
//		return parse(s, "yyyy-MM-dd HH:mm:ss");
        try {
            return NORM_DATETIME_FORMAT.parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format " + NORM_DATETIME_FORMAT.toPattern() + " error!", e);
        }
        return null;
    }

    /**
     * 格式yyyy-MM-dd
     *
     * @param dateString 标准形式的日期字符串
     * @return 日期对象
     */
    public static Date parseDate(String dateString) {
        try {
            return NORM_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format " + YYYY_MM_DD + " error!", e);
        }
        return null;
    }

    /**
     * 格式HH:mm:ss
     *
     * @param timeString 标准形式的日期字符串
     * @return 日期对象
     */
    public static Date parseTime(String timeString) {
        try {
            return NORM_TIME_FORMAT.parse(timeString);
        } catch (ParseException e) {
            log.error("Parse " + timeString + " with format " + HH_MM_SS + " error!", e);
        }
        return null;
    }

    /**
     * 格式：<br>
     * 1、yyyy-MM-dd HH:mm:ss<br>
     * 2、yyyy-MM-dd<br>
     * 3、HH:mm:ss>
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parse(String dateStr) {
        int length = dateStr.length();
        try {
            if (length == DateUtil.YYYY_MM_DD_HH_MM_SS.length()) {
                return parseDateTime(dateStr);
            } else if (length == DateUtil.YYYY_MM_DD.length()) {
                return parseDate(dateStr);
            } else if (length == DateUtil.HH_MM_SS.length()) {
                return parseTime(dateStr);
            }
        } catch (Exception e) {
            log.error("Parse " + dateStr + " with format normal error!", e);
        }
        return null;
    }
    // ------------------------------------ Parse end ----------------------------------------------

    // ------------------------------------ Offset start ----------------------------------------------

    /**
     * 昨天
     *
     * @return 昨天
     */
    public static Date yesterday() {
        return offsiteDate(new Date(), Calendar.DAY_OF_YEAR, -1);
    }

    /**
     * 上周
     *
     * @return 上周
     */
    public static Date lastWeek() {
        return offsiteDate(new Date(), Calendar.WEEK_OF_YEAR, -1);
    }

    /**
     * 上个月
     *
     * @return 上个月
     */
    public static Date lastMouth() {
        return offsiteDate(new Date(), Calendar.MONTH, -1);
    }

    /**
     * 获取指定日期偏移指定时间后的时间
     *
     * @param date          基准日期
     * @param calendarField 偏移的粒度大小（小时、天、月等）使用Calendar中的常数
     * @param offsite       偏移量，正数为向后偏移，负数为向前偏移
     * @return 偏移后的日期
     */
    public static Date offsiteDate(Date date, int calendarField, int offsite) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, offsite);
        return cal.getTime();
    }
    // ------------------------------------ Offset end ----------------------------------------------

    /**
     * 判断两个日期相差的时长<br/>
     * 返回 minuend - subtrahend 的差
     *
     * @param subtrahend 减数日期
     * @param minuend    被减数日期
     * @param diffField  相差的选项：相差的天、小时
     * @return 日期差
     */
    public static long diff(Date subtrahend, Date minuend, long diffField) {
        long diff = minuend.getTime() - subtrahend.getTime();
        return diff / diffField;
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：纳秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，纳秒
     */
    public static long spendNt(long preTime) {
        return System.nanoTime() - preTime;
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：毫秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，毫秒
     */
    public static long spendMs(long preTime) {
        return System.currentTimeMillis() - preTime;
    }

    public static Date beginOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date endOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date getDayOf(Date date, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.set(field, value);
        return calendar.getTime();

    }

    public static Date getBeginDayOfWeek() {
        return getDayOf(null, Calendar.DAY_OF_WEEK, 1);
    }

    public static Date getEndDayOfWeek() {
        return getDayOf(null, Calendar.DAY_OF_WEEK, 0);
    }

    public static Date getEndDayOfMonth() {
        return getDayOf(null, Calendar.DAY_OF_MONTH, Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH));
    }

    public static Date getBeginDayOfMonth() {
        return getDayOf(null, Calendar.DAY_OF_MONTH, 1);
    }

    public static Date getEndDayOfLastMonth() {
        return getDayOf(null, Calendar.DAY_OF_MONTH, 0);
    }

    public static Date getBeginDayOfLastMonth() {
        return getDayOf(getEndDayOfLastMonth(), Calendar.DAY_OF_MONTH, 1);
    }

    public static boolean isSameDay(Date day1, Date day2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(day1);
        calendar2.setTime(day2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

    public static Date getBeginDayOfYear(int year) {
        return getDayOf(getDayOf(null, Calendar.YEAR, year), Calendar.DAY_OF_YEAR, 0);
    }

    public static int month(Date date) {
        return getValueOf(date, Calendar.MONTH);
    }

    public static int getValueOf(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return calendar.get(field);
    }
}