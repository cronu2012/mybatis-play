package com.example.mybatis_play.service;

import cn.hutool.core.bean.BeanUtil;
import com.example.mybatis_play.daomapper.BankDao;
import com.example.mybatis_play.domain.Bank;
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
public class BankXmlService {
    private static final String CLASS = BankXmlService.class.getSimpleName();

    private final BankDao bankDao;

    public BankListDto findAll(){
        log.info("{} 使用XML的findAll",CLASS);
        String log = String.format("%s 使用XML的findAll",CLASS);

        List<Bank> banks = bankDao.selectAll();

        List<BankDto> bankDtos = new ArrayList<>();

        if(!banks.isEmpty()){
            for (Bank bank : banks){
                BankDto dto = new BankDto();
                BeanUtil.copyProperties(bank, dto);
                dto.setLog(log);
                bankDtos.add(dto);
            }
        }
        BankListDto listDto = new BankListDto();
        listDto.setList(bankDtos);
        listDto.setLog(log);

        return listDto;
    }


}
