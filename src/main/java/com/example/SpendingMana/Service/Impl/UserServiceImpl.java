package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Constants.FileConstants;
import com.example.SpendingMana.Service.UserService;
import com.example.SpendingMana.entity.User;
import com.example.SpendingMana.error.DataNotFoundException;
import com.example.SpendingMana.model.UserModel;
import com.example.SpendingMana.respository.UserRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    private static final String authServerUrl = FileConstants.url;
    private static final String realm = FileConstants.realm;
    private static final String clientId = FileConstants.resource;
    private static final String clientSecret = FileConstants.credentialssecret;
    private static final String userName = FileConstants.username;
    private static final String passWord = FileConstants.password;
   //sử dụng lớp KeycloakBuilder để tạo ra một đối tượng Keycloak mới và khởi tạo nó với các thông tin đã được cung cấp
    private Keycloak getKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(userName)
                .password(passWord)
                .build();
    }

    @Override
    public List<UserModel> getUsers() {
        //goi mehod getkeycloak để lấy đối tượng keycloak sử dụng cho quản lý xác thực
        Keycloak keycloak = getKeycloak();
        //lấy tài nguyên RealmResource. RealmResource được sử dụng để truy cập vào các thông tin liên quan đến realm.
        RealmResource realmResource = keycloak.realm(realm);
        //lấy tài nguyên UsersResource. UsersResource được sử dụng để truy cập vào thông tin các người dùng trong realm.
        UsersResource usersResource = realmResource.users();
        //lấy danh sách các đại diện người dùng
        List<UserRepresentation> users = usersResource.list();
        List<UserModel> userModels = new ArrayList<>();
        for (UserRepresentation user : users) {
            UserModel userModel = modelMapper.map(user, UserModel.class);
            //lấy danh sách các đại diện vai trò (RoleRepresentation) của người dùng hiện tại
            List<RoleRepresentation> roleRepresentations = usersResource.get(user.getId()).roles().realmLevel().listAll();
            List<String> roles = new ArrayList<>();
            for (RoleRepresentation roleRepresentation : roleRepresentations) {
                roles.add(roleRepresentation.getName());
            }
            userModel.setRoles(roles.toString());
            userModels.add(userModel);
        }
        return userModels;
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
    public UserRepresentation createUserWithRole(UserRepresentation userRepresentation, String roleName) {
        Keycloak keycloak = getKeycloak();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        //kích hoạt đối tượng userRepresentation
        userRepresentation.setEnabled(true);
        // gọi mehthod create để tạo người dùng mới
        Response response = usersResource.create(userRepresentation);
        //method create trả về đối tượng response
        URI uri = response.getLocation();
        String userId = uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1);
        RoleRepresentation role =
                realmResource.roles().get(roleName).toRepresentation();
        List<RoleRepresentation> roles = new ArrayList<>();
        roles.add(role);
        realmResource.users().get(userId).roles().realmLevel().add(roles);



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
//    public UserRepresentation createUser(UserRepresentation userRepresentation) {
//        Keycloak keycloak = getKeycloak();
//        RealmResource realmResource = keycloak.realm(realm);
//        UsersResource usersResource = realmResource.users();
//
//        // Check if username or email already exists
//        List<UserRepresentation> users = usersResource.search(userRepresentation.getUsername(), true);
//        if (users != null && !users.isEmpty()) {
//            new DataNotFoundException("user name exist");
//        }
//        users = usersResource.search(userRepresentation.getEmail(), true);
//        if (users != null && !users.isEmpty()) {
//            new DataNotFoundException("email exist");
//        }
//
//        // Create user if everything is okay
//        userRepresentation.setEnabled(true);
//        Response response = usersResource.create(userRepresentation);
//        URI uri = response.getLocation();
//        String userId = uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1);
//        return usersResource.get(userId).toRepresentation();
//    }

}
