package com.example.SpendingMana.respository;

import com.example.SpendingMana.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome,Long> {
}
