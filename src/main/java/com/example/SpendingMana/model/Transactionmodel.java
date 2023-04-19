package com.example.SpendingMana.model;

import com.example.SpendingMana.entity.TypeOfTransaction;
import com.example.SpendingMana.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactionmodel {
    private TypeOfTransaction type;
    private Integer amount;
    private Wallet wallet;
}
