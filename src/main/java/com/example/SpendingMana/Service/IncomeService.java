package com.example.SpendingMana.Service;

import com.example.SpendingMana.entity.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IncomeService {
    public Page<Income> getAllIncome(Pageable pageable);
}
