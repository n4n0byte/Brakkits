package com.brakkits.config;

import com.brakkits.business.EventBusinessServiceInterface;
import com.brakkits.data.AttendeesRepository;
import com.brakkits.data.TournamentRepository;
import com.brakkits.data.UserRepository;
import com.brakkits.models.Attendee;
import com.brakkits.models.Tournament;
import com.brakkits.models.User;
import com.google.common.math.IntMath;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 * inserts mock data on application startup
 **/
@Component
public class CMDRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private AttendeesRepository attendeesRepository;

    @Autowired
    private EventBusinessServiceInterface eventBusinessServiceInterface;


    /**
     * inserts users into db
     */
    private void insertUsers(){

        //create 20 users
        for (int i = 0; i < 20; i++){
            User usr =
                    User
                        .builder()
                        .status("ACTIVE")
                        .displayName("displayName " + i)
                        .tag(i * 2 + " TAG")
                        .email(i + "@number.com")
                        .eventsAttended(20)
                        .lossCount(0)
                        .winCount(12)
                        .placement(i)
                        .winLossRatio(.4)
                    .build();
            userRepository.save(usr);
        }
    }

    /**
     * creates mock events
     */
    private void createAndTestEvents(){
        User user = userRepository.findByTag("2 TAG").get();

        List<Attendee> attendees = new ArrayList<>();

        for (int i = 0; i < 16; i++){
            attendees.add(
                Attendee
                    .builder()
                    .tag(i + " TAG")
                    .email(i + "@number.com")
                    .build()
            );
        }

        attendeesRepository.saveAll(attendees);

        Tournament tournament = Tournament
                .builder()
                    .startDate(new Date())
                    .owner(user)
                    .title("test")
                    .gameTitle("Smash Ultimate")
                    .description("Smash Ultimate Tourney")
                    .imgUrl("http://localhost:8080/images/smash.jpg")
                    .points(10)
                    .attendees(attendeesRepository.findAll())
                    .isActive(true)
                .stageList(Arrays.asList("Dreamland","Halberd","Fountain of Dreams"))
                .capacity(64)
                .build();
        tournamentRepository.save(tournament);
        eventBusinessServiceInterface.createEvent(user, null, "Service","description", new Date(), 32, "Melee");
    }


    @Override
    public void run(String... args) throws Exception {
        insertUsers();
        createAndTestEvents();
    }
}
