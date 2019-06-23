package com.greatonce.mkwebservice.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * company:Shenzhen Greatonce Co Ltd
 * author:buer
 * create date:2017/4/20
 * remark:
 */
public abstract class DateTimeUtil {

    public static final DateTimeFormatter MS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final DateTimeFormatter S_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter M_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter NO_SPLIT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter NO_SPLIT_M_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public static final DateTimeFormatter NO_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DATE_HH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");

    public static LocalDateTime toLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String format(Date date){
        if(null == date){
            return "";
        }
        return format(date, DATETIME_FORMATTER);
    }

    public static String format(Date date, DateTimeFormatter formatter){
        if(null == date){
            return "";
        }
        return format(toLocalDateTime(date), formatter);
    }

    public static String format(LocalDate date){
        if(null == date){
            return "";
        }
        return format(date, DATE_FORMATTER);
    }

    public static String format(LocalDate date, DateTimeFormatter formatter){
        if(null == date){
            return "";
        }
        return date.format(formatter);
    }

    public static String format(LocalDateTime dateTime){
        if(null == dateTime){
            return "";
        }
        return format(dateTime, DATETIME_FORMATTER);
    }

    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter){
        if(null == dateTime){
            return "";
        }
        ZonedDateTime time = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
        return time.format(formatter);
    }

    public static String nowDate(){
        return format(LocalDate.now());
    }

    public static String nowTime(){
        return format(LocalDateTime.now());
    }

    public static Date parserDate(String text){
        return parserDate(text, DATETIME_FORMATTER);
    }

    public static Date parserDate(String text, DateTimeFormatter formatter){
        LocalDateTime time = parserLocalDateTime(text, formatter);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = time.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static LocalDate parserLocalDate(String text){
        return parserLocalDate(text, DATE_FORMATTER);
    }

    public static LocalDate parserLocalDate(String text, DateTimeFormatter formatter){
        return LocalDate.parse(text, formatter);
    }

    public static LocalDateTime parserLocalDateTime(String text){
        return parserLocalDateTime(text, DATETIME_FORMATTER);
    }

    public static LocalDateTime parserLocalDateTime(String text, DateTimeFormatter formatter){
        return LocalDateTime.parse(text, formatter);
    }

    public static LocalDateTime parserTimestamp(long timestamp){
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
    }

    public static LocalDateTime parserTimestampMilli(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
    }

    public static long toTimestamp(LocalDateTime time){
        return time.toInstant(Constants.DEFAULT_ZONE_OFF_SET).getEpochSecond();
    }

    public static long toTimestampMilli(LocalDateTime time){
        return time.toInstant(Constants.DEFAULT_ZONE_OFF_SET).toEpochMilli();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
}
