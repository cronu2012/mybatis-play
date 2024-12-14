package com.example.mybatis_play.service;

import com.example.mybatis_play.dto.ApiResult;
import com.example.mybatis_play.dto.BankDto;

public interface BankService {

    ApiResult findAll(Integer method);

    ApiResult findById(Long id, Integer method);

    ApiResult create(BankDto bankDto);

    ApiResult update(Long id, BankDto bankDto);

    ApiResult deleteById(Long id, Integer method);

    ApiResult delete(Long id, Integer method);

    ApiResult findByCondition(int start, int end);
}
