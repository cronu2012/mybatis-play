package com.example.mybatis_play.domain;

import com.example.mybatis_play.domain.Bank;
import org.apache.ibatis.jdbc.SQL;


public class BankSqlProvider {

    /**
     * 查询所有银行信息
     * @return
     */
    public String selectAll() {
        return new SQL() {{
            SELECT("*");
            FROM("t_bank");
            WHERE("is_del = 0");
        }}.toString();
    }
    /**
     * 根据ID查询银行信息
     * @param id
     * @return
     */
    public String selectById(Long id) {
        return new SQL() {{
            SELECT("*");
            FROM("t_bank");
            WHERE("id = #{id}");
            AND();
            WHERE("is_del = 0");
        }}.toString();
    }
    /**
     * 根据bankCode查询银行信息
     * @param bankCode
     * @return
     */
    public String selectByBankCode(String bankCode) {
        return new SQL() {{
            SELECT("*");
            FROM("t_bank");
            WHERE("bank_code = #{bankCode}");
            AND();
            WHERE("is_del = 0");
        }}.toString();
    }

    /**
     * 根据bankName查询银行信息
     * @param bankName
     * @return
     */
    public String selectByBankName(String bankName) {
        return new SQL() {{
            SELECT("*");
            FROM("t_bank");
            WHERE("bank_name = #{bankName}");
            AND();
            WHERE("is_del = 0");
        }}.toString();
    }

    /**
     * 插入新银行信息
     * @param bank
     * @return
     */
    public String insert(Bank bank) {
        return new SQL() {{
            INSERT_INTO("t_bank");
            VALUES("create_by", "#{createBy}");
            VALUES("update_by", "#{updateBy}");
            VALUES("bank_code", "#{bankCode}");
            VALUES("bank_name", "#{bankName}");
        }}.toString();
    }

    /**
     * 更新银行信息
     * @param id
     * @param bank
     * @return
     */
    public String update(Long id, Bank bank) {
        return new SQL() {{
            UPDATE("t_bank");
            if (bank.getCreateBy() != null) {
                SET("create_by = #{createBy}");
            }
            if (bank.getUpdateBy() != null) {
                SET("update_by = #{updateBy}");
            }
            if (bank.getBankCode() != null) {
                SET("bank_code = #{bankCode}");
            }
            if (bank.getBankName() != null) {
                SET("bank_name = #{bankName}");
            }
            if (bank.getIsEnable() != null) {
                SET("is_enable = #{isEnable}");
            }
            WHERE("id = #{id}");
            AND();
            WHERE("is_del = 0");
        }}.toString();
    }

    /**
     * 逻辑删除银行信息
     * @param id
     * @return
     */
    public String deleteById(Long id){
        return new SQL() {{
            UPDATE("t_bank");
            SET("is_del = 1");
            WHERE("id = #{id}");
        }}.toString();
    }

    /**
     * 從資料庫删除银行資料
     * @param id
     * @return
     */
    public String delete(Long id){
        return new SQL() {{
            DELETE_FROM("t_bank");
            WHERE("id = #{id}");
        }}.toString();
    }
}