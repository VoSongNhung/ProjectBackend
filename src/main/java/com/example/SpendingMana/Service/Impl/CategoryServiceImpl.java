package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Service.CategoryService;
import com.example.SpendingMana.dto.CategoryDTO;
import com.example.SpendingMana.entity.*;
import com.example.SpendingMana.error.DataNotFoundException;
import com.example.SpendingMana.model.Transactionmodel;
import com.example.SpendingMana.respository.CardRepository;
import com.example.SpendingMana.respository.CategoryRepository;
import com.example.SpendingMana.respository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    WalletRepository walletRepo;
    @Autowired
    CardRepository cardRepo;
    @Override
    public Page<Category> getAllCategory(Pageable pageable) {
        return categoryRepo.findAll(pageable);
    }

    @Override
    public Category addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setNameCategory(categoryDTO.getNameCategory());
        category.setIcon(categoryDTO.getIcon());
        category.setType(categoryDTO.getType());
        return categoryRepo.save(category);
    }
    @Override
    public boolean deleteCategory(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new DataNotFoundException("category not found"));
        for (Transaction transaction : category.getTransactions()){
            Transactionmodel transactionmodel = modelMapper.map(transaction, Transactionmodel.class);
            Wallet wallet = transactionmodel.getWallet();
            if(transaction.getCategory().getType().equals(Type.EXPENSE)){
                if(transactionmodel.getType().equals(TypeOfTransaction.CASH)){
                    wallet.setCash(wallet.getCash() + transactionmodel.getAmount());
                }
                else {
                    Card card = transaction.getCard();
                    wallet.setCreadit(wallet.getCreadit() + transactionmodel.getAmount());
                    card.setAmount(card.getAmount() + transactionmodel.getAmount());
                    cardRepo.save(card);
                }
                wallet.setTotal(wallet.getCash() + wallet.getCreadit());
                walletRepo.save(wallet);
            }
            else {
                if(transactionmodel.getType().equals(TypeOfTransaction.CASH)){
                    wallet.setCash(wallet.getCash() - transactionmodel.getAmount());
                }
                else {
                    Card card = transaction.getCard();
                    wallet.setCreadit(wallet.getCreadit() - transactionmodel.getAmount());
                    card.setAmount(card.getAmount() + transactionmodel.getAmount());
                    cardRepo.save(card);
                }
                wallet.setTotal(wallet.getCash() + wallet.getCreadit());
                walletRepo.save(wallet);
            }
        }
        categoryRepo.deleteById(id);
        return true;
    }
}
