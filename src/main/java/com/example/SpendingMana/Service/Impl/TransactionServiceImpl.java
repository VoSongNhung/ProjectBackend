package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Service.TransactionService;
import com.example.SpendingMana.dto.TransactionDTO;
import com.example.SpendingMana.dto.TransactionUpdateDTO;
import com.example.SpendingMana.entity.*;
import com.example.SpendingMana.error.DataNotFoundException;
import com.example.SpendingMana.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRespo;
    @Autowired
    WalletRepository walletRepo;
    @Autowired
    OutcomeRepository outcomeRepo;
    @Autowired
    IncomeRepository incomeRepo;
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    CardRepository cardRepo;

    List<Transaction> transactionList = new ArrayList<>();
    @Override
    public Transaction create(TransactionDTO transactionDTO) {
        Income income1 = null;
        Outcome outcome1 = null;
        Transaction transaction = new Transaction();
        transaction.setDate(transactionDTO.getDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setNote(transactionDTO.getNote());
        transaction.setType(transactionDTO.getType());
        Wallet wallet = walletRepo.findById(transactionDTO.getWalletId()).orElseThrow(() -> new DataNotFoundException("wallet not found"));
        transaction.setWallet(wallet);
        Category category = categoryRepo.findById(transactionDTO.getCategoryId()).orElseThrow(() -> new DataNotFoundException("category not found"));
        transaction.setCategory(category);
        transactionRespo.save(transaction);

        //xử lí income
        if(category.getType().equals(Type.INCOME)){
            List<Income> incomes = incomeRepo.findAll();
            for (Income income : incomes){
                if(income.getDateTime().equals(transactionDTO.getDate())){
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
                income.setDateTime(transactionDTO.getDate());
                transactionList.add(transaction);
                income.setTransactions(transactionList);
                incomeRepo.save(income);
            }

            //update tiền bên wallet
            if(transactionDTO.getType().equals(TypeOfTransaction.CASH)){
                wallet.setCash(wallet.getCash() + transactionDTO.getAmount());
                wallet.setTotal(wallet.getTotal() + transactionDTO.getAmount());
                walletRepo.save(wallet);
            }else {
                Card card = cardRepo.findById(transactionDTO.getCardId()).orElseThrow(() -> new DataNotFoundException("card not found"));
                card.setAmount(card.getAmount() + transactionDTO.getAmount());
                wallet.setCreadit(wallet.getCreadit() + transactionDTO.getAmount());
                wallet.setTotal(wallet.getTotal() + transactionDTO.getAmount());
                walletRepo.save(wallet);
            }
        }

        //xử lí outcome
        else {
            List<Outcome> outcomes = outcomeRepo.findAll();
            for (Outcome outcome : outcomes){
                if(outcome.getDateTime().equals(transactionDTO.getDate())){
                    outcome1 = outcome;
                }
            }
            if(outcome1 != null){
                transactionList = outcome1.getTransactions();
                transactionList.add(transaction);
                outcomeRepo.save(outcome1);
            }
            else {
                Outcome outcome = new Outcome();
                outcome.setDateTime(transactionDTO.getDate());
                transactionList.add(transaction);
                outcome.setTransactions(transactionList);
                outcomeRepo.save(outcome);
            }

            //update tiền bên wallet
            if(transactionDTO.getType().equals(TypeOfTransaction.CASH)){
                wallet.setCash(wallet.getCash() - transactionDTO.getAmount());
                wallet.setTotal(wallet.getTotal() - transactionDTO.getAmount());
                walletRepo.save(wallet);
            }else {
                Card card = cardRepo.findById(transactionDTO.getCardId()).orElseThrow(() -> new DataNotFoundException("card not found"));
                card.setAmount(card.getAmount() - transactionDTO.getAmount());
                wallet.setCreadit(wallet.getCreadit() - transactionDTO.getAmount());
                wallet.setTotal(wallet.getTotal() - transactionDTO.getAmount());
                walletRepo.save(wallet);
            }
        }

        return transaction;
    }

    @Override

    public Page<Transaction> getAllTransaction(Pageable pageable) {
        return transactionRespo.findAll(pageable);
    }
//    update chua lam xong
    @Override
    public boolean updateTransaction(TransactionUpdateDTO transactionDTO, Long id) {
        Transaction transaction = transactionRespo.findById(id).orElseThrow(() -> new DataNotFoundException("transaction not found"));

//;        Wallet wallet = walletRepo.findById(transactionDTO.getWalletId()).orElseThrow(() -> new DataNotFoundException("wallet not found"));
        Wallet wallet = transaction.getWallet();
        Category category = categoryRepo.findById(transactionDTO.getCategoryId()).orElseThrow(() -> new DataNotFoundException("category not found"));
//        Card card = cardRepo.findById(transactionDTO.getCardId()).orElseThrow(() -> new DataNotFoundException("card not found"));

        // kiểm tra xem category là loại nào
        // điều kiện đang check là category loại income

        if(category.getType().equals(Type.INCOME)){
            // kiểm tra xem phương thức thanh toán là tiền túi hay thẻ
            //điều kiện đang check là thanh toán bằng tiền túi

            if(transactionDTO.getType().equals(TypeOfTransaction.CASH)){
                //nếu tiền sau khi update lớn hơn ban đầu thì phải xử lí lại
                if(transactionDTO.getAmount() > transaction.getAmount()){
                    wallet.setCash(wallet.getCash() + (transactionDTO.getAmount() - transaction.getAmount()));
                }
                //nếu tiền sau khi update nhỏ hơn ban đầu thì phải xử lí lại
                else if(transactionDTO.getAmount() < transaction.getAmount()){
                    wallet.setCash(wallet.getCash() - (transaction.getAmount() - transactionDTO.getAmount()));
                }
            }
            //điều kiện đang check là thanh toán bằng tiền thẻ
            else {
                Card card = cardRepo.findById(transactionDTO.getCardId()).orElseThrow(() -> new DataNotFoundException("card not found"));
                //nếu tiền sau khi update lớn hơn ban đầu thì phải xử lí lại
                if (!card.getCardId().equals(transaction.getCard().getCardId())){
                    Card card1 = transaction.getCard();
                    card1.setAmount(card1.getAmount() - transaction.getAmount());
                    cardRepo.save(card1);
                }
                if(transactionDTO.getAmount() > transaction.getAmount()){
                    card.setAmount(card.getAmount() + (transactionDTO.getAmount() - transaction.getAmount()));
                    wallet.setCreadit(wallet.getCreadit() + (transactionDTO.getAmount() - transaction.getAmount()));
                }
                //nếu tiền sau khi update nhỏ hơn ban đầu thì phải xử lí lại
                else if(transactionDTO.getAmount() < transaction.getAmount()){
                    card.setAmount(card.getAmount() - (transactionDTO.getAmount() - transaction.getAmount()));
                    wallet.setCreadit(wallet.getCreadit() - (transaction.getAmount() - transactionDTO.getAmount()));
                }
                cardRepo.save(card);
            }
        }
        // điều kiện đang check là category loại outcome
        else {
            if(transactionDTO.getType().equals(TypeOfTransaction.CASH)){
                //nếu tiền sau khi update lớn hơn ban đầu thì phải xử lí lại
                if(transactionDTO.getAmount() > transaction.getAmount()){
                    wallet.setCash(wallet.getCash() - (transactionDTO.getAmount() - transaction.getAmount()));
                }
                //nếu tiền sau khi update nhỏ hơn ban đầu thì phải xử lí lại
                else if(transactionDTO.getAmount() < transaction.getAmount()){
                    wallet.setCash(wallet.getCash() + (transaction.getAmount() - transactionDTO.getAmount()));
                }
            }
            //điều kiện đang check là thanh toán bằng tiền thẻ
            else {
                Card card = cardRepo.findById(transactionDTO.getCardId()).orElseThrow(() -> new DataNotFoundException("card not found"));
                //nếu tiền sau khi update lớn hơn ban đầu thì phải xử lí lại
                if (!card.getCardId().equals(transaction.getCard().getCardId())){
                    Card card1 = transaction.getCard();
                    card1.setAmount(card1.getAmount() + transaction.getAmount());
                    cardRepo.save(card1);
                }
                if(transactionDTO.getAmount() > transaction.getAmount()){
                    card.setAmount(card.getAmount() - (transactionDTO.getAmount() - transaction.getAmount()));
                    wallet.setCash(wallet.getCreadit() - (transactionDTO.getAmount() - transaction.getAmount()));
                }
                //nếu tiền sau khi update nhỏ hơn ban đầu thì phải xử lí lại
                else if(transactionDTO.getAmount() < transaction.getAmount()){
                    card.setAmount(card.getAmount() + (transactionDTO.getAmount() - transaction.getAmount()));
                    wallet.setCash(wallet.getCreadit() + (transaction.getAmount() - transactionDTO.getAmount()));
                }
                cardRepo.save(card);
            }
        }
        wallet.setTotal(wallet.getCash() + wallet.getCreadit());
        walletRepo  .save(wallet);

        transaction.setDate(transactionDTO.getDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setNote(transactionDTO.getNote());
        transaction.setType(transactionDTO.getType());
        transaction.setCategory(category);
        transactionRespo.save(transaction);
        return true;
    }

    @Override
    public boolean deleteTransaction(Long id) {
        Transaction transaction = transactionRespo.findById(id).orElseThrow(() -> new DataNotFoundException("transaction not found"));
        Wallet wallet = transaction.getWallet();
        if(transaction.getCategory().getType().equals(Type.EXPENSE)){
            if(transaction.getType().equals(TypeOfTransaction.CASH)){
                wallet.setCash(wallet.getCash() + transaction.getAmount());
            }
            else {
                Card card = transaction.getCard();
                card.setAmount(card.getAmount() + transaction.getAmount());
                cardRepo.save(card);
            }
        }
        else {
            if(transaction.getType().equals(TypeOfTransaction.CASH)){
                wallet.setCash(wallet.getCash() - transaction.getAmount());
            }
            else {
                Card card = transaction.getCard();
                card.setAmount(card.getAmount() - transaction.getAmount());
                cardRepo.save(card);
            }
        }
        wallet.setTotal(wallet.getCash() + wallet.getCreadit());
        walletRepo.save(wallet);
        transactionRespo.deleteById(id);
        return true;
    }
}
