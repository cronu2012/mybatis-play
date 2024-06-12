package com.example.mybatis_play.dto;

import lombok.Data;

@Data
public class BankDto {
    /**
     * 使用方法  1:xml 2:註解 3:@SqlProvider
     */
    private Integer method;

    /**
     * 創建者
     */
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 状态  1：启用，0：禁用
     */
    private Integer isEnable;

    private String log;
}
