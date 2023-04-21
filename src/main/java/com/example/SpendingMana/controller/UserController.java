package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.UserService;
import com.example.SpendingMana.model.UserModel;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public List<UserModel> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public UserModel getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public UserRepresentation createUser(@RequestBody UserRepresentation userRepresentation) {
        return userService.createUser(userRepresentation);
    }

    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public UserRepresentation updateUser(@PathVariable String id, @RequestBody UserRepresentation userRepresentation) {
        return userService.updateUser(id, userRepresentation);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}

