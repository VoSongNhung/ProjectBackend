package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Service.CardBrandService;
import com.example.SpendingMana.entity.CardBrand;
import com.example.SpendingMana.entity.Currency;
import com.example.SpendingMana.respository.CardBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardBrandServiceImpl implements CardBrandService {
    @Autowired
    CardBrandRepository cardBrandRepository;
    @Override
    public List<CardBrand> listCardBrand() {
        return cardBrandRepository.findAll();
    }

    @Override
    public CardBrand addCardBrand(CardBrand cardBrand) {
        return cardBrandRepository.save(cardBrand);
    }

    @Override
    public CardBrand updateCardBrand(CardBrand cardBrand, Long id) {
        CardBrand findbyId = cardBrandRepository.findById(id).get();
        if(!findbyId.equals(null)){
            cardBrand.setBrandId(id);
            return cardBrandRepository.save(cardBrand);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void deleteCardBrand(Long id) {
        cardBrandRepository.deleteById(id);

    }
}
