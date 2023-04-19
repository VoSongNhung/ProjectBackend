package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/outcome")
public class OutcomeController {
    @Autowired
    OutcomeService outcomeService;
    @GetMapping
    public ResponseEntity<?> getAllOutcome(Pageable pageable){
        return new ResponseEntity<>(outcomeService.getAllOutcome(pageable), HttpStatus.OK);
    }
}
