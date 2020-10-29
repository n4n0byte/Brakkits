package com.brakkits.models;

import lombok.*;
import lombok.extern.log4j.Log4j;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 *
 * This is the Attendee Model class, it uses Lombok annotations to
 * generate accessor methods, builders, and toString methods
 **/
@Getter
@Setter
@ToString
@Builder
@Log4j
@NoArgsConstructor
@AllArgsConstructor
public class UserTournamentPrivilege {
    String tag, email;
    boolean isEnteredIntoTournament, isOwner;
}
