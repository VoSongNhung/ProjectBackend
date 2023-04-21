package com.example.SpendingMana.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long keycloakId;

    private String firstname;
    private String lastname;
    private  String username;
    private String password;

    @OneToOne(mappedBy ="user")
    @JsonIgnore
    private Wallet wallet;
}
