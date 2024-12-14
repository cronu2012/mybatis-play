package com.example.mybatis_play.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONObject;
import com.example.mybatis_play.daomapper.BankAnnoMapper;
import com.example.mybatis_play.daomapper.BankBaseMapper;
import com.example.mybatis_play.daomapper.BankProviderMapper;
import com.example.mybatis_play.daomapper.BankXmlMapper;
import com.example.mybatis_play.domain.Bank;
import com.example.mybatis_play.dto.ApiResult;
import com.example.mybatis_play.dto.BankDto;
import com.example.mybatis_play.dto.BankListDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService{
    private static final String CLASS = BankServiceImpl.class.getSimpleName();

    private final BankXmlMapper bankXmlMapper;

    private final BankAnnoMapper bankAnnoMapper;

    private final BankProviderMapper bankProviderMapper;

    private final BankBaseMapper bankBaseMapper;

    @Override
    public ApiResult findAll(Integer method) {
        String message = null;

        ApiResult response = new ApiResult();

        List<Bank> banks = new ArrayList<>();

        if (method == 1) {
            message = String.format("%s 使用XML的selectAll", CLASS);
            banks = bankXmlMapper.selectAll();
            log.info("{} 使用XML的selectAll", CLASS);
        } else if (method == 2) {
            message = String.format("%s 使用註解的selectAll", CLASS);
            banks = bankAnnoMapper.selectAll();
            log.info("{} 使用註解的selectAll", CLASS);
        } else if (method == 3) {
            message = String.format("%s 使用Provider的selectAll", CLASS);
            banks = bankProviderMapper.selectAll();
            log.info("{} 使用Provider的selectAll", CLASS);
        } else if (method == 4) {
            message = String.format("%s 使用BaseMapper的selectAll", CLASS);
            banks = bankBaseMapper.selectAll();
            log.info("{} 使用BaseMapper的selectAll", CLASS);
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
        } else {
            response.setData(bankDtos);
            response.setHead("查無資料");
        }

        return response;
    }

    @Override
    public ApiResult findById(Long id, Integer method) {
        String message = null;

        ApiResult response = new ApiResult();

        Bank bank = new Bank();

        if (method == 1) {
            message = String.format("%s 使用XML的selectById", CLASS);
            bank = bankXmlMapper.selectById(id);
            log.info("{} 使用XML的selectById", CLASS);
        } else if (method == 2) {
            message = String.format("%s 使用註解的selectById", CLASS);
            bank = bankAnnoMapper.selectById(id);
            log.info("{} 使用註解的selectById", CLASS);
        } else if (method == 3) {
            message = String.format("%s 使用Provider的selectById", CLASS);
            bank = bankProviderMapper.selectById(id);
            log.info("{} 使用Provider的selectById", CLASS);
        } else if (method == 4) {
            message = String.format("%s 使用BaseMapper的selectById", CLASS);
            bank = bankBaseMapper.selectById(id);
            log.info("{} 使用BaseMapper的selectById", CLASS);
        }
        log.info("{} 取得的Bank {}", CLASS, bank);

        BankDto dto = new BankDto();
        if (bank != null) {
            BeanUtil.copyProperties(bank, dto);
            dto.setMethod(method);
            dto.setMessage(message);

            response.setData(dto);
            response.setHead("搜尋成功");
        } else {
            response.setData(message);
            response.setHead("查無資料");
        }

        return response;
    }

    @Override
    public ApiResult create(BankDto bankDto) {
        String message = null;

        ApiResult response = new ApiResult();

        if (bankXmlMapper.selectByBankCode(bankDto.getBankCode()) != null ||
                bankXmlMapper.selectByBankName(bankDto.getBankName()) != null) {
            response.setData("銀行編碼與銀行名稱不可重複!");
            response.setHead("創建失敗!");
            return response;
        }

        Bank bank = new Bank();
        BeanUtil.copyProperties(bankDto, bank);
        bank.setUpdateBy(bankDto.getCreateBy());

        if (bankDto.getMethod() == 1) {
            message = String.format("%s 使用XML的insert", CLASS);
            bankXmlMapper.insert(bank);
            log.info("{} 使用XML的insert", CLASS);
        } else if (bankDto.getMethod() == 2) {
            message = String.format("%s 使用註解的insert", CLASS);
            bankAnnoMapper.insert(bank);
            log.info("{} 使用註解的insert", CLASS);
        } else if (bankDto.getMethod() == 3) {
            message = String.format("%s 使用Provider的insert", CLASS);
            bankProviderMapper.insert(bank);
            log.info("{} 使用Provider的insert", CLASS);
        } else if (bankDto.getMethod() == 4) {
            message = String.format("%s 使用BaseMapper的insert", CLASS);
            bankBaseMapper.insert(bank);
            log.info("{} 使用BaseMapper的insert", CLASS);
        }

        if (bank.getId() != null) {
            Bank result = bankProviderMapper.selectById(bank.getId());
            BankDto resultDto = new BankDto();
            BeanUtil.copyProperties(result, resultDto);
            resultDto.setMethod(bankDto.getMethod());
            resultDto.setMessage(message);

            response.setData(resultDto);
            response.setHead("創建成功");
        } else {
            response.setData(new JSONObject());
            response.setHead("創建失敗");
        }

        return response;
    }

    @Override
    public ApiResult update(Long id, BankDto bankDto) {
        String message = null;

        ApiResult response = new ApiResult();

        Bank oldBank = bankXmlMapper.selectById(id);
        if (oldBank == null) {
            response.setData("無此id的銀行!");
            response.setHead("更新失敗");
            return response;
        }

        Bank bankByCode = bankXmlMapper.selectByBankCode(bankDto.getBankCode());
        Bank bankByName = bankXmlMapper.selectByBankName(bankDto.getBankName());

        if(bankByCode != null && !Objects.equals(id, bankByCode.getId())){
            response.setData("新的銀行編碼不可與其他銀行重複!");
            response.setHead("更新失敗");
            return response;
        }
        if (bankByName != null && !Objects.equals(id, bankByName.getId())) {
            response.setData("新的銀行名稱不可與其他銀行重複!");
            response.setHead("更新失敗");
            return response;
        }
        // 使用 Hutool 的 copyProperties 方法并指定忽略 null 值
        BeanUtil.copyProperties(bankDto, oldBank, CopyOptions.create().setIgnoreNullValue(true));

        if (bankDto.getMethod() == 1) {
            message = String.format("%s 使用XML的update", CLASS);
            bankXmlMapper.update(oldBank);
            log.info("{} 使用XML的update", CLASS);
        } else if (bankDto.getMethod() == 2) {
            message = String.format("%s 使用註解的update", CLASS);
            bankAnnoMapper.update(oldBank);
            log.info("{} 使用註解的update", CLASS);
        } else if (bankDto.getMethod() == 3) {
            message = String.format("%s 使用Provider的update", CLASS);
            bankProviderMapper.update(oldBank);
            log.info("{} 使用Provider的update", CLASS);
        } else if (bankDto.getMethod() == 4) {
            message = String.format("%s 使用BaseMapper的update", CLASS);
            bankBaseMapper.update(oldBank);
            log.info("{} 使用BaseMapper的update", CLASS);
        }

        Bank result = bankXmlMapper.selectById(id);
        BankDto resultDto = new BankDto();
        BeanUtil.copyProperties(result, resultDto);
        resultDto.setMethod(bankDto.getMethod());
        resultDto.setMessage(message);

        response.setData(resultDto);
        response.setHead("更新成功");

        return response;
    }

    @Override
    public ApiResult deleteById(Long id, Integer method) {
        String message = null;

        ApiResult response = new ApiResult();

        Bank oldBank = bankXmlMapper.selectById(id);
        if (oldBank == null) {
            response.setData("無此id的銀行!");
            response.setHead("刪除失敗");
            return response;
        }

        if (method == 1) {
            message = String.format("%s 使用XML的deleteById", CLASS);
            bankXmlMapper.deleteById(id);
            log.info("{} 使用XML的deleteById", CLASS);
        } else if (method == 2) {
            message = String.format("%s 使用註解的deleteById", CLASS);
            bankAnnoMapper.deleteById(id);
            log.info("{} 使用註解的deleteById", CLASS);
        } else if (method == 3) {
            message = String.format("%s 使用Provider的deleteById", CLASS);
            bankProviderMapper.deleteById(id);
            log.info("{} 使用Provider的deleteById", CLASS);
        } else if (method == 4) {
            message = String.format("%s 使用BaseMapper的deleteById", CLASS);
            bankBaseMapper.deleteById(id);
            log.info("{} 使用BaseMapper的deleteById", CLASS);
        }

        Bank result = bankXmlMapper.selectById(id);

        response.setData(message);
        if(result == null ){
            response.setHead("刪除成功");
        } else {
            response.setHead("刪除失敗");
        }

        return response;
    }

    @Override
    public ApiResult delete(Long id, Integer method) {
        String message = null;

        ApiResult response = new ApiResult();


        if (method == 1) {
            message = String.format("%s 使用XML的delete", CLASS);
            bankXmlMapper.delete(id);
            log.info("{} 使用XML的delete", CLASS);
        } else if (method == 2) {
            message = String.format("%s 使用註解的delete", CLASS);
            bankAnnoMapper.delete(id);
            log.info("{} 使用註解的delete", CLASS);
        } else if (method == 3) {
            message = String.format("%s 使用Provider的delete", CLASS);
            bankProviderMapper.delete(id);
            log.info("{} 使用Provider的delete", CLASS);
        } else if (method == 4) {
            message = String.format("%s 使用BaseMapper的delete", CLASS);
            bankBaseMapper.delete(id);
            log.info("{} 使用BaseMapper的delete", CLASS);
        }

        Bank result = bankXmlMapper.selectById(id);

        response.setData(message);
        if( result == null ){
            response.setHead("刪除成功");
        } else {
            response.setHead("刪除失敗");
        }

        return response;
    }

    @Override
    public ApiResult findByCondition(int start, int end) {
        List<Bank> banks = bankBaseMapper.selectAll();

        log.info("banks:{}", banks);

        List<Bank> result = banks.stream()
                .filter(bank -> {
                    int bankCode = Integer.parseInt(bank.getBankCode()); // 提早轉型
                    boolean validStart = start == -1 || bankCode >= start; // 處理 start 條件
                    boolean validEnd = end == -1 || bankCode <= end;       // 處理 end 條件
                    return validStart && validEnd; // 合併條件
                })
                .toList();
        log.info("Result:{}", result);

        ApiResult response = new ApiResult();
        response.setData(result);
        response.setHead("Success");
        return response;
    }



}
