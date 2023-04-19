package com.example.SpendingMana.dto;

import com.example.SpendingMana.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private String nameCategory;

    private String icon;
    private Type type;
}
