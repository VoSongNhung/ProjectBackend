package com.example.SpendingMana.payload.Request;

import com.example.SpendingMana.entity.CardBrand;
import com.example.SpendingMana.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
    private String cardNumber;
    private Integer amount;
    private Long cardBrandId;
    private Long walletId;
}
