package com.lpz.graph.gateway.common.util;


import com.lpz.graph.gateway.common.constant.DatePattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 */
@Slf4j
public class DateUtil implements DatePattern {

    public static final String FORMATSTR_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FORMATSTR_YYYY_MM_DD_HHMMSS_S = "yyyy-MM-dd HH:mm:ss.S";
    public static final String FORMATSTR_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATSTR_YYYY_MM_DD_HHMM = "yyyy-MM-dd HH:mm";
    public static final String FORMATSTR_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String FORMATSTR_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMATSTR_YYYY = "yyyy";
    public static final String FORMATSTR_YYYYMM = "yyyyMM";
    public static final String FORMATSTR_YYYYMMDD = "yyyyMMdd";
    public static final String FORMATSTR_YYYYMMDDDELIMITER = "-";


    /**
     * 格式化Date时间
     *
     * @param time       Date类型时间
     * @param timeFromat String类型格式
     * @return 格式化后的字符串
     */
    public static String formatDateToStr(Date time, String timeFromat) {
        if (time == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat(timeFromat);
        return dateFormat.format(time);
    }

    /**
     * 格式化Timestamp时间
     *
     * @param timestamp  Timestamp类型时间
     * @param timeFromat
     * @return 格式化后的字符串
     */
    public static String formatTimestampToStr(Timestamp timestamp, String timeFromat) {
        SimpleDateFormat df = new SimpleDateFormat(timeFromat);
        return df.format(timestamp);
    }

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param timeFromat   String类型格式
     * @param defaultValue 默认值为当前时间Date
     * @return 格式化后的字符串
     */
    public static String formatDateToStr(Date time, String timeFromat, final Date defaultValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            return dateFormat.format(time);
        } catch (Exception e) {
            if (defaultValue != null) {
                return formatDateToStr(defaultValue, timeFromat);
            } else {
                return formatDateToStr(new Date(), timeFromat);
            }
        }
    }

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param timeFromat   String类型格式
     * @param defaultValue 默认时间值String类型
     * @return 格式化后的字符串
     */
    public static String formatDateToStr(Date time, String timeFromat, final String defaultValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            return dateFormat.format(time);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 格式化String时间
     *
     * @param time       String类型时间
     * @param timeFromat String类型格式
     * @return 格式化后的Date日期
     */
    public static Date parseStrToDate(String time, String timeFromat) {
        if (StringUtils.isBlank(time)) {
            return null;
        }

        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            date = dateFormat.parse(time);
        } catch (Exception e) {
            log.error("parseStrToDate::time = [{}], timeFromat = [{}],e=[{}]", time, timeFromat, e.getMessage());
        }
        return date;
    }

    /**
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 格式化String时间
     *
     * @param strTime      String类型时间
     * @param timeFromat   String类型格式
     * @param defaultValue 异常时返回的默认值
     * @return
     */
    public static Date parseStrToDate(String strTime, String timeFromat,
                                      Date defaultValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            return dateFormat.parse(strTime);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getTimeString(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);

        return dateTimeFormatter.format(localDateTime);
    }

    public static LocalDate getLocalDate(String dateTime, String format) {
        if (StringUtils.isBlank(dateTime) || StringUtils.isBlank(format)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);

        return LocalDate.parse(dateTime, dateTimeFormatter);
    }

    public static String getYYYYMM(LocalDate localDate) {

        if (localDate == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMATSTR_YYYYMM);
        return localDate.format(dateTimeFormatter);
    }

    public static String getFormatString(LocalDate localDate, String format) {

        if (localDate == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 当前的年月
     *
     * @return
     */
    public static String getCurrentYYYYMM() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMATSTR_YYYYMM);
        return LocalDate.now().format(dateTimeFormatter);
    }

    /**
     * 当前时间上一个月
     *
     * @return
     */
    public static String getLastYYYYMM() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMATSTR_YYYYMM);
        return LocalDate.now().minusMonths(1).format(dateTimeFormatter);
    }

    /**
     * 获取昨天
     *
     * @return
     */
    public static String getYesterday() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMATSTR_YYYYMMDD);
        return LocalDate.now().minusDays(1).format(dateTimeFormatter);
    }

    /**
     * 获取limit天之前的某个日期，yyyyMMdd
     *
     * @param current
     * @param limit
     * @return
     */
    public static String getLastYYYYMMDD(LocalDate current, Integer limit) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMATSTR_YYYYMMDD);
        return current.minusDays(limit).format(dateTimeFormatter);
    }

    /**
     * 取某个时间的之前的某一个月
     *
     * @return
     */
    public static String getLastYYYYMM(LocalDate current, Integer limit) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMATSTR_YYYYMM);
        return current.minusMonths(limit).format(dateTimeFormatter);
    }

    /**
     * 当前时间下一个月
     *
     * @return
     */
    public static LocalDate getNextYYYYMM(LocalDate current) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMATSTR_YYYYMM);
        return current.plusMonths(1);
    }

    /**
     * 获取某个时间及之前的数月
     *
     * @param current 查询时间
     * @param limit   过去月数
     * @return
     */
    public static List<String> getMonthBeforeList(LocalDate current, Integer limit) {
        return Stream
                .iterate(current, x -> x.minusMonths(1L))
                .limit(limit)
                .map(localDate -> DateUtil.getYYYYMM(localDate))
                .collect(Collectors.toList());
    }

    /**
     * 获取from -> to之间的月
     *
     * @param from 查询时间
     * @param to   过去月数
     * @return
     */
    public static List<String> getMonthFromToList(LocalDate from, LocalDate to) {
        return Stream
                .iterate(to, x -> x.minusMonths(1L))
                .limit(13)
                .filter(e -> e.isAfter(from) || e.isEqual(from))
                .map(localDate -> DateUtil.getYYYYMM(localDate))
                .collect(Collectors.toList());
    }

    /**
     * 获取from -> to之间的月
     *
     * @param from 查询时间
     * @param to   过去月数
     * @return
     */
    public static List<String> getDaysFromToList(LocalDate from, LocalDate to) {
        return Stream
                .iterate(to, x -> x.minusDays(1L))
                .limit(13)
                .filter(e -> e.isAfter(from) || e.isEqual(from))
                .map(localDate -> DateUtil.getYYYYMM(localDate))
                .collect(Collectors.toList());
    }

    /**
     * 获取前一个月1号
     */
    public static String getLastMonthFirst() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal1 = Calendar.getInstance();//获取当前日期
        cal1.add(Calendar.MONTH, -1);
        cal1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String lastFirstDay = format.format(cal1.getTime());
        return lastFirstDay;
    }


    /**
     * 获取当月1号
     */
    public static String getMonthFirst() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal1 = Calendar.getInstance();//获取当前日期
        cal1.add(Calendar.MONTH, 0);
        cal1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal1.getTime());
        return firstDay;
    }

    public static String getBeforeLastFirst() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal1 = Calendar.getInstance();//获取当前日期
        cal1.add(Calendar.MONTH, -2);
        cal1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal1.getTime());
        return firstDay;
    }

    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    public static void main(String[] args) {

        String month = "201908";
        LocalDate queryMonthDate = getLocalDate(month + "01", DateUtil.FORMATSTR_YYYYMMDD);
        String fromMonth = getLastYYYYMM(queryMonthDate, 5);
        System.out.println(fromMonth); // 201903

    }

}
