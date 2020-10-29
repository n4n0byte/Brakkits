package com.brakkits.models;

import lombok.*;

import javax.persistence.*;

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
public class Attendee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;


    private String tag;

    private String email;

    private Integer placement = -1;

}
