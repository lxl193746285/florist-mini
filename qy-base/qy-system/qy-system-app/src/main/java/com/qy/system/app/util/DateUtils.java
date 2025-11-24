package com.qy.system.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
    public static final String dateFormat = "yyyy-MM-dd";
    public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String shortDateFormat = "yy-MM-dd";
    public static final String monthDateFormat = "yy-MM";
    public static final String yearMonthDateFormat = "yyyy-MM";
    public static final String shortDateTimeFormat = "yy-MM-dd HH:mm";
    public static final String timeFormat = "HH:mm:ss";
    public static final String shortTimeFormat = "HH:mm";
    public static final String localDateTimeFormat = "yyyy-MM-dd HH:mm";

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 生成单据号
     */
    public static String billNo(String key, int time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String sd = sdf.format(new Date(Long.valueOf(time + "000")));
        return key + sd;
    }

    /**
     * 通过日期时间字符串转成时间戳
     *
     * @param datetimestr
     * @param format
     * @return
     */
    public static int DatetimestrTransitionTimestamp(String datetimestr, String format) {
        if (StringUtils.isNullOfEmpty(datetimestr)) {
            return 0;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        int timestamp = 0;
        try {
            Date date = simpleDateFormat.parse(datetimestr);
            timestamp = (int) (date.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;

    }

    /**
     * 通过日期时间字符串转成时间戳
     *
     * @param Datetimestr
     * @return
     */
    public static int DatetimestrTransitionTimestamp(String Datetimestr) {
        if (null == Datetimestr) {
            return 0;
        }
        String format = "yyyy-MM-dd HH:mm:ss";
        return DatetimestrTransitionTimestamp(Datetimestr, format);
    }

    /**
     * 通过日期时间字符串转成时间戳
     *
     * @param Datetimestr
     * @return
     */
    public static int DatetimestrTransitionTimestampOnlyMinute(String Datetimestr) {
        String format = "yyyy-MM-dd HH:mm";
        return DatetimestrTransitionTimestamp(Datetimestr, format);
    }

    /**
     * 通过日期字符串转成时间戳
     *
     * @param Datestr
     * @param format
     * @return
     */
    public static int DatestrTransitionTimestamp(String Datestr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        int timestamp = 0;
        try {
            Date date = simpleDateFormat.parse(Datestr);
            timestamp = (int) (date.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;

    }

    /**
     * 通过日期字符串转成时间戳
     *
     * @param Datestr
     * @return
     */
    public static int DatestrTransitionTimestamp(String Datestr) {
        String format = "yyyy-MM-dd HH:mm:ss";
        return DatestrTransitionTimestamp(Datestr, format);
    }

    /**
     * 通过日期字符串转成时间戳（只有月份）
     *
     * @param Datestr
     * @return
     */
    public static int DatestrTransitionTimestampMonth(String Datestr) {
        String format = "yyyy-MM";
        return DatestrTransitionTimestamp(Datestr, format);
    }

    /**
     * 通过日期字符串转成时间戳（只有年）
     *
     * @param Datestr
     * @return
     */
    public static int DatestrTransitionTimestampYear(String Datestr) {
        String format = "yyyy";
        return DatestrTransitionTimestamp(Datestr, format);
    }

    /**
     * 返回当前系统时间戳
     *
     * @return
     * @author ran
     */
    public static int timeStamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 返回当前系统时间戳
     *
     * @param input
     * @return
     */
    public static int timeStamp(LocalDateTime input) {
        return Integer.valueOf((int) input.toEpochSecond(ZoneOffset.of("+8")));
    }

    /**
     * 根据传入的起始日期字符串 返回起始时间戳
     *
     * @param startTime
     * @return
     */
    public static Integer timeStampStartTime(String startTime) {
        if (startTime == null || startTime.isEmpty())
            return null;
        if (startTime.length() < 11) {
            return DatetimestrTransitionTimestamp(startTime + " 00:00:00");
        } else {
            return DatetimestrTransitionTimestamp(startTime);
        }
    }


    /**
     * 根据传入的起始日期字符串 返回起始时间戳
     *
     * @param startTime
     * @return
     */
    public static Integer dateTotimeStampStartTime(Date startTime) {
        if (startTime == null)
            return null;
        String time = DateToStr(startTime);
        if (time.length() < 11) {
            return DatetimestrTransitionTimestamp(time + " 00:00:00");
        } else {
            return DatetimestrTransitionTimestamp(time);
        }
    }


    /**
     * 根据传入的截止日期字符串 返回截止时间戳
     *
     * @param endTime
     * @return
     */
    public static Integer timeStampEndTime(String endTime) {
        if (endTime == null || endTime.isEmpty())
            return null;
        if (endTime.length() < 11) {
            return DatetimestrTransitionTimestamp(endTime + " 23:59:59");
        } else {
            return DatetimestrTransitionTimestamp(endTime);
        }
    }

    /**
     * 根据传入的截止日期字符串 返回截止时间戳
     *
     * @param endTime
     * @return
     */
    public static Integer dateToTimeStampEndTime(Date endTime) {
        if (endTime == null)
            return null;
        String time = DateToStr(endTime);
        if (time.length() < 11) {
            return DatetimestrTransitionTimestamp(time + " 23:59:59");
        } else {
            return DatetimestrTransitionTimestamp(time);
        }
    }



    /**
     * 当前日期字符串
     *
     * @return
     */
    public static String nowDatestr() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(now);
    }

    /**
     * 当前时间字符串
     *
     * @return
     */
    public static String nowTimestr() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now);
    }

    /**
     * 当前时间字符串
     *
     * @return
     */
    public static String nowTime() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(now);
    }

    /**
     * 通过时间戳转成日期字符串
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDatestr(String input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return format.format(new Date(Long.valueOf(input)));
    }

    /**
     * 通过时间戳转成日期时间字符串
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDatestr(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }

    /**
     * 通过时间戳转成日期时间字符串(yyyy-MM-dd HH:mm:ss)
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDatestr2(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }


    /**
     * 通过时间戳转成日期时间字符串(yy-MM-dd HH:mm)
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDatestrWithOutYear(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }

    /**
     * 通过时间戳转成日期字符串（yyyy-MM-dd）
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDateStr(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }

    /**
     * 通过时间戳转成日期时分秒字符串（yyyy-MM-dd）
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDateTimeStr(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) *1000));
    }
    /**
     * 通过时间戳转成日期字符串（yy-MM-dd）
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDateStrYear(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }

    /**
     * 通过时间戳转成日期字符串(yyyy)
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDateStrOnlyYear(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }


    /**
     * 通过时间戳转成日期字符串(月份)
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDateStrMonth(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }

    /**
     * 通过时间戳转成日期字符串(月份)
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDateStrMonthWithOutYear(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yy-MM");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }

    /**
     * 通过时间戳转成日期字符串
     *
     * @param Datestr
     * @return
     */
    public static String TimestampTransitionDatestrOtherFormat(String Datestr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return format.format(new Date(Long.valueOf(Datestr)));

    }

    /**
     * 通过时间戳转成日期字符串
     *
     * @param Datestr
     * @return
     */
    public static String TimestampTransitionDatestrOtherFormatMonth(String Datestr) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
        return format.format(new Date(Long.valueOf(Datestr)));
    }

    /**
     * 获取当前时间的字符串
     *
     * @return
     */
    public static String Datestr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /**
     * 转换时间间隔秒数为可读格式
     */
    public static String intervalToReadable(int interval) {
        String readable = "";
        int oneDaySeconds = 3600 * 24;
        double day = Math.floor(interval / oneDaySeconds);
        readable += day > 0 ? day + "天" : "";
        double hour = Math.floor(interval % oneDaySeconds / 3600);
        readable += hour > 0 ? hour + "小时" : "";
        double minute = Math.floor(interval % oneDaySeconds % 3600 / 60);
        readable += minute > 0 ? minute + "分" : "";
        return readable;
    }

    /**
     * 转换当前时间格式为yyyyMMdd
     *
     * @return
     */
    public static String nowDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    /**
     * 转换当前时间格式为yyyyMM
     *
     * @return
     */
    public static String nowDateFormatToDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(new Date());
    }

    /**
     * 通过时间戳转成日期时间字符串(yyyy-MM-dd HH:mm)
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDatestrToMin(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }

    /**
     * 通过时间戳转成日期时间字符串(HH:mm)
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDatestrToHourAndMin(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }

    /**
     * 通过时间戳转成日期时间字符串(MM-dd HH:mm)
     *
     * @param input
     * @return
     */
    public static String TimestampTransitionDatestrToMonthHourAndMin(Integer input) {
        if (input == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

        return input == 0 ? "" : format.format(new Date(Long.valueOf(input) * 1000));
    }

    /**
     * Date转yyyy-MM-dd字符串
     *
     * @return
     */
    public static String DateToStr(Date date) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * Date转字符串
     *
     * @return
     */
    public static String DateToStr(Date date, String dateFormat) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    /**
     * Date转yyyy-MM字符串
     *
     * @return
     */
    public static String DateToStrYM(Date date) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(date);
    }

    /**
     * yyyy-MM-dd字符串转Date
     *
     * @return
     */
    public static Date StrToDate(String dateStr) {
        if (null == dateStr) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间戳转date
     *
     * @param time
     * @return
     */
    public static Date timeStampToDate(Long time) {
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(time);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳转date
     *
     * @param time
     * @return
     */
    public static Date timeStampToDate(Integer time,String sf) {
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat(sf);
        String d = format.format(time);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳转为指定格式的日期字符串
     *
     * @param time
     * @return
     */
    public static String timeStampToDateFormat(Integer time, String format) {
        if (NumberUtils.isNullOfEmpty(time))
            return "";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(Long.valueOf(time) * 1000));
    }

    /**
     * 日期时间字符串转时间戳
     * 例:20190101235959
     *
     * @param input
     * @return
     */
    public static Integer timeStrToTimestamp(String input) {
        if (ObjectUtils.isNullOfEmpty(input))
            return null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime ldt = LocalDateTime.parse(input, dtf);

        return timeStamp(ldt);
    }

    /**
     * 获取这周第一天
     *
     * @param date
     * @return
     */
    public static LocalDate getFirstOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue())));
    }

    /**
     * 获取这周第一天
     *
     * @return
     */
    public static LocalDate getFirstOfWeek() {
        return getFirstOfWeek(LocalDate.now());
    }

    /**
     * 获取这周最后一天
     *
     * @param date
     * @return
     */
    public static LocalDate getLastOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue())));
    }

    /**
     * 获取这周最后一天
     *
     * @return
     */
    public static LocalDate getLastOfWeek() {
        return getLastOfWeek(LocalDate.now());
    }

    /**
     * 获取这周第一天时间戳
     *
     * @param time
     * @return
     */
    public static Integer getFirstOfWeekTimeStamp(LocalDateTime time) {
        LocalDate newDate = getFirstOfWeek(time.toLocalDate());

        return timeStamp(LocalDateTime.of(newDate.getYear(), newDate.getMonth(), newDate.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond()));
    }

    /**
     * 获取这周最后一天时间戳
     *
     * @param time
     * @return
     */
    public static Integer getLastOfWeekTimeStamp(LocalDateTime time) {
        LocalDate newDate = getLastOfWeek(time.toLocalDate());

        return timeStamp(LocalDateTime.of(newDate.getYear(), newDate.getMonth(), newDate.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond()));
    }

    /**
     * 获取这个月的第一天
     *
     * @param date
     * @return
     */
    public static LocalDate getFirstOfMonth(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonth(), 1);
    }

    /**
     * 获取这个月的最后一天
     *
     * @param date
     * @return
     */
    public static LocalDate getLastOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取这个月第一天时间戳
     *
     * @param time
     * @return
     */
    public static Integer getFirstOfMonthTimeStamp(LocalDateTime time) {
        LocalDate newDate = getFirstOfMonth(time.toLocalDate());
        return timeStamp(LocalDateTime.of(newDate.getYear(), newDate.getMonth(), newDate.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond()));
    }

    /**
     * 获取这个月的最后一天时间戳
     *
     * @param time
     * @return
     */
    public static Integer getLastOfMonthTimeStamp(LocalDateTime time) {
        LocalDate newDate = getLastOfMonth(time.toLocalDate());
        return timeStamp(LocalDateTime.of(newDate.getYear(), newDate.getMonth(), newDate.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond()));
    }

    /**
     * LocalDate转换为Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate转换为StrDate
     *
     * @param localDate
     * @return
     */
    public static String localDateToStrDate(LocalDate localDate, String formatter) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(formatter);
        return formatters.format(localDate);
    }


    /**
     * StrDate转换为LocalDate
     *
     * @param strDate
     * @param formatter
     * @return
     */
    public static LocalDate strDateToLocalDate(String strDate, String formatter) {
        return LocalDate.parse(strDate, DateTimeFormatter.ofPattern(formatter));
    }


    /**
     * LocalDate转为时间戳
     *
     * @param localDate
     * @return
     */
    public static int localDateToTimeStamp(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        return (int) (localDate.atStartOfDay(zoneId).toInstant().toEpochMilli() / 1000);
    }

    /**
     * LocalDateTime转为时间戳
     *
     * @param localDateTime
     * @return
     */
    public static int localDateTimeToTimeStamp(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        return (int) (localDateTime.atZone(zoneId).toInstant().toEpochMilli() / 1000);
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime转换为字符串
     *
     * @param localDateTime
     * @return
     */
    public static String localDateTimeToDateStr(LocalDateTime localDateTime, String dateFormat) {
        if(localDateTime == null ){
            return "";
        }
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return DateToStr(date, dateFormat);
    }

    /**
     * LocalDateTime转换为字符串
     *
     * @param localDateTime
     * @return
     */
    public static String localDateTimeToDateStr(LocalDateTime localDateTime) {
        return localDateTimeToDateStr(localDateTime,localDateTimeFormat);
    }

    /**
     * 字符串转换为LocalDateTime
     *
     * @param timeStr
     * @param dateFormat
     * @return
     */
    public static LocalDateTime dateStrToLocalDateTime(String timeStr, String dateFormat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDateTime.parse(timeStr, df);
    }


    /**
     * Date转换为LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    /**
     * 当前时间加一天
     *
     * @return
     */
    public static int nextDayTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        Date date = calendar.getTime();
        return (int) date.getTime();
    }

    /**
     * 当前时间加多少分钟
     *
     * @param minute
     * @return
     */
    public static int nextMinTimeStamp(Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);//+1今天的时间加一天
        Date date = calendar.getTime();
        return (int) date.getTime();
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        if (StringUtils.isNullOfEmpty(datetime)) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /**
     * 秒转小时分钟
     *
     * @param second
     * @return
     */
    public static String secondToHourAndMinute(String second) {
        //秒数
        Integer dt = Integer.parseInt(second);
        if (dt < 3600) return Math.round(dt / 60) + "分钟";
        int hour = Math.round(dt / 3600);
        int minute = Math.round((dt - (hour * 3600)) / 60);
        return hour + "小时" + (minute == 0 ? "" : minute + "分钟");
    }


    /**
     * 分钟转小时分钟
     *
     * @param minutes
     * @return
     */
    public static String minuteToHourAndMinute(String minutes) {
        //分钟
        Integer dt = Integer.parseInt(minutes);
        if (dt < 60) return dt + "分钟";
        int hour = Math.round(dt / 60);
        int minute = Math.round(dt - (hour * 60));
        return hour + "小时" + (minute == 0 ? "" : minute + "分钟");
    }


    /**
     * 比较两个时间的大小
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (StringUtils.isNullOfEmpty(DATE1) || StringUtils.isNullOfEmpty(DATE2)) {
                return -1;
            }
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else if (dt1.getTime() == dt2.getTime()) {
                return 0;
            } else {
                return -1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    /**
     * 字符串转LocalDateTime
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static LocalDateTime strToLocalDateTime(String dateStr, String format) {
        if (StringUtils.isNullOfEmpty(dateStr)) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        LocalDateTime ldt = LocalDateTime.parse(dateStr, df);
        return ldt;
    }

    /**
     * 时间戳转换为localDatetime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime timeStampToLocalDateTime(Integer timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
    }

    /**
     * 获取可读的时间
     *
     * @param second
     * @return
     */
    public static String getReadableTime(Integer second) {
        return getReadableTime(second, "DDHHmmss");
    }

    /**
     * 获取可读的时间
     *
     * @param second 总的秒数
     * @param format 格式，支持HH：小时、mm：分钟、ss：秒
     * @return
     */
    public static String getReadableTime(Integer second, String format) {
        int oneMinSecond = 60;
        int oneHourSecond = 60 * 60;
        int oneDaySecond = 24 * 60 * 60;
        String secondFormat = "";
        String minuteFormat = "";
        String hourFormat = "";
        String dayFormat = "";

        if (second < oneMinSecond) {
            secondFormat = String.format("%s秒", second);
        } else if (second < oneHourSecond) {
            Integer min = second / oneMinSecond;
            Integer remainSecond = second - (min * oneMinSecond);
            minuteFormat = min + "分钟";
            secondFormat = remainSecond != 0 ? remainSecond + "秒" : "";
        } else if (second < oneDaySecond) {
            Integer hour = second / oneHourSecond;
            Integer min = (second - hour * oneHourSecond) / oneMinSecond;
            Integer remainSecond = (second - hour * oneHourSecond) - min * oneMinSecond;

            secondFormat = remainSecond != 0 ? remainSecond + "秒" : "";
            minuteFormat = min != 0 ? min + "分钟" : "";
            hourFormat = hour + "小时";
        } else {
            Integer day = second / oneDaySecond;
            Integer hour = (second - day * oneDaySecond) / oneHourSecond;
            Integer min = (second - day * oneDaySecond - hour * oneHourSecond) / oneMinSecond;
            Integer remainSecond = (second - day * oneDaySecond - hour * oneHourSecond) - min * oneMinSecond;

            secondFormat = remainSecond != 0 ? remainSecond + "秒" : "";
            minuteFormat = min != 0 ? min + "分钟" : "";
            hourFormat = hour != 0 ? hour + "小时" : "";
            dayFormat = day + "天";
        }
        return format.replace("DD", dayFormat).replace("HH", hourFormat).replace("mm", minuteFormat).replace("ss", secondFormat);
    }

    /**
     * 时间戳转LocalDate
     *
     * @param timestamp
     * @return
     */
    public static LocalDate timeStampToLocalDate(Integer timestamp) {
        if (timestamp == null) {
            return null;
        }
        return dateToLocalDate(timeStampToDate((long)timestamp*1000));
        //return Instant.ofEpochMilli(timestamp*1000).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }


    /**
     * 获取一段时间内的周日的数量
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<LocalDate> getWeeks(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> weekDates = new ArrayList<>();
        Integer weekday = startDate.getDayOfWeek().getValue();
        if(weekday == 7){
            for(int i = 0;i<100;i++){
                LocalDate nextWeek = startDate.plusWeeks(i);
                if(nextWeek.isAfter(endDate)){
                    break;
                }
                weekDates.add(nextWeek);
            }
        }else{
            DayOfWeek dayOfWeek = DayOfWeek.of(7);
            LocalDate week = startDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
            for(int i = 0;i<100;i++){
                LocalDate nextWeek = week.plusWeeks(i);
                if(nextWeek.isAfter(endDate)){
                    break;
                }
                weekDates.add(nextWeek);
            }
        }
        return weekDates;
    }

    /**
     * 获取当前月份
     * @return
     */
    public static String getMonthDateFormat(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(yearMonthDateFormat);
        String dateStr = date.format(fmt);
        return dateStr;
    }
    /**
     * 获取前一天日期
     * @return
     */
    public static String getYesterdayDateFormat(){
        LocalDate date = LocalDate.now().minusDays(1);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
        String dateStr = date.format(fmt);
        return dateStr;
    }
    /**
     * 获取上个月份
     * @return
     */
    public static String getLastMonthDateFormat(){
        LocalDate date = LocalDate.now().minusMonths(1);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(yearMonthDateFormat);
        String dateStr = date.format(fmt);
        return dateStr;
    }
    /**
     * 获取本周第一天
     * @return
     */
    public static String getFirstDayOfWeek(){
        LocalDate date = getFirstOfWeek();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
        String dateStr = date.format(fmt);
        return dateStr;
    }
    /**
     * 获取本周最后一天
     * @return
     */
    public static String getLastDayOfWeek(){
        LocalDate date = getLastOfWeek();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
        String dateStr = date.format(fmt);
        return dateStr;
    }
    /**
     * 获取上周第一天
     * @return
     */
    public static String getFirstDayOfLastWeek(){
        LocalDate date = getFirstOfWeek(LocalDate.now().minusWeeks(1));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
        String dateStr = date.format(fmt);
        return dateStr;
    }
    /**
     * 获取上周最后一天
     * @return
     */
    public static String getLastDayOfLastWeek(){
        LocalDate date = getLastOfWeek(LocalDate.now().minusWeeks(1));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
        String dateStr = date.format(fmt);
        return dateStr;
    }

    /**
     * 字符串转换为当天开始localDatetime
     *
     * @param timeStr
     * @return
     */
    public static LocalDateTime timeStampToStartLocalDateTime(String timeStr) {
        return LocalDateTime.ofEpochSecond(DateUtils.timeStampStartTime(timeStr), 0, ZoneOffset.ofHours(8));
    }

    /**
     * 字符串转换为当天结束localDatetime
     *
     * @param timeStr
     * @return
     */
    public static LocalDateTime timeStampToEndLocalDateTime(String timeStr) {
        return LocalDateTime.ofEpochSecond(DateUtils.timeStampEndTime(timeStr), 0, ZoneOffset.ofHours(8));
    }
}
