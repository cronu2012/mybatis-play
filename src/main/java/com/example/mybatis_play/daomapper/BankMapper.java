package com.example.mybatis_play.daomapper;

import com.example.mybatis_play.domain.Bank;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface BankMapper {

    @Results(id = "BankResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "bankCode", column = "bank_code"),
            @Result(property = "bankName", column = "bank_name"),
            @Result(property = "isDel", column = "is_del"),
            @Result(property = "isEnable", column = "is_enable")
    })
    @Select("SELECT * FROM t_bank WHERE is_del = 0")
    List<Bank> selectAll();

    @ResultMap("BankResultMap")
    @Select("SELECT * FROM t_bank WHERE id = #{id} AND is_del = 0")
    Bank selectById(Long id);

    @ResultMap("BankResultMap")
    @Select("SELECT * FROM t_bank WHERE bank_code = #{bankCode} AND is_del = 0")
    Bank selectByBankCode(String bankCode);

    @ResultMap("BankResultMap")
    @Select("SELECT * FROM t_bank WHERE bank_name = #{bankName} AND is_del = 0")
    Bank selectByBankName(String bankName);

    @Insert("INSERT INTO t_bank (create_by, update_by, bank_code, bank_name)" +
            " VALUES (#{createBy}, #{updateBy}, #{bankCode}, #{bankName})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insert(Bank bank);

    @Update("UPDATE t_bank" +
            " SET update_by = #{updateBy}," +
            " bank_code = #{bankCode}," +
            " bank_name = #{bankName}," +
            " is_enable = #{isEnable}" +
            " WHERE id = #{id} AND is_del = 0")
    Long update(Bank bank);

    @Update("UPDATE t_bank SET is_del = 1 WHERE id = #{id}")
    int deleteById(Long id);

    @Delete("DELETE FROM t_bank WHERE id = #{id}")
    int delete(Long id);
}
