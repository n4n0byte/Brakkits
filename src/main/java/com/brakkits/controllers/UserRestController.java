package com.brakkits.controllers;

import com.brakkits.business.UserService;
import com.brakkits.business.UserServiceInterface;
import com.brakkits.data.DTO;
import com.brakkits.data.UserRepository;
import com.brakkits.models.User;
import com.brakkits.util.GenericException;
import com.brakkits.util.RetrieveJWTValues;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 *
 * This persists the Open ID Connect token recieved from the front-end
 **/
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserRestController {

    private Client client = Clients.builder()
            .setOrgUrl("https://dev-436597.okta.com")
            .setClientCredentials(new TokenClientCredentials("00ICLr2QCg5anviwIw8GkUNmZiq9PwyorgZUvkEPk-"))
            .build();


    @Autowired
    UserServiceInterface userService;

    @PostMapping("/updateUsername/{username}")
    public DTO<String> updateUsername(JwtAuthenticationToken token, @PathVariable String username) {
        userService.updateUsername(RetrieveJWTValues.makeUser(token), username);
        return new DTO<>("success");
    }

    /**
     * inserts user info into db
     *
     * @param token JWTToken
     * @return DTO<String>
     */
    @PostMapping("/storeUser")
    public DTO<String> storeUserInfo(JwtAuthenticationToken token) {
        userService.storeUser(token);
        return new DTO<>("success");
    }
}

