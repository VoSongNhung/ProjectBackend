package com.example.SpendingMana.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currency_id;
    private String nameCurrency;

    private String symbol;
    private String icon;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<Wallet> wallets;
}
