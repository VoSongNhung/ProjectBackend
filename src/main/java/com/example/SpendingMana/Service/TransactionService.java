package com.example.SpendingMana.Service;

import com.example.SpendingMana.entity.Transaction;
import com.example.SpendingMana.payload.Request.TransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.plaf.PanelUI;
import java.util.List;

public interface TransactionService {
    public Transaction create(TransactionRequest transactionRequest);
    public Page<Transaction> getAllTransaction(Pageable pageable);
    public Transaction updateTransaction(TransactionRequest transactionRequest, Long id);
}
