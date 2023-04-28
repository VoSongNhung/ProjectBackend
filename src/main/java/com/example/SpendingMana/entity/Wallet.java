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
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;
    private Integer total;
    private String icon;
    private Integer cash;
    private Integer creadit;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @OneToMany(mappedBy = "wallet",cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    private List<Card> cardList;

    @OneToMany(mappedBy = "wallet", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    private List<Transaction> transactionList;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "keycloak_id")
    private User user;
}
