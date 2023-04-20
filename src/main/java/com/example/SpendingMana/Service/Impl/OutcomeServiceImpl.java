package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Service.OutcomeService;
import com.example.SpendingMana.entity.Outcome;
import com.example.SpendingMana.respository.OutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OutcomeServiceImpl implements OutcomeService {
    @Autowired
    OutcomeRepository outcomeRepo;

    @Override
    public Page<Outcome> getAllOutcome(Pageable pageable) {
        return outcomeRepo.findAll(pageable);
    }
}
