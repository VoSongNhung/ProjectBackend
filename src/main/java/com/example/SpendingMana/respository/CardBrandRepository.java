package com.example.SpendingMana.respository;

import com.example.SpendingMana.entity.CardBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBrandRepository extends JpaRepository<CardBrand,Long> {
}
