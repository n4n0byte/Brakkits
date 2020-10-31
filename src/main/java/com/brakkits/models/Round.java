package com.brakkits.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 * This is the Round Model class, it uses Lombok annotations to
 * generate accessor methods, builders, and toString methods
 **/
@Getter
@Setter
public class Round {

    private Integer id;

    private List<Pair> users;

}
