package com.kzm.blog.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:50 2020/2/27
 * @Version
 */
public class TimeUtils {
    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

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
}
