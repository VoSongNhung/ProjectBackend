package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.TransactionService;
import com.example.SpendingMana.dto.TransactionDTO;
import com.example.SpendingMana.dto.TransactionUpdateDTO;
import com.example.SpendingMana.entity.Transaction;
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
    public Transaction create(@RequestBody TransactionDTO transactionDTO){
        return transactionService.create(transactionDTO);
    }
    @GetMapping
    public ResponseEntity<?> getAllTransaction(Pageable pageable){
        return new ResponseEntity<>(transactionService.getAllTransaction(pageable), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>updateTransaction(@RequestBody TransactionUpdateDTO transactionUpdateDTO, @PathVariable Long id){
        if(transactionService.updateTransaction(transactionUpdateDTO, id)){
            return new ResponseEntity<>("update Success!!",HttpStatus.OK);
        }
        return new ResponseEntity<>("update Fail!!",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id){
        if(transactionService.deleteTransaction(id)){
            return new ResponseEntity<>("delete Success!!",HttpStatus.OK);
        }
        return new ResponseEntity<>("delete Fail!!",HttpStatus.BAD_REQUEST);
    }

}