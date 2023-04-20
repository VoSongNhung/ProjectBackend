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
import javax.annotation.security.RolesAllowed;


@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService ;
    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public Transaction create(@RequestBody TransactionDTO transactionDTO){
        return transactionService.create(transactionDTO);
    }
    @GetMapping
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<?> getAllTransaction(Pageable pageable){
        return new ResponseEntity<>(transactionService.getAllTransaction(pageable), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?>updateTransaction(@RequestBody TransactionUpdateDTO transactionUpdateDTO, @PathVariable Long id){
        if(transactionService.updateTransaction(transactionUpdateDTO, id)){
            return new ResponseEntity<>("update Success!!",HttpStatus.OK);
        }
        return new ResponseEntity<>("update Fail!!",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id){
        if(transactionService.deleteTransaction(id)){
            return new ResponseEntity<>("delete Success!!",HttpStatus.OK);
        }
        return new ResponseEntity<>("delete Fail!!",HttpStatus.BAD_REQUEST);
    }

}