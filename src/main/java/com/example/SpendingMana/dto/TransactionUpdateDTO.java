package com.example.SpendingMana.dto;

import com.example.SpendingMana.entity.TypeOfTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionUpdateDTO {
    private String note;
    private Integer amount;
    private Long categoryId;
    private Long cardId;
}
