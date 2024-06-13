package com.example.mybatis_play.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResult<T> implements Serializable {
    private String head;
    private T data;
}
