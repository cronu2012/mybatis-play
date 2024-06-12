package com.example.mybatis_play.dto;

import lombok.Data;

@Data
public class BankDto {

    private static final long serialVersionUID = 1L;

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


}
