package com.brakkits.business;

import com.brakkits.models.Tournament;
import com.brakkits.models.User;
import com.brakkits.models.UserTournamentPrivilege;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 11/1/2020
 * Interface for the event business services
 **/
public interface EventBusinessServiceInterface {

    Tournament findTournament(String title);

    /**
     * Delete
     * @param user User
     * @param title String
     * @return String
     */
    boolean deleteEvent(User user, String title);

    /**
     * Updates an event
     * @param user User
     * @param image File
     * @param oldTitle String
     * @param title String
     * @param description String
     * @param gameTitle String
     * @return boolean
     */
    boolean updateEvent(User user, MultipartFile image, String oldTitle, String title, String description, String gameTitle);

    /**
     * Creates and event
     * @param user User
     * @param image MultipartFile
     * @param title String
     * @param description String
     * @param selectedStartDate Date
     * @param capacity Integer
     * @param gameTitle String
     */
    void createEvent(User user, MultipartFile image, String title, String description, Date selectedStartDate, Integer capacity, String gameTitle);

    void addEntrant(User entrant, String eventName);

    /**
     * finds all Tournaments, returns an iterable of them
     * @return Iterable<Tournament>
     */
    Iterable<Tournament> findAllEvents();

    /**
     * looks for all tournaments with the same name
     * @param name String
     * @return List<Tournament>
     */
    List<Tournament> searchTournaments(String name);

    /**
     * checks privileges of a user
     * @param token jwtToken
     * @param eventName event Name
     * @return UserTournamentPrivilege
     */
    UserTournamentPrivilege privilegeCheck(String eventName, JwtAuthenticationToken token);
}
