package com.example.mybatis_play.daomapper;

import com.example.mybatis_play.domain.Bank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BankXmlMapper {

   List<Bank> selectAll();

   Bank selectById(Long id);

   Bank selectByBankCode(String bankCode);

   Bank selectByBankName(String bankName);

   void insert(Bank bank);

   Long update(Bank bank);

   void deleteById(Long id);

   void delete(Long id);
}
