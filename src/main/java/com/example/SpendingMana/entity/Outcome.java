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
@Table(name = "outcome")
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outcomeId;
    private LocalDate dateTime;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "outcome_id")
    private List<Transaction> transactions;
}
