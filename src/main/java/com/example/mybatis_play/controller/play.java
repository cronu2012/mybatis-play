package com.example.mybatis_play.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import com.example.mybatis_play.domain.BankMsmHandleDTO;
import com.example.mybatis_play.util.SpiderUtil;
import lombok.extern.slf4j.Slf4j;


import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class play {

    public static void main(String... args) {
        String longMsm = "คุณได้รับเงิน 10.00 บาท จาก TMBA 0072XXXX4956 ที่ 0204XXXX9440 วันที่ 18 เม.ย. 67 เวลา 13:38 น. คงเหลือ 91.00 บาท";
        String shortMsm = "คุณได้รับเงิน 100.00 บาท จาก KBNK 0013XXXX3828 ที่";
        Assert.notNull(msgParse(longMsm));
        Assert.notNull(msgParse(shortMsm));
    }

    public static BankMsmHandleDTO msgParse(String msm) {
        try {
            Pattern pattern = Pattern.compile(GsbConstants.MSM_REGEX);
            Pattern patternLong = Pattern.compile(GsbConstants.MSM_REGEX_LONG);
            Matcher matcher = pattern.matcher(msm);
            Matcher matcherLong = patternLong.matcher(msm);
            if (!matcher.find()) {
                log.error("{}，msm解析規則不匹配，msm：{}", GsbConstants.BANK_CODE, msm);
                return null;
            }
            String amountStr = matcher.group(1).replaceAll(",", "");
            Long amount = NumberUtil.mul(amountStr, String.valueOf(1000)).longValue();
            String bankCode = matcher.group(2);
            // 取銀行帳號後四位
            String bankAccount = matcher.group(3).substring(matcher.group(3).length() - 4);
            ;
            Date payTime = matcherLong.find() ? payTime2LocalZoneDatetime(matcherLong) : new DateTime();
            BankMsmHandleDTO result = BankMsmHandleDTO.builder()
                    .bankCode(bankCode)
                    .bankAccount(bankAccount)
                    .payTime(payTime)
                    .amount(amount)
                    .build();
            log.info("{}，msm解析成功，結果：{}，msm：{}", GsbConstants.BANK_CODE, result, msm);
            return result;
        } catch (Exception e) {
            log.error("{}，msm解析异常，msm:{}", GsbConstants.BANK_CODE, msm, e);
        }
        return null;
    }

    private static Date payTime2LocalZoneDatetime(Matcher matcher) throws ParseException {
        // 年：加2500是泰國佛曆，減去543換算成西元
        int year = Integer.parseInt(matcher.group(7)) + 2500 - 543;
        // 月：泰文縮寫轉換成數字
        int month = convertMonthThaiToNum(matcher.group(6));
        // 日
        int day = Integer.parseInt(matcher.group(5));
        // 時間
        String time = matcher.group(8);
        String dateTimeStr = String.format("%04d-%02d-%02d %s", year, month, day, time);
        return SpiderUtil.getGmtPlus7ToGMTReduce4DateTime(dateTimeStr);
    }

    private static int convertMonthThaiToNum(String thaiMonth) {
        switch (thaiMonth) {
            case "ม.ค.":
                return 1;
            case "ก.พ.":
                return 2;
            case "มี.ค.":
                return 3;
            case "เม.ย.":
                return 4;
            case "พ.ค.":
                return 5;
            case "มิ.ย.":
                return 6;
            case "ก.ค.":
                return 7;
            case "ส.ค.":
                return 8;
            case "ก.ย.":
                return 9;
            case "ต.ค.":
                return 10;
            case "พ.ย.":
                return 11;
            case "ธ.ค.":
                return 12;
            default:
                return 0;
        }
    }
}
