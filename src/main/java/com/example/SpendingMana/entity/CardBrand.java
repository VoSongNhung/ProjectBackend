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
@Table(name = "card_brand")
public class CardBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String nameBrand;
    private String icon;
    @OneToMany(mappedBy = "cardBrand", cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<Card> cards;
}
