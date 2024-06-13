package com.example.mybatis_play.daomapper;

import com.example.mybatis_play.domain.Bank;
import com.example.mybatis_play.domain.BankSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BankProviderMapper {

    @SelectProvider(type = BankSqlProvider.class, method = "selectAll")
    List<Bank> selectAll();

    @SelectProvider(type = BankSqlProvider.class, method = "selectById")
    Bank selectById(Long id);

    @SelectProvider(type = BankSqlProvider.class, method = "selectByBankCode")
    Bank selectByBankCode(String bankCode);

    @SelectProvider(type = BankSqlProvider.class, method = "selectByBankName")
    Bank selectByBankName(String bankName);

    @InsertProvider(type = BankSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insert(Bank bank);

    @UpdateProvider(type = BankSqlProvider.class, method = "update")
    Long update(Bank bank);

    @UpdateProvider(type = BankSqlProvider.class, method = "deleteById")
    int deleteById(Long id);

    @DeleteProvider(type = BankSqlProvider.class, method = "delete")
    int delete(Long id);
}
