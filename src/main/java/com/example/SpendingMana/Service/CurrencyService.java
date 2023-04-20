package com.example.SpendingMana.Service;

import com.example.SpendingMana.entity.Currency;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CurrencyService {
    List<Currency> listCurrency();
    Currency addCurrency(Currency currency);
    Currency updateCurrency(Currency currency,Long id);
    void deleteCurrency(Long id);
    Boolean exportCurrency();

    List<Currency> importCurrency(MultipartFile file) throws IOException;
}
