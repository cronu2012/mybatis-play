package com.example.mybatis_play.daomapper;

import com.example.mybatis_play.domain.Bank;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface BankDao {

   List<Bank> selectAll();

   Bank selectById(Long id);

   Long insert(Bank bank);

   Bank update(Bank bank);

   int deleteById(Long id);

   int delete(Long id);
}
