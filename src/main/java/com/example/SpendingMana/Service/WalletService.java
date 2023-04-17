package com.example.SpendingMana.Service;

import com.example.SpendingMana.entity.Wallet;
import com.example.SpendingMana.payload.Request.WalletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletService {
    public Wallet create (WalletRequest walletRequest);
    public Page<Wallet> getAll(Pageable pageable);
}
