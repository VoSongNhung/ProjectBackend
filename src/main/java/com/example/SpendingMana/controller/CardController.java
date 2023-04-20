package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.CardBrandService;
import com.example.SpendingMana.Service.CardService;
import com.example.SpendingMana.dto.CardDTO;
import com.example.SpendingMana.dto.CardUpdateDTO;
import com.example.SpendingMana.entity.Card;
import com.example.SpendingMana.entity.CardBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {
    @Autowired
    CardService cardService;

    @GetMapping("/getall")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public List<Card> getAllCard(){
        return cardService.listCard();
    }
    @PostMapping("/add")
    @RolesAllowed("ROLE_ADMIN")
    public Card addCard(@RequestBody CardDTO cardDTO){
        return cardService.addCard(cardDTO);
    }
    @PutMapping("/update/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public Card updateCard(@RequestBody CardUpdateDTO cardUpdateDTO, @PathVariable Long id){
        return cardService.updateCard(cardUpdateDTO,id);
    }
    @DeleteMapping("/delete/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public void deleteCard(@PathVariable("id") Long id){
        cardService.deleteCard(id);
    }

}
