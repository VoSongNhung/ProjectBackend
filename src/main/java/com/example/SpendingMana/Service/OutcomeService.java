package com.example.SpendingMana.Service;

import com.example.SpendingMana.entity.Outcome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OutcomeService {
    public Page<Outcome> getAllOutcome(Pageable pageable);
}
