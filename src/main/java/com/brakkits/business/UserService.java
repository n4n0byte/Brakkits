package com.brakkits.business;

import com.brakkits.data.DTO;
import com.brakkits.data.UserRepository;
import com.brakkits.models.User;
import com.brakkits.util.DataNotFoundException;
import com.brakkits.util.GenericException;
import com.brakkits.util.InvalidDataException;
import com.brakkits.util.RetrieveJWTValues;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/25/2020
 * event business services
 **/
public class UserService implements UserServiceInterface {
    private Client client = Clients.builder()
            .setOrgUrl("https://dev-436597.okta.com")
            .setClientCredentials(new TokenClientCredentials("00ICLr2QCg5anviwIw8GkUNmZiq9PwyorgZUvkEPk-"))
            .build();

    @Autowired
    private UserRepository userRepository;


    /**
     *
     * @param user User
     * @param newUsername Username
     */
    @Override
    public void updateUsername(User user, String newUsername){

        // contact db to fill out any mising info from token
        // will throw data not found exception if nothing is found
        User tmp = userRepository.findUserByEmail(user.getEmail()).orElseThrow(DataNotFoundException::new);

        // find user by email
        UserList users = client.listUsers(tmp.getEmail(), null, null, null, null);

        // update user info server side
        try {
            users.stream().forEach(usr -> {
                usr.getProfile().put("userTag", newUsername);
            });
        } catch (Exception e){
            throw new GenericException(e);
        }

        // update user info client side
        tmp.setTag(newUsername);
        userRepository.save(tmp);

    }

    /**
     * returns user by email
     * @param email String
     * @return User
     */
    @Override
    public User findUserWithEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(DataNotFoundException::new);
    }

    /**
     * Stores user info
     * @param token JwtToken
     */
    @Override
    public void storeUser(JwtAuthenticationToken token){
        // stores user model generated from jwt
        User user = RetrieveJWTValues.makeUser(token);
        if (!userRepository.existsUserByEmail(user.getEmail())){
            userRepository.save(user);
        }

    }

}
