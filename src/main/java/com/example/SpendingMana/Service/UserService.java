package com.example.SpendingMana.Service;

import com.example.SpendingMana.model.UserModel;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface UserService {
    List<UserModel> getUsers();
    UserModel getUserById(String id);
    UserRepresentation createUser(UserRepresentation userRepresentation);
    UserRepresentation updateUser(String id, UserRepresentation userRepresentation);
    void deleteUser(String id);
}