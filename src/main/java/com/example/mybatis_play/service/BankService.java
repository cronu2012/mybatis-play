package com.example.mybatis_play.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.example.mybatis_play.daomapper.BankDao;
import com.example.mybatis_play.daomapper.BankMapper;
import com.example.mybatis_play.domain.Bank;
import com.example.mybatis_play.dto.ApiResult;
import com.example.mybatis_play.dto.BankDto;
import com.example.mybatis_play.dto.BankListDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BankService {
    private static final String CLASS = BankService.class.getSimpleName();

    private final BankDao bankDao;

    private final BankMapper bankMapper;

    public ApiResult findAll(Integer method) {
        String message = null;

        ApiResult response = new ApiResult();

        List<Bank> banks = new ArrayList<>();

        if(method == 1){
            message = String.format("%s 使用XML的selectAll", CLASS);
            banks = bankDao.selectAll();
            log.info("{} 使用XML的selectAll", CLASS);
        } else if (method == 2) {
            message = String.format("%s 使用註解的selectAll", CLASS);
            banks = bankMapper.selectAll();
            log.info("{} 使用註解的selectAll", CLASS);
        }

        List<BankDto> bankDtos = new ArrayList<>();

        if (!banks.isEmpty()) {
            for (Bank bank : banks) {
                BankDto dto = new BankDto();
                BeanUtil.copyProperties(bank, dto);
                dto.setMessage(message);
                dto.setMethod(method);
                bankDtos.add(dto);
            }

            BankListDto listDto = new BankListDto();
            listDto.setList(bankDtos);
            listDto.setMessage(message);

            response.setData(listDto);
            response.setHead("搜尋成功");
        }else {
            response.setData(bankDtos);
            response.setHead("查無資料");
        }

        return response;
    }

    public ApiResult findById(Long id,Integer method) {
        String message = null;

        ApiResult response = new ApiResult();

        Bank bank = new Bank();

        if(method == 1){
            message = String.format("%s 使用XML的selectById", CLASS);
            bank = bankDao.selectById(id);
            log.info("{} 使用XML的selectById", CLASS);
        } else if (method == 2) {
            message = String.format("%s 使用註解的selectById", CLASS);
            bank = bankMapper.selectById(id);
            log.info("{} 使用註解的selectById", CLASS);
        }
        log.info("{} 取得的Bank {}", CLASS, bank);
        BankDto dto = new BankDto();
        if(bank != null){
            BeanUtil.copyProperties(bank, dto);
            dto.setMethod(method);
            dto.setMessage(message);

            response.setData(dto);
            response.setHead("搜尋成功");
        }else {
            response.setData(new JSONObject());
            response.setHead("查無資料");
        }

        return response;
    }

    public ApiResult create(BankDto bankDto){
        String message = null;

        ApiResult response = new ApiResult();

        if(bankDao.selectByBankCode(bankDto.getBankCode())!=null ||
                bankDao.selectByBankName(bankDto.getBankName())!=null){
            response.setData("銀行編碼與銀行名稱不可重複!");
            response.setHead("創建失敗!");
            return response;
        }

        Bank bank = new Bank();
        BeanUtil.copyProperties(bankDto, bank);
        bank.setUpdateBy(bankDto.getCreateBy());

        if(bankDto.getMethod() == 1){
            message = String.format("%s 使用XML的insert", CLASS);
            bankDao.insert(bank);
            log.info("{} 使用XML的insert", CLASS);
        } else if (bankDto.getMethod() == 2) {
            message = String.format("%s 使用註解的insert", CLASS);
            bankMapper.insert(bank);
            log.info("{} 使用註解的insert", CLASS);
        }

        Bank result = bankDao.selectById(bank.getId());

        if(result != null){
            BankDto resultDto = new BankDto();
            BeanUtil.copyProperties(result, resultDto);
            resultDto.setMethod(bankDto.getMethod());
            resultDto.setMessage(message);

            response.setData(resultDto);
            response.setHead("創建成功");
        }else {
            response.setData(new JSONObject());
            response.setHead("創建失敗!");
        }

        return response;
    }



}
