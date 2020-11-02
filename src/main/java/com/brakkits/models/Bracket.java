package com.brakkits.models;

import lombok.*;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 11/1/2020
 *
 * This is the Bracket Model class, it uses Lombok annotations to
 * generate accessor methods, builders, and toString methods
 **/
@Getter
@Setter
@ToString
@Builder
@Log4j
@NoArgsConstructor
@AllArgsConstructor
public class Bracket {

    private Integer id;

    private Tournament tournament;

    private List<Round> rounds;

}
