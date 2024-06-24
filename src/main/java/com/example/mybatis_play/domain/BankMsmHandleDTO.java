package com.example.mybatis_play.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BankMsmHandleDTO {
    String bankCode;
    String bankAccount;
    Date payTime;
    Long amount;
}
