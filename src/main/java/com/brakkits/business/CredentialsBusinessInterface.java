package com.brakkits.business;

import com.brakkits.models.User;

import java.util.Optional;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 11/1/2020
 * Interface specification for the credentials business service
 **/
public interface CredentialsBusinessInterface {

    /**
     * calls login orchestration methods and returns
     * a user model with the respective isRegistered
     * and hasValidCredentials
     * @param user User
     * @return User user
     */
    public User loginFacade(User user);

    /**
     * calls Registration orchestration methods and returns
     * a user model with the respective isRegistered  bools
     * and hasValidCredentials
     * @param user User
     * @return User user
     */
    public User registrationFacade(User user);

}
