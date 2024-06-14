package com.example.mybatis_play.daomapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mybatis_play.domain.Bank;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BankBaseMapper {
    
    private final BankMapper bankMapper;

    public List<Bank> selectAll() {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0);
        return bankMapper.selectList(wrapper);
    }

    public Bank selectById(Long id) {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id)
                .eq("is_del", 0);
        return bankMapper.selectOne(wrapper);
    }

    public Bank selectByBankCode(String bankCode) {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("bank_code", bankCode)
                .eq("is_del", 0);
        return bankMapper.selectOne(wrapper);
    }

    public Bank selectByBankName(String bankName) {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("bank_name", bankName)
                .eq("is_del", 0);
        return bankMapper.selectOne(wrapper);
    }

    public void insert(Bank bank) {
        bankMapper.insert(bank);
    }

    public int update(Bank bank) {
        UpdateWrapper<Bank> wrapper = new UpdateWrapper<>();
        wrapper.set(bank.getUpdateBy() != null, "update_by", bank.getUpdateBy())
                .set(bank.getBankCode() != null, "bank_code", bank.getBankCode())
                .set(bank.getBankName() != null, "bank_name", bank.getBankName())
                .set(bank.getIsEnable() != null, "is_enable", bank.getIsEnable())
                .eq("id", bank.getId())
                .eq("is_del", 0);
        return bankMapper.update(wrapper);
    }

    public void deleteById(Long id) {
        UpdateWrapper<Bank> wrapper = new UpdateWrapper<>();
        wrapper.set("is_del", 1)
                .eq("id", id);
        bankMapper.update(wrapper);
    }

    public void delete(Long id) {
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        bankMapper.delete(wrapper);
    }
}
