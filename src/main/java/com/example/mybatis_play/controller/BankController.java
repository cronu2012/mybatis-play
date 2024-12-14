package com.example.mybatis_play.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.example.mybatis_play.dto.ApiResult;
import com.example.mybatis_play.dto.BankDto;
import com.example.mybatis_play.service.BankService;
import com.example.mybatis_play.service.BankServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mybatis/bank")
@AllArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping("all")
    public ResponseEntity<?> findAll(@RequestParam Integer method) {

        ApiResult response;
        try {
            response = bankService.findAll(method);
        } catch (Exception e) {
            log.error("Service findAll處理錯誤: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> findOne(@RequestParam Long id, @RequestParam Integer method) {
        ApiResult response ;
        try {
            response = bankService.findById(id, method);
        } catch (Exception e) {
            log.error("Service findById處理錯誤: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("")
    public ResponseEntity<?> create(BankDto bankDto) {
        ApiResult response = new ApiResult();

        String error = checkBankDto(bankDto);
        if (!StrUtil.isBlank(error)) {
            response.setData(new JSONObject());
            response.setHead(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            response = bankService.create(bankDto);
        } catch (Exception e) {
            log.error("Service create處理錯誤: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        return ResponseEntity.status("創建成功".equals(response.getHead()) ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, BankDto bankDto){
        ApiResult response = new ApiResult();

        String error = checkBankDto2(bankDto);
        if (!StrUtil.isBlank(error)) {
            response.setData(new JSONObject());
            response.setHead(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            response = bankService.update(id, bankDto);
        } catch (Exception e) {
            log.error("Service update處理錯誤: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        return ResponseEntity.status("更新成功".equals(response.getHead()) ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteId(@PathVariable Long id, Integer method){
        ApiResult response;
        try {
            response = bankService.deleteById(id, method);
        } catch (Exception e) {
            log.error("Service deleteById處理錯誤: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        return ResponseEntity.status("刪除成功".equals(response.getHead()) ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Integer method){
        ApiResult response;
        try {
            response = bankService.delete(id, method);
        } catch (Exception e) {
            log.error("Service delete處理錯誤: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        return ResponseEntity.status("刪除成功".equals(response.getHead()) ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }

    @PostMapping("/bank_code")
    public ResponseEntity<?> findByCondition(Integer start, Integer end){
        if(start == null) start = -1;
        if(end == null) end = -1;

        ApiResult response;
        try {
            response = bankService.findByCondition(start, end);
        } catch (Exception e) {
            log.error("Service findByCondition: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        return ResponseEntity.status("Success".equals(response.getHead()) ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }

    private String checkBankDto(BankDto bankDto) {
        StringBuilder sb = new StringBuilder();

        if (bankDto == null) {
            sb.append("BankDto不可為空");
            return sb.toString();
        }

        List<Integer> methods = List.of(1, 2, 3, 4);
        if (bankDto.getMethod() == null || !methods.contains(bankDto.getMethod())) {
            sb.append("method參數 錯誤");
            return sb.toString();
        }

        if (StrUtil.isBlank(bankDto.getCreateBy())) {
            sb.append("創建者不得為空!");
        }
        if (StrUtil.isBlank(bankDto.getBankCode())) {
            sb.append("銀行編碼不得為空!");
        }
        if (StrUtil.isBlank(bankDto.getBankName())) {
            sb.append("銀行名稱不得為空!");
        }
        return sb.toString();
    }

    private String checkBankDto2(BankDto bankDto) {
        StringBuilder sb = new StringBuilder();

        if (bankDto == null) {
            sb.append("BankDto不可為空");
            return sb.toString();
        }

        List<Integer> methods = List.of(1, 2, 3, 4);
        if (bankDto.getMethod() == null || !methods.contains(bankDto.getMethod())) {
            sb.append("method參數 錯誤");
            return sb.toString();
        }

        if (StrUtil.isBlank(bankDto.getUpdateBy())) {
            sb.append("修改者不得為空!");
        }

        return sb.toString();
    }
}
