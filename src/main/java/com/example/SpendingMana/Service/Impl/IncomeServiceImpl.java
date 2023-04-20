package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Service.IncomeService;
import com.example.SpendingMana.entity.Income;
import com.example.SpendingMana.respository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    IncomeRepository incomeRepo;
    @Override
    public Page<Income> getAllIncome(Pageable pageable) {
        return incomeRepo.findAll(pageable);
    }
}
