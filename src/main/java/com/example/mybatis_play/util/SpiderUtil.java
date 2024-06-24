package com.example.mybatis_play.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SpiderUtil {
    public static String monthEnTODigitalStr(String monthStr) {
        if (monthStr.equals("Jan")) {
            return "01";
        }
        if (monthStr.equals("Feb")) {
            return "02";
        }

        if (monthStr.equals("Mar")) {
            return "03";
        }

        if (monthStr.equals("Apr")) {
            return "04";
        }

        if (monthStr.equals("May")) {
            return "05";
        }

        if (monthStr.equals("Jun")) {
            return "06";
        }

        if (monthStr.equals("Jul")) {
            return "07";
        }

        if (monthStr.equals("Aug")) {
            return "08";
        }

        if (monthStr.equals("Sep")) {
            return "09";
        }

        if (monthStr.equals("Oct")) {
            return "10";
        }

        if (monthStr.equals("Nov")) {
            return "11";
        }

        if (monthStr.equals("Dec")) {
            return "12";
        }
        return "";
    }

    public static void sleepSecond(long timeout){
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void sleepMilliseconds(long timeout){
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static DateTime getLocalZone2GMT7DateTime(Date date) {
        ZoneId zoneId = ZoneId.of("GMT+7");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(zoneId));
        return DateUtil.parse(simpleDateFormat.format(date));
    }

    public static DateTime getGmtPlus7ToGMTReduce4DateTime(String dateTimeStr){
        // todo 新增一个gmt7转本地时间的方法
        return DateUtil.offsetHour(DateUtil.parse(dateTimeStr), -11);
    }
}
