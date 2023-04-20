package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.CardBrandService;
import com.example.SpendingMana.entity.CardBrand;
import com.example.SpendingMana.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cardbrand")
public class CardBrandController {
    @Autowired
    CardBrandService cardBrandService;

    @GetMapping("/getall")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public List<CardBrand> getAllCardBrand(){
        return cardBrandService.listCardBrand();
    }
    @PostMapping("/add")
    @RolesAllowed("ROLE_ADMIN")
    public CardBrand addCardBrand(@RequestBody CardBrand cardBrand){
        return cardBrandService.addCardBrand(cardBrand);
    }
    @PutMapping("/update/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public CardBrand updateCardBrand(@RequestBody CardBrand currency,@PathVariable Long id){
        return cardBrandService.updateCardBrand(currency,id);
    }
    @DeleteMapping("/delete/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public void deleteCardBrand(@PathVariable("id") Long id){
        cardBrandService.deleteCardBrand(id);
    }

}
