package com.brakkits.controllers;

import com.brakkits.business.EventBusinessServiceInterface;
import com.brakkits.data.DTO;
import com.brakkits.data.TournamentRepository;
import com.brakkits.data.UserRepository;
import com.brakkits.models.Tournament;
import com.brakkits.models.User;
import com.brakkits.models.UserTournamentPrivilege;
import com.brakkits.util.DataNotFoundException;
import com.brakkits.util.RetrieveJWTValues;
import com.brakkits.util.ValidationException;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 11/1/2020
 *
 * Controller for Event related control, logic & other operations
 **/
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://salty-inlet-26141.herokuapp.com:443"})

public class EventRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    EventBusinessServiceInterface eventService;

    /**
     * checks privileges of a user
     * @param token jwtToken
     * @param eventName event Name
     * @return DTO<UserTournamentPrivilege>
     */
    @GetMapping("/checkEventPrivilege/{eventName}")
    public DTO<UserTournamentPrivilege> privilegeCheck(JwtAuthenticationToken token, @PathVariable("eventName") String eventName){
        return new DTO<>(eventService.privilegeCheck(eventName,token));
}

    /**
     * attempts to delete an event
     */
    @GetMapping("/deleteEvent/{eventName}")
    public DTO<Boolean> deleteEvent(JwtAuthenticationToken token, @PathVariable("eventName") String eventName){
        User usr = RetrieveJWTValues.makeUser(token);
        return new DTO<>(eventService.deleteEvent(usr, eventName));
    }



    /**
     * Join Event
     */
    @GetMapping("/joinEvent/{eventName}")
    public DTO<Tournament> addEntrant(JwtAuthenticationToken token, @PathVariable("eventName") String eventName){
        eventService.addEntrant(RetrieveJWTValues.makeUser(token), eventName);
        return new DTO<>(eventService.findTournament(eventName));
    }

    /**
     * @param token JwtToken
     * @param maxAmount amount
     * @return
     */
    @GetMapping("/getTopEvents/{maxAmount}")
    public DTO<List<Tournament>> getTopEvents(JwtAuthenticationToken token, @PathVariable("maxAmount") int maxAmount) {
        Pageable max = PageRequest.of(0, maxAmount);
        return new DTO<List<Tournament>>(tournamentRepository.findAll(max));
    }

    /**
     * return tourney DTO
     * @param token JwtAuthenticationToken
     * @param name name
     * @return DTO<Tournament>
     */
    @GetMapping("/findEventByName")
    public DTO<Tournament> findEventListing(JwtAuthenticationToken token, @RequestParam(name = "eventName") String name) {
        return new DTO<Tournament>(eventService.findTournament(name));
    }

    /**
     * @param token JwtAuthenticationToken
     * @param name eventName
     * @return  DTO<List<Tournament>>
     */
    @GetMapping("/findEvents")
    public DTO<List<Tournament>> findEvents(@RequestParam(name = "eventName") String name) {
        return new DTO<List<Tournament>>(eventService.searchTournaments(name));
    }


    /**
     *
     * @param token JWT Token
     * @param name String
     * @return dtp string list
     */
    @GetMapping("/getStageList")
    public DTO<List<String>> stageList(JwtAuthenticationToken token, @RequestParam(name = "eventName") String name) {
        Tournament t = tournamentRepository.findTournamentByTitle(name).orElse(null);
        return new DTO<>(t.getStageList());
    }

    /**
     * Updates an event
     * @param token Token
     * @param image Image
     * @param title String
     * @param oldTitle String
     * @param description String
     * @param gameTitle String
     * @return bool dto
     */
    @PostMapping(value = "/updateEvent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DTO<Boolean> update(JwtAuthenticationToken token, @RequestParam MultipartFile image,
                                   @RequestParam String title, @RequestParam String oldTitle, @RequestParam String description,
                                   @RequestParam String gameTitle) {
        User tmp = RetrieveJWTValues.makeUser(token);
        return new DTO<Boolean>(eventService.updateEvent(tmp , image, oldTitle, title, description, gameTitle));
    }

    /**
     * finds events created by a user
     *
     * @param token JwtAuthenticationToken
     * @return DTO<List<Tournament>>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @GetMapping("/findEventsCreated")
    public DTO<List<Tournament>> findEventsCreated(JwtAuthenticationToken token) {

        User jwtUser = RetrieveJWTValues.makeUser(token);
        User user = userRepository.findUserByEmail(jwtUser.getEmail()).get();
        return new DTO<>(tournamentRepository.findTournamentsByOwner(user));

    }

    /**
     * finds all events attended
     * @param token JwtToken
     * @return DTO<List<Tournament>>
     */
    @GetMapping("/findEventsAttended")
    public DTO<List<Tournament>> findAttendedEvents(JwtAuthenticationToken token) {
        // TODO: Link to attendies through brackets
        User jwtUser = RetrieveJWTValues.makeUser(token);
        User user = userRepository.findUserByEmail(jwtUser.getEmail()).get();
        return new DTO<>(tournamentRepository.findTournamentsByOwner(user));
    }

    /**
     * creates an event
     * @param token String
     * @param image MultipartFile image
     * @param title String
     * @param description String
     * @param gameTitle String
     * @param selectedStartDate Date
     * @param capacity int
     * @return DTO<String> res
     */
    @PostMapping(value = "/createEvent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DTO<String> createEvent(JwtAuthenticationToken token, @RequestParam MultipartFile image,
                                   @RequestParam String title, @RequestParam String description,
                                   @RequestParam String gameTitle,
                                   @RequestParam Date selectedStartDate, @RequestParam Integer capacity) {

        // parse jwt and create user
        User user = RetrieveJWTValues.makeUser(token);

        // create event
        eventService.createEvent(user, image, title, description, selectedStartDate, capacity, gameTitle);

        return new DTO<>("success");
    }

}
