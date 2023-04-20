package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;


@RestController
@RequestMapping("/api/v1/income")
public class IncomeController {
    @Autowired
    IncomeService incomeService;
    @GetMapping
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<?> getAllIncome(Pageable pageable){
        return new ResponseEntity<>(incomeService.getAllIncome(pageable), HttpStatus.OK);
    }
}
