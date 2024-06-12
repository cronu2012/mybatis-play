package com.example.mybatis_play.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankListDto {
    private String log;

    private List<BankDto> list;
}
