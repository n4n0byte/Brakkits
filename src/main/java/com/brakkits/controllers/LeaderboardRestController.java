package com.brakkits.controllers;

import com.brakkits.data.DTO;
import com.brakkits.data.TournamentRepository;
import com.brakkits.data.UserRepository;
import com.brakkits.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 11/1/2020
 *
 * Controller for Leaderbord operations
 **/
@RestController
@CrossOrigin
public class LeaderboardRestController {

    @Autowired
    private UserRepository userRepository;

    /**
     * get top 500 ranked players
     * @param token JWTToken
     * @return DTO with list of Users
     */
    @GetMapping("/leaderboard")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DTO< List<User> > leaderboardStats(JwtAuthenticationToken token){
        return new DTO<>(userRepository.findTop500ByOrderByPlacementAsc());
    }

}
