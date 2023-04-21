package com.example.SpendingMana.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    public String id;
    public String username;
    public String firstName;
    public String lastName;
    public String email;
    public String enabled;
    public String emailVerified;
}