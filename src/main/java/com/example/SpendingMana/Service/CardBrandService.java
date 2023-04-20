package com.example.SpendingMana.Service;

import com.example.SpendingMana.entity.CardBrand;
import com.example.SpendingMana.entity.Currency;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CardBrandService {
    List<CardBrand> listCardBrand();
    CardBrand addCardBrand(CardBrand cardBrand);
    CardBrand updateCardBrand(CardBrand cardBrand,Long id);
    void deleteCardBrand(Long id);
}
