package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.UserService;
import com.example.SpendingMana.entity.User;
import com.example.SpendingMana.model.UserModel;
import com.example.SpendingMana.respository.UserRepository;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    UserRepository userRepository;
    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserModel> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<UserModel> getUserById(@PathVariable String id) {
        UserModel user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<UserRepresentation> createUserWithRole(@RequestBody UserRepresentation userRepresentation, String roleName) {
        UserRepresentation userrep = userService.createUserWithRole(userRepresentation,"ROLE_USER");
        User user = new User();
        user.setKeycloakId(userrep.getId());
        user.setFirstname(userRepresentation.getFirstName());
        user.setLastname(userRepresentation.getLastName());
        user.setUsername(userRepresentation.getUsername());
        user.setEmail(userRepresentation.getEmail());
        user.setRoles("USER_ROLE");
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userrep);
    }
    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<UserRepresentation> updateUser(@PathVariable String id, @RequestBody UserRepresentation userRepresentation) {
        UserRepresentation updatedUser = userService.updateUser(id, userRepresentation);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}

