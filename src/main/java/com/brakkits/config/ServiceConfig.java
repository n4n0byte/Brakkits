package com.brakkits.config;

import com.brakkits.business.EventBusinessService;
import com.brakkits.business.EventBusinessServiceInterface;
import com.brakkits.business.UserService;
import com.brakkits.business.UserServiceInterface;
import com.brakkits.data.*;
import com.brakkits.models.Bracket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 11/1/2020
 * This class handles the IoC for all of the relevant Services
 * & DAOs
 **/
@Configuration
public class ServiceConfig {

    ///////////////////////////////
    // DAOs
    //////////////////////////////
    /**
     * DAO IoC config
     * @return BracketDAO
     */
    @Bean
    @Primary
    public DAOInterface<Bracket> bracketDAOInterface(){
        return new BracketDAO();
    }


    ///////////////////////////////
    // Business Services
    //////////////////////////////

    /**
     * injects business service
     * @return EventBusinessService
     */
    @Bean
    @Primary
    EventBusinessServiceInterface eventBusinessServiceInterface() {
        System.out.println("INJECTING Business Service");
        return new EventBusinessService();
    }

    @Bean
    @Primary
    UserServiceInterface userServiceInterface(){
        System.out.println("injecting user interface");
        return new UserService();
    }



}
