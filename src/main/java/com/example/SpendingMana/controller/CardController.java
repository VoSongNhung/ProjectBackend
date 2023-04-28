package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.CardBrandService;
import com.example.SpendingMana.Service.CardService;
import com.example.SpendingMana.dto.CardDTO;
import com.example.SpendingMana.dto.CardUpdateDTO;
import com.example.SpendingMana.entity.Card;
import com.example.SpendingMana.entity.CardBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {
    @Autowired
    CardService cardService;

    @GetMapping
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public List<Card> getAllCard(){
        return cardService.listCard();
    }
    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> addCard(@RequestBody CardDTO cardDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.addCard(cardDTO));
    }
    @PutMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public Card updateCard(@RequestBody CardUpdateDTO cardUpdateDTO, @PathVariable Long id){
        return cardService.updateCard(cardUpdateDTO,id);
    }
    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public void deleteCard(@PathVariable("id") Long id){
        cardService.deleteCard(id);
    }

}
