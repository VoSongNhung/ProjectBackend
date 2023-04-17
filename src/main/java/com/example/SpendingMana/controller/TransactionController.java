package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.TransactionService;
import com.example.SpendingMana.entity.Transaction;
import com.example.SpendingMana.payload.Request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService ;
    @PostMapping
    public Transaction create(@RequestBody TransactionRequest transactionRequest){
        return transactionService.create(transactionRequest);
    }
    @GetMapping
    public ResponseEntity<?> getAllTransaction(Pageable pageable){
        return new ResponseEntity<>(transactionService.getAllTransaction(pageable), HttpStatus.OK);
    }

}