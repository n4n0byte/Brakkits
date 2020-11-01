package com.brakkits.controllers;

import com.brakkits.business.EventBusinessServiceInterface;
import com.brakkits.data.DTO;
import com.brakkits.data.TournamentRepository;
import com.brakkits.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
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

    @Autowired
    private EventBusinessServiceInterface eventBusinessServiceInterface;


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
    public DTO< Tournament > getBracket(@PathVariable("eventName") String eventName){

        DTO< Tournament > bracket = new DTO<>();
        Tournament t = eventBusinessServiceInterface.findTournament(eventName);
        bracket.setData(t);
        return bracket;
    }

}
