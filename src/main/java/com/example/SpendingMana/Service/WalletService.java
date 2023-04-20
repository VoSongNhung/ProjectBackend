package com.example.SpendingMana.Service;

import com.example.SpendingMana.dto.WalletDTO;
import com.example.SpendingMana.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletService {
    public Wallet create (WalletDTO walletDTO);
    public Page<Wallet> getAll(Pageable pageable);
    public boolean deleteWallet(Long id);
}
