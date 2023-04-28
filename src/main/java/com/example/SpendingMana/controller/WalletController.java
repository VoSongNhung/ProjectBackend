package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.WalletService;
import com.example.SpendingMana.dto.WalletDTO;
import com.example.SpendingMana.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    @Autowired
    WalletService walletServe;
    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Wallet> create(@RequestBody WalletDTO wallet){
        Wallet createdWallet = walletServe.create(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWallet);
    }

    @GetMapping
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<?> getaAllWallet(Pageable pageable){
        return new ResponseEntity<>(walletServe.getAll(pageable), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<?> deleteWallet(@PathVariable Long id){
        if(walletServe.deleteWallet(id)){
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete failed", HttpStatus.BAD_REQUEST);
    }
}
