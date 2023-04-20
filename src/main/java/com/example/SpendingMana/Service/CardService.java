package com.example.SpendingMana.Service;

import com.example.SpendingMana.dto.CardDTO;
import com.example.SpendingMana.dto.CardUpdateDTO;
import com.example.SpendingMana.entity.Card;
import com.example.SpendingMana.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardService{
    List<Card> listCard();
    Card addCard(CardDTO cardDTO);
    Card updateCard(CardUpdateDTO cardUpdateDTO, Long id);
    void deleteCard(Long id);
}
