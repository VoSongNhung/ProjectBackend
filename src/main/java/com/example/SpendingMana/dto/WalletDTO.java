package com.example.SpendingMana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {
    private String icon;
    private Integer cash;
    private Long currencyId;
    private Long userId;
}
