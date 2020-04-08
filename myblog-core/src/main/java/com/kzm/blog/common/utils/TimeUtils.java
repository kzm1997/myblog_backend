package com.kzm.blog.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:50 2020/2/27
 * @Version
 */
public class TimeUtils  {
    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * localDateTime 转String
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String LocalDateTimeToString(LocalDateTime localDateTime,String pattern){
        DateTimeFormatter df=DateTimeFormatter.ofPattern(pattern);
        return   df.format(localDateTime);
    }

    /**
     * localDateTime 转String
     * @param localDateTime
     * @return
     */
    public static String LocalDateTimeToString(LocalDateTime localDateTime){
        return TimeUtils.LocalDateTimeToString(localDateTime,FULL_TIME_PATTERN);
    }

    public static String YearMonthToString(YearMonth localDate, String pattern){
      DateTimeFormatter df=DateTimeFormatter.ofPattern(pattern);
      return df.format(localDate);
    }
}
