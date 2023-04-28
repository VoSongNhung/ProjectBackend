package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Service.WalletService;
import com.example.SpendingMana.dto.WalletDTO;
import com.example.SpendingMana.entity.Currency;
import com.example.SpendingMana.entity.User;
import com.example.SpendingMana.entity.Wallet;
import com.example.SpendingMana.error.DataNotFoundException;
import com.example.SpendingMana.respository.CurrencyRepository;
import com.example.SpendingMana.respository.UserRepository;
import com.example.SpendingMana.respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletRepository walletRepo;
    @Autowired
    CurrencyRepository currencyRepo;
    @Override
    public Wallet create(WalletDTO walletDTO) {
        Wallet wallet = new Wallet();
        wallet.setIcon(walletDTO.getIcon());
        wallet.setCreadit(0);
        wallet.setCash(walletDTO.getCash());
        wallet.setTotal(walletDTO.getCash() + 0);
        Currency currency = currencyRepo.findById(walletDTO.getCurrencyId()).orElseThrow(() -> new DataNotFoundException("currency not found"));
        User user = userRepository.findById(walletDTO.getUserId()).orElseThrow(() -> new DataNotFoundException("user not found"));
        wallet.setCurrency(currency);
        wallet.setUser(user);
        return walletRepo.save(wallet);
    }

    @Override
    public Page<Wallet> getAll(Pageable pageable) {
        return walletRepo.findAll(pageable);
    }

    @Override
    public boolean deleteWallet(Long id) {
        Wallet wallet = walletRepo.findById(id).orElseThrow(() -> new DataNotFoundException("Wallet not found"));
        walletRepo.delete(wallet);
        return true;
    }
}
