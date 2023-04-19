package com.example.SpendingMana.respository;

import com.example.SpendingMana.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {
}
