package com.example.mybatis_play.controller;

import com.example.mybatis_play.dto.BankListDto;
import com.example.mybatis_play.service.BankXmlService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mybatis/bank")
@AllArgsConstructor
public class BankController {

    private final BankXmlService bankXmlService;


    @GetMapping("all")
    public ResponseEntity<?> findAll(@RequestParam Integer method){

        if(method == 1){
            BankListDto response = bankXmlService.findAll();
            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.ok().build();
    }
}
