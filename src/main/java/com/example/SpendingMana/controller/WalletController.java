package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.WalletService;
import com.example.SpendingMana.entity.Wallet;
import com.example.SpendingMana.payload.Request.WalletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    @Autowired
    WalletService walletServe;
    @PostMapping
    public Wallet create(@RequestBody WalletRequest wallet){
        return  walletServe.create(wallet);
    }
    @GetMapping
    public ResponseEntity<?> getaAllWallet(Pageable pageable){
        return new ResponseEntity<>(walletServe.getAll(pageable), HttpStatus.OK);
    }
}
