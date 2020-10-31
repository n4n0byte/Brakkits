package com.brakkits.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 *
 * This is the Attendee Model class, it uses Lombok annotations to
 * generate accessor methods, builders, and toString methods
 **/
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class Tournament {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    private int capacity, points;
    private String gameTitle, title, description, imgUrl;
    private Date startDate;
    private Boolean isActive;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private User owner;

    @Transient
    private String bracketType = "Single Elimination";

    @ElementCollection
    private List<String> stageList;

    @OneToMany()
    private List<Attendee> attendees;

    @Transient
    private List<List< List<BracketUser> >> rounds;

}
