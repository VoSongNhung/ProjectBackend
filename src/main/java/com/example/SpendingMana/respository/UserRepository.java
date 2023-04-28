package com.example.SpendingMana.respository;

import com.example.SpendingMana.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
