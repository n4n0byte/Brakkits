package com.brakkits.controllers;

import com.brakkits.data.DTO;
import com.brakkits.data.UserRepository;
import com.brakkits.models.User;
import com.brakkits.util.RetrieveJWTValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @Autowired
    private UserRepository userRepository;

    /**
     * inserts user info into db
     * @param token JWTToken
     * @return DTO<String>
     */
    @PostMapping("/storeUser")
    public DTO<String> storeUserInfo(JwtAuthenticationToken token){

        // stores user model generated from jwt
        User user = RetrieveJWTValues.makeUser(token);
            if (!userRepository.existsUserByEmail(user.getEmail())){
                userRepository.save(user);
                return new DTO<>("success");
            } else{
                System.out.println(userRepository.findUserByEmail(user.getEmail()));
                return new DTO<>("already registered");
            }
        }
    }

