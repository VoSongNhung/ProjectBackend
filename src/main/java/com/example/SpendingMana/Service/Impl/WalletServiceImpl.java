package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Service.WalletService;
import com.example.SpendingMana.entity.Currency;
import com.example.SpendingMana.entity.Wallet;
import com.example.SpendingMana.error.DataNotFoundException;
import com.example.SpendingMana.payload.Request.WalletRequest;
import com.example.SpendingMana.respository.CurrencyRepository;
import com.example.SpendingMana.respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepo;
    @Autowired
    CurrencyRepository currencyRepo;
    @Override
    public Wallet create(WalletRequest walletRequest) {
        Wallet wallet = new Wallet();
        wallet.setIcon(walletRequest.getIcon());
        wallet.setCreadit(0);
        wallet.setCash(walletRequest.getCash());
        wallet.setTotal(walletRequest.getCash() + 0);
        Currency currency = currencyRepo.findById(walletRequest.getCurrencyId()).orElseThrow(() -> new DataNotFoundException("currency not found"));
        wallet.setCurrency(currency);
        return walletRepo.save(wallet);
    }

    @Override
    public Page<Wallet> getAll(Pageable pageable) {
        return walletRepo.findAll(pageable);
    }
}
