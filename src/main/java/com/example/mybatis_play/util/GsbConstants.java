package com.example.mybatis_play.util;

public interface GsbConstants {
    String BANK_CODE = "GSB";
    String MSM_REGEX = "คุณได้รับเงิน\\s+(\\S+)\\s+บาท\\s+จาก\\s+(\\S+)\\s+(\\S+)\\s+ที่";
    String MSM_REGEX_LONG = "คุณได้รับเงิน\\s+(\\S+)\\s+บาท\\s+จาก\\s+(\\S+)\\s+(\\S+)\\s+ที่\\s+(\\S+)\\s+วันที่\\s+(\\d+)\\s+(\\S+)\\s+(\\S+)\\s+เวลา\\s+(\\d{1,2}:\\d{2})\\s+น.";
}
