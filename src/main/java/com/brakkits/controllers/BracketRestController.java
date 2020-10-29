package com.brakkits.controllers;

import com.brakkits.data.DTO;
import com.brakkits.data.TournamentRepository;
import com.brakkits.models.Attendee;
import com.brakkits.models.Tournament;
import com.brakkits.models.User;
import com.brakkits.models.UserTournamentPrivilege;
import com.brakkits.util.RetrieveJWTValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 *
 * Controller for bracket related control, logic & other operations
 **/
@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class BracketRestController {

    @Autowired
    private TournamentRepository tournamentRepository;

    /**
     * Attempts to delete an event
     * @param token JwtToken
     * @param eventName String
     * @return DTO<Boolean> bool
     */
    @DeleteMapping("/deleteEvent/{eventName}")
    public DTO<Boolean> deleteEvent(JwtAuthenticationToken token, @PathVariable("eventName") String eventName) {
        DTO<Boolean> res = new DTO<>();
        User user = RetrieveJWTValues.makeUser(token);
        Optional<Tournament> tournament = tournamentRepository.findTournamentByTitle(eventName);

        // check for presence of tourney and if tournament owner email matches
        if (!tournament.isPresent()) {
            res.setMessage("not found");
            res.setStatusCode(404);
            res.setData(false);
        } else if(tournament.get().getOwner().getEmail().equals(user.getEmail())) {
            res.setData(true);
            tournamentRepository.delete(tournament.get());
        } else {
            res.setMessage("invalid credentials");
            res.setStatusCode(401);
            res.setData(false);
        }

        return res;
    }




    /**
     * checks tourney status
     * @param eventName String
     * @return bool dto
     */
    @GetMapping("/checkEventStatus/{eventName}")
    public DTO<Boolean> checkStatus(@PathVariable("eventName") String eventName){

        Optional<Tournament> tournament = tournamentRepository.findTournamentByTitle(eventName);
        DTO<Boolean> res = new DTO<>();

        // existence check
        if (tournament.isPresent()){
            Date tDate = tournament.get().getStartDate();
            Date date = new Date();
            // check to see if current date is after start date
            res.setData(date.compareTo(tDate) > 0);
        } else{
            // will throw once global exception handling is done
            res.setStatusCode(404);
            res.setMessage("Not Found");
            res.setData(false);
        }
        return res;
    }

    /**
     * Mock Send Bracket data
     * @param eventName String
     * @return List<List< List<BracketUser> >>
     */
    @GetMapping("/getEventBracket/{eventName}")
    public DTO< List<List< List<User> >> > getBracket(@PathVariable("eventName") String eventName){

        DTO< List<List< List<User> >> > bracket = new DTO<>();

        // create pair
        List<User> pair = new ArrayList<>();
        pair.add(new User());
        pair.add(new User());

        // fill round with pairs
        List< List<User> > round = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            round.add(pair);
        }

        // fill bracket with rounds
        List< List< List<User> >> roundList = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            roundList.add(round);
        }
        bracket.setMessage("success");
        bracket.setStatusCode(200);
        bracket.setData(roundList);

        return bracket;
    }

}
