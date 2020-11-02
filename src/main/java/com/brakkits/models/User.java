package com.brakkits.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 11/1/2020
 *
 * This is the User Model class, it uses Lombok annotations to
 * generate accessor methods, builders, and toString methods
 *
 **/
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {


    public void copy(User other) {
        this.id = other.id;
        this.tag = other.tag;
        this.email = other.email;
        this.displayName = other.displayName;
        this.status = other.status;
        this.wonRound = other.wonRound;
        this.placement = other.placement;
        this.winCount = other.winCount;
        this.lossCount = other.lossCount;
        this.eventsAttended = other.eventsAttended;
        this.winLossRatio = other.winLossRatio;
    }

    public User(BracketUser bracketUser) {
        this.tag = bracketUser.getUser();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tag;
    private String email;
    private String displayName;
    private String status;

    private boolean wonRound;

    private int placement, winCount, lossCount, eventsAttended;

    private double winLossRatio;

}
