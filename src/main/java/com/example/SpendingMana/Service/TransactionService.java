package com.example.SpendingMana.Service;

import com.example.SpendingMana.dto.TransactionDTO;
import com.example.SpendingMana.dto.TransactionUpdateDTO;
import com.example.SpendingMana.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.plaf.PanelUI;
import java.util.List;

public interface TransactionService {
    public Transaction create(TransactionDTO transactionDTO);
    public Page<Transaction> getAllTransaction(Pageable pageable);
    public boolean updateTransaction(TransactionUpdateDTO transactionUpdateDTO, Long id);
    public boolean deleteTransaction(Long id);
}
