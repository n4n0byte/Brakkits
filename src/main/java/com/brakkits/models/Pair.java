package com.brakkits.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 11/1/2020
 * This is the Pair Model class, it uses Lombok annotations to
 * generate accessor methods, builders, and toString methods
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Pair {

    User leftUser;
    User rightUser;

}
