package com.brakkits.business;

import com.brakkits.data.TournamentRepository;
import com.brakkits.data.UserRepository;
import com.brakkits.models.Attendee;
import com.brakkits.models.Tournament;
import com.brakkits.models.User;

import com.brakkits.models.UserTournamentPrivilege;
import com.brakkits.util.DataNotFoundException;
import com.brakkits.util.RetrieveJWTValues;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 *
 * Business Layer Service Impl. for handling events
 **/
public class EventBusinessService implements EventBusinessServiceInterface {

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    UserRepository userRepository;

    private static String imgLocation = "C:\\Users\\coopn\\Desktop\\BrakkitsRoot\\brakkits-backend\\src\\main\\resources\\static\\images\\";

    /**
     * switch on the selected games to get stagelists
     * @param gameName Stirng
     * @return List of String
     */
    private List<String> getStageList(String gameName){

        switch (gameName){
            case "Melee":
                return Arrays.asList("Dreamland","Final Destination","Fountain of Dreams");
            case "Smash 4":
                return Arrays.asList("Dreamland","Final Destination","Fountain of Dreams", "Yoshi's Story");
            case "Smash Ultimate":
                return Arrays.asList("Dreamland","Final Destination","Fountain of Dreams", "Battlefield");
            default:
                return Arrays.asList("Dreamland","Halberd","Fountain of Dreams");
        }


    }

    /**
     * Updates an event, throws in failure case
     * @param user User
     * @param image File
     * @param title String
     * @param description String
     * @return boolean
     */
    @Override
    public boolean updateEvent(User user, MultipartFile image, String oldTitle, String title, String description, String gameTitle){

        Optional<Tournament> t = tournamentRepository.findTournamentByTitle(oldTitle);

        if (t.isEmpty()){
            throw new DataNotFoundException();
        }


        return false;
    }

    /**
     * creates an event
     * @param user User
     * @param image File
     * @param title String
     * @param description String
     * @param selectedStartDate Date
     * @param capacity Integer
     */
    @Override
    public void createEvent(User user, MultipartFile image, String title, String description, Date selectedStartDate, Integer capacity, String gameTitle) {
        String modifiedImgName = null;
        Path path = null;

        if (image != null){
            // encode image as base64
            byte[] encodedUrl = Base64.encodeBase64(new String(new Date() + user.getTag() + image.getOriginalFilename()).getBytes());
            modifiedImgName = new String(encodedUrl) + ".jpg";

            File f = new File(imgLocation + modifiedImgName);

            path = Paths.get(imgLocation + modifiedImgName);
            // save image
            try {
                f.createNewFile();
                FileOutputStream fout = new FileOutputStream(f);
                fout.write(image.getBytes());
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        User tmp = userRepository.findUserByEmail(user.getEmail()).get();

        // build a tourney
        Tournament t = Tournament.builder()
                .owner(tmp)
                .capacity(capacity)
                .description(description)
                .stageList(getStageList(gameTitle))
                .gameTitle(gameTitle)
                .title(title)
                .startDate(selectedStartDate)
                .points(capacity*2)
                .isActive(false)
                .imgUrl(modifiedImgName == null ? "http://localhost:8080/images/smash.jpg" : "http://localhost:8080/images/" + modifiedImgName)
            .build();

        tournamentRepository.save(t);

    }

    /**
     * returns all events
     * @return iterable tournament
     */
    @Override
    public Iterable<Tournament> findAllEvents(){
        return tournamentRepository.findAll();
    }

    /**
     *
     * @param name String
     * @return List of Tournaments
     */
    @Override
    public List<Tournament> searchTournaments(String name){
        return tournamentRepository.findTournamentsByTitleContaining(name);
    }

    /**
     * checks privileges of a user
     * @param token jwtToken
     * @param eventName event Name
     * @return UserTournamentPrivilege
     */
    @Override
    public UserTournamentPrivilege privilegeCheck(String eventName, JwtAuthenticationToken token){
        UserTournamentPrivilege userTournamentPrivilege = new UserTournamentPrivilege();

        Optional<Tournament> tournament = tournamentRepository.findTournamentByTitle(eventName);

        // find out if the requester is the owner and set perms accordingly
        if (tournament.isPresent()){
            User owner = tournament.get().getOwner();
            User requester = RetrieveJWTValues.makeUser(token);
            userTournamentPrivilege.setOwner(requester.getEmail().equals(owner.getEmail()));

            // filter attendees for proper one
            Optional<Attendee> attender = tournament.get().getAttendees().stream()
                    .filter((attendee)->{return attendee.getEmail().equals(requester.getEmail());})
                    .findFirst();

            userTournamentPrivilege.setEnteredIntoTournament(attender.isPresent());

        }

        return userTournamentPrivilege;
    }

}
