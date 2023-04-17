package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Service.TransactionService;
import com.example.SpendingMana.entity.Income;
import com.example.SpendingMana.entity.Transaction;
import com.example.SpendingMana.entity.TypeOfTransaction;
import com.example.SpendingMana.entity.Wallet;
import com.example.SpendingMana.error.DataNotFoundException;
import com.example.SpendingMana.payload.Request.TransactionRequest;
import com.example.SpendingMana.payload.Response.TransactionResponse;
import com.example.SpendingMana.respository.IncomeRepository;
import com.example.SpendingMana.respository.TransactionRepository;
import com.example.SpendingMana.respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRespo;
    @Autowired
    WalletRepository walletRepo;
    @Autowired
    IncomeRepository incomeRepo;
    List<Transaction> transactionList = new ArrayList<>();
    @Override
    public Transaction create(TransactionRequest transactionRequest) {
        Income income1 = null;
        Transaction transaction = new Transaction();
        transaction.setDate(transactionRequest.getDate());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setNote(transactionRequest.getNote());
        transaction.setType(transactionRequest.getType());
        Wallet wallet = walletRepo.findById(transactionRequest.getWalletId()).orElseThrow(() -> new DataNotFoundException("wallet not found"));
        transaction.setWallet(wallet);
        transactionRespo.save(transaction);
        List<Income> incomes = incomeRepo.findAll();
        for (Income income : incomes){
            if(income.getDateTime().equals(transactionRequest.getDate())){
                income1 = income;
            }
        }
        if(income1 != null){
            transactionList =income1.getTransactions();
            transactionList.add(transaction);
            incomeRepo.save(income1);
        }
        else {
            Income income = new Income();
            income.setDateTime(transactionRequest.getDate());
            transactionList.add(transaction);
            income.setTransactions(transactionList);
            incomeRepo.save(income);
        }
        if(transactionRequest.getType().equals(TypeOfTransaction.CASH)){
            wallet.setCash(wallet.getCash() + transactionRequest.getAmount());
            wallet.setTotal(wallet.getTotal() + transactionRequest.getAmount());
            walletRepo.save(wallet);
        }
        return transaction;
    }

    @Override
    public Page<Transaction> getAllTransaction(Pageable pageable) {
        return transactionRespo.findAll(pageable);
    }

    @Override
    public Transaction updateTransaction(TransactionRequest transactionRequest, Long id) {
        Transaction transaction = transactionRespo.findById(id).orElseThrow(() -> new DataNotFoundException("transaction not found"));
        transaction.setDate(transactionRequest.getDate());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setNote(transactionRequest.getNote());
        transaction.setType(transactionRequest.getType());
        Wallet wallet = walletRepo.findById(transactionRequest.getWalletId()).orElseThrow(() -> new DataNotFoundException("wallet not found"));
        transaction.setWallet(wallet);
        return transactionRespo.save(transaction);
    }
}
