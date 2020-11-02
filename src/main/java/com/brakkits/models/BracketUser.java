package com.brakkits.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

@NoArgsConstructor
@Data
public class BracketUser {

    @Getter(AccessLevel.NONE)
    private String user;
    private Boolean isWinner = false;
    private String userLink = "";
    private Integer score = 0;
    private String scoreLink = "";

    @Getter(AccessLevel.NONE)
    private static int count = 1;

    public BracketUser(String user) {
        this.user = user;
    }


    public String getUser() {
        if (user == null) return "User " + count++;
        return user;
    }



    public void copy(User user){
        isWinner = false;
        this.user = user.getTag();
        this.userLink = "testLink";
        this.score = 0;
        this.scoreLink = "empty";
    }

}
