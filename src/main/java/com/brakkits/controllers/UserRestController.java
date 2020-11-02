package com.brakkits.controllers;

import com.brakkits.business.UserService;
import com.brakkits.business.UserServiceInterface;
import com.brakkits.data.DTO;
import com.brakkits.data.UserRepository;
import com.brakkits.models.User;
import com.brakkits.util.GenericException;
import com.brakkits.util.RetrieveJWTValues;
import com.google.common.math.IntMath;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 11/1/2020
 *
 * This persists the Open ID Connect token recieved from the front-end
 **/
@RestController
@CrossOrigin
public class UserRestController {

    @Autowired
    UserServiceInterface userService;


    /**
     * Updates a username
     * @param token Token
     * @param username Username
     * @return result dto
     */
    @PostMapping(value = "/updateUsername", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DTO<String> updateUsername(JwtAuthenticationToken token, @RequestParam String username) {
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

    @GetMapping("/getUsername")
    public DTO<String> getUsername(JwtAuthenticationToken token){
        User usr = RetrieveJWTValues.makeUser(token);

        return new DTO<>(userService.findUserWithEmail(usr.getEmail()).getTag());
    }

}

