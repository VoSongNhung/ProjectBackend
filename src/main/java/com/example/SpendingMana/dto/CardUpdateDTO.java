package com.example.SpendingMana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardUpdateDTO {
    private String cardNumber;
    private Integer amount;
    private Long cardBrandId;
}
