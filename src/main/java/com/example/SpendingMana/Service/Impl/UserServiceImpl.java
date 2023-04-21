package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Constants.FileConstants;
import com.example.SpendingMana.Service.UserService;
import com.example.SpendingMana.model.UserModel;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;
    private static final String authServerUrl = FileConstants.url;
    private static final String realm = FileConstants.realm;
    private static final String clientId = FileConstants.resource;
    private static final String clientSecret = FileConstants.credentialssecret;
    private static final String userName = FileConstants.username;
    private static final String passWord = FileConstants.password;


//    @Value("${keycloak.auth-server-url}")
//    private String authServerUrl;

//    @Value("${keycloak.realm}")
//    private String realm;
//
//    @Value("${keycloak.resource}")
//    private String clientId;
//
//    @Value("${keycloak.credentials.secret}")
//    private String clientSecret;
//
//    @Value("${keycloak.username}")
//    private String userName;
//
//    @Value("${keycloak.password}")
//    private String passWord;

    private Keycloak getKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(userName)
                .password(passWord)
//                .authorization("authorization")
                .build();
    }

    @Override
    public List<UserModel> getUsers() {
        Keycloak keycloak = getKeycloak();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> users = usersResource.list();
        List<UserModel> userModels = new ArrayList<>();
        for (UserRepresentation use : users){
            UserModel userModel = modelMapper.map(use, UserModel.class);
            userModels.add(userModel);
        }
        return  userModels;
    }

    @Override
    public UserModel getUserById(String id) {
        Keycloak keycloak = getKeycloak();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserRepresentation user = usersResource.get(id).toRepresentation();
        UserModel userModel = modelMapper.map(user, UserModel.class);
        return userModel;
    }

    @Override
    public UserRepresentation createUser(UserRepresentation userRepresentation) {
        Keycloak keycloak = getKeycloak();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(userRepresentation);
        URI uri = response.getLocation();
        String userId = uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1);
        return usersResource.get(userId).toRepresentation();
    }

    @Override
    public UserRepresentation updateUser(String id, UserRepresentation userRepresentation) {
        Keycloak keycloak = getKeycloak();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        usersResource.get(id).update(userRepresentation);
        return usersResource.get(id).toRepresentation();
    }

    @Override
    public void deleteUser(String id) {
        Keycloak keycloak = getKeycloak();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        usersResource.get(id).remove();
    }
}
