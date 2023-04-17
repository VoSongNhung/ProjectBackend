package com.example.SpendingMana.payload.Request;

import com.example.SpendingMana.entity.Currency;
import com.example.SpendingMana.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequest {
    private String icon;
    private Integer cash;
    private Integer creadit;
    private Integer total;
    private Long currencyId;
    private Long keycloakId;
}
