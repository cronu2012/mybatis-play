package com.example.mybatis_play.daomapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis_play.domain.Bank;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BankBaseMapper extends BaseMapper<Bank> {

    default List<Bank> selectAll() {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0);
        return this.selectList(wrapper);
    }

    default Bank selectById(Long id) {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id)
                .eq("is_del", 0);
        return this.selectOne(wrapper);
    }

    default Bank selectByBankCode(String bankCode) {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("bank_code", bankCode)
                .eq("is_del", 0);
        return this.selectOne(wrapper);
    }

    default Bank selectByBankName(String bankName) {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("bank_name", bankName)
                .eq("is_del", 0);
        return this.selectOne(wrapper);
    }

    default int update(Bank bank) {
        UpdateWrapper<Bank> wrapper = new UpdateWrapper<>();
        wrapper.set(bank.getUpdateBy() != null, "update_by", bank.getUpdateBy())
                .set(bank.getBankCode() != null, "bank_code", bank.getBankCode())
                .set(bank.getBankName() != null, "bank_name", bank.getBankName())
                .set(bank.getIsEnable() != null, "is_enable", bank.getIsEnable())
                .eq("id", bank.getId())
                .eq("is_del", 0);
        return this.update(wrapper);
    }

    default  int deleteById(Long id) {
        UpdateWrapper<Bank> wrapper = new UpdateWrapper<>();
        wrapper.set("is_del", 1)
                .eq("id", id);
        return this.update(wrapper);
    }

    default int delete(Long id) {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return this.delete(wrapper);
    }
}
