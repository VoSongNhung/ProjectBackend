package com.example.SpendingMana.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeId;
    private LocalDate dateTime;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "income_id")
    private List<Transaction> transactions;
}
