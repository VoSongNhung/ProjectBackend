package com.example.SpendingMana.payload.Request;

import com.example.SpendingMana.entity.TypeOfTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class TransactionRequest {
        private LocalDate date;
        private String note;
        private Integer amount;
        private TypeOfTransaction type;
        private Long categoryId;
        private Long walletId;
        private Long cardId;
    }
