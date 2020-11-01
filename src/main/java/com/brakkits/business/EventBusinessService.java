package com.brakkits.business;
import com.brakkits.data.TournamentRepository;
import com.brakkits.data.UserRepository;
import com.brakkits.models.*;
import com.brakkits.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.math.IntMath;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.SecurityException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/25/2020
 *
 * Business Layer Service Impl. for handling events
 **/
public class EventBusinessService implements EventBusinessServiceInterface {

    private TournamentRepository tournamentRepository;

    private UserRepository userRepository;

    private Tournament t;

    private static HashMap<String, Tournament> tournamentHashMap = new HashMap<>();

    private ObjectMapper obj = new ObjectMapper();

    private static String imgLocation = "resources/static/images/";

    @Autowired
    public void setTournamentRepository(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * switch on the selected games to get stagelists
     * @param gameName Stirng
     * @return List of String
     */
    private List<String> getStageList(String gameName){

        switch (gameName){
            case "Melee":
                return Arrays.asList("Battlefield", "Dreamland","Final Destination",
                        "Fountain of Dreams", "Pokemon Stadium", "Yoshi's Story");
            case "Smash 4":
                return Arrays.asList("Battlefield", "Dreamland",
                        "Final Destination","Lylat Cruise","Smashville",
                        "Town and City", "Yoshi's Story");
            case "Smash Ultimate":
                return Arrays.asList("Battlefield","Battlefield", "Final Destination", "Yoshi's Story",
                        "Pokemon Stadium 2", "Smashville", "Fountain of Dreams", "Battlefield",
                        "Kalos Pokemon League", "Town and City");
            default:
                return Arrays.asList("Dreamland","Halberd","Fountain of Dreams");
        }

    }



    /**
     * Finds a tournament
     * @param title String
     * @return Tournament
     */
    @Override
    public Tournament findTournament(String title){
        t = tournamentRepository.findTournamentByTitle(title).orElseThrow(DataNotFoundException::new);

        try {
            t.setRounds(tournamentHashMap.get(title).getRounds());
        } catch(Exception e){
            throw new DataNotFoundException("Tournament wasn't inserted into map");
        }

        return t;
    }

    /**
     * Deletes am event
     * @param user User
     * @param title String
     * @return boolean
     */
    @Override
    public boolean deleteEvent (User user, String title){

        // throw is no tourney is found
        t = tournamentRepository.findTournamentByTitle(title)
                .orElseThrow(DataNotFoundException::new);

        // try to delete tournament bracket or throw
        try {
            tournamentHashMap.remove(title);
        } catch(Exception e){
            throw new DataNotFoundException("Hashmap delete failed");
        }

        // throw if the tournament owner is not the same as the user submitting
        if (!t.getOwner().getTag().equals(user.getTag())) throw new SecurityException("Invalid User");

        try {
            tournamentRepository.delete(t);
        } catch (RuntimeException e){
            throw new InvalidDataException(e);
        }

        return true;
    }

    /**
     * Updates an event, throws in failure case
     * @param user user
     * @param image File
     * @param title String
     * @param description String
     * @return boolean
     */
    @Override
    public boolean updateEvent(User user, MultipartFile image, String oldTitle, String title, String description, String gameTitle){

        // throw is no tourney is found
        t = tournamentRepository.findTournamentByTitle(oldTitle)
                .orElseThrow(DataNotFoundException::new);

        // throw if the tournament owner is not the same as the user submitting
        if (!t.getOwner().getTag().equals(user.getTag())) throw new SecurityException("Invalid User");

        // setup file io for image
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

        // update tournament
        t.setTitle(title);
        t.setDescription(description);
        t.setGameTitle(gameTitle);

        //TODO update this for deployment
        t.setImgUrl("http://localhost:8080/images/" + modifiedImgName);

        tournamentRepository.save(t);
        return true;
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

        // attempt to write image
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
        t = Tournament.builder()
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



        List<List< List<User> >> rounds = new ArrayList<>();

        //initialize bracket with new round
        t.setRounds(doInitialBracketSetup(t.getCapacity()));

        // store the tournament in hashmap
        tournamentHashMap.put(title, t);


        String serializedBracket = null;

        // serialize the bracket
        try {
           serializedBracket = obj.writeValueAsString(tournamentHashMap);
           t.setBracket(serializedBracket);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DataNotFoundException();
        }

        tournamentRepository.save(t);
    }

    private ArrayList<ArrayList< ArrayList<BracketUser> >> doInitialBracketSetup(int capacity){
        ArrayList<ArrayList< ArrayList<BracketUser> >> rounds = new ArrayList<>();

        int maxRoundCapacity = IntMath.floorPowerOfTwo(capacity);

        // create max capacity at each round corresponding a power of 2
        while (maxRoundCapacity >= 1){

            ArrayList< ArrayList<BracketUser> > currentRound = new ArrayList<>();


            for (int currentRoundIndex = 0; currentRoundIndex < maxRoundCapacity; currentRoundIndex++){
                 // add a pair of users for each match in round
                ArrayList<BracketUser> pair = new ArrayList<>();
                pair.add(new BracketUser());
                pair.add(new BracketUser());
                currentRound.add(pair);
            }
            rounds.add(currentRound);
            maxRoundCapacity = maxRoundCapacity / 2;
        }

        return rounds;
    }

    @Override
    public void addEntrant(User entrant, String eventName){

        BracketUser bUser = new BracketUser();
        bUser.copy(entrant);


        try{
            t = tournamentHashMap.get(eventName);
        } catch (Exception e) {
            throw  new DataNotFoundException("Tournament name not found");
        }

        ArrayList<ArrayList<ArrayList<BracketUser>>> rounds = t.getRounds();

        List<User> tmp = t.getAttendees();
        tmp.add(new User(bUser));
        t.setAttendees(tmp);

        try{
            for (int round = 0; round < rounds.size(); round++){
                for (int pair = 0; pair < rounds.get(round).size(); pair++){
                    for (int userIndex = 0; userIndex < 2; userIndex++){
                        BracketUser user = rounds.get(round).get(pair).get(userIndex);
                        if(user.getUser() == null){
                            user = bUser;
                            rounds.get(round).get(pair).set(userIndex, user);
                            throw new LoopBreakException();
                        }
                    }
                }
            }
        } catch (Exception e){
            System.out.println("Broke out of user bracket loop");
        }

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
            Optional<User> attender = tournament.get().getAttendees().stream()
                    .filter((attendee)->{return attendee.getEmail().equals(requester.getEmail());})
                    .findFirst();

            userTournamentPrivilege.setEnteredIntoTournament(attender.isPresent());

        }

        return userTournamentPrivilege;
    }

}
