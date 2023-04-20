package com.example.SpendingMana.dto;

import com.example.SpendingMana.entity.CardBrand;
import com.example.SpendingMana.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private String cardNumber;
    private Integer amount;
    private Long cardBrandId;
    private Long walletId;
}
