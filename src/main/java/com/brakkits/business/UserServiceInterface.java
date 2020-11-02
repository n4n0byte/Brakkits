package com.brakkits.business;

import com.brakkits.models.User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/25/2020
 * Interface for the event business services
 **/
public interface UserServiceInterface
{
    /**
     *
     * @param user User
     * @param newUsername Username
     */
    void updateUsername(User user, String newUsername);

    User findUserWithEmail(String email);

    /**
     * Stores user info
     * @param token
     */
    void storeUser(JwtAuthenticationToken token);
}
