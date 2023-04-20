package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Service.CardService;
import com.example.SpendingMana.dto.CardDTO;
import com.example.SpendingMana.dto.CardUpdateDTO;
import com.example.SpendingMana.entity.Card;
import com.example.SpendingMana.entity.CardBrand;
import com.example.SpendingMana.entity.Wallet;
import com.example.SpendingMana.error.DataNotFoundException;
import com.example.SpendingMana.respository.CardBrandRepository;
import com.example.SpendingMana.respository.CardRepository;
import com.example.SpendingMana.respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CardBrandRepository cardBrandRepository;
    @Autowired
    WalletRepository walletRepository;
    @Autowired

    @Override
    public List<Card> listCard() {
        return cardRepository.findAll();
    }

    @Override
    public Card addCard(CardDTO cardDTO) {
        Card card = new Card();
        CardBrand cardBrand = cardBrandRepository.findById(cardDTO.getCardBrandId()).orElseThrow(() -> new DataNotFoundException("Cardbrand not found"));
        Wallet wallet = walletRepository.findById(cardDTO.getWalletId()).orElseThrow(() -> new DataNotFoundException("wallet not found"));
        card.setCardBrand(cardBrand);
        card.setCardNumber(cardDTO.getCardNumber());
        card.setAmount(cardDTO.getAmount());
        card.setWallet(wallet);
        wallet.setCreadit(wallet.getCreadit() + cardDTO.getAmount());
        wallet.setTotal(wallet.getCash() + wallet.getCreadit());
        walletRepository.save(wallet);
        cardRepository.save(card);
        return card;
    }

    @Override
    public Card updateCard(CardUpdateDTO cardUpdateDTO, Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Card not found"));
        CardBrand cardBrand = cardBrandRepository.findById(cardUpdateDTO.getCardBrandId()).orElseThrow(() -> new DataNotFoundException("Cardbrand not found"));
        Wallet wallet = card.getWallet();
        card.setCardBrand(cardBrand);
        card.setCardNumber(cardUpdateDTO.getCardNumber());
        if(cardUpdateDTO.getAmount() != card.getAmount()){
            wallet.setCreadit(wallet.getCreadit() + cardUpdateDTO.getAmount() - card.getAmount());
            wallet.setTotal(wallet.getCash() + wallet.getCreadit());
            walletRepository.save(wallet);
        }
//        else if(cardUpdateDTO.getAmount() < card.getAmount()){
//            wallet.setCreadit(wallet.getCreadit() + cardUpdateDTO.getAmount() - card.getAmount());
//            wallet.setTotal(wallet.getCash() + wallet.getCreadit());
//            walletRepository.save(wallet);
//        }
        card.setWallet(wallet);
        card.setAmount(cardUpdateDTO.getAmount());
        cardRepository.save(card);
        return card;
    }

    @Override
    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Card not found"));
        Wallet wallet = card.getWallet();
        wallet.setCreadit(wallet.getCreadit() - card.getAmount());
        wallet.setTotal(wallet.getCash() + wallet.getCreadit());
        walletRepository.save(wallet);
        cardRepository.deleteById(id);
    }
}
