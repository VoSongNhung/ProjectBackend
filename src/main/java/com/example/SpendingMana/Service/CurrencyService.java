package com.example.SpendingMana.Service;

import com.example.SpendingMana.entity.Currency;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CurrencyService {
    Boolean exportCurrency();

    List<Currency> importCurrency(MultipartFile file) throws IOException;
}
