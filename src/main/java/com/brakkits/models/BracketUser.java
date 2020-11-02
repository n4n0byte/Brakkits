package com.brakkits.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BracketUser {

    private String user;
    private Boolean isWinner;
    private String userLink;
    private Integer score = 0;
    private String scoreLink;

    public void copy(User user){
        isWinner = false;
        this.user = user.getTag();
        this.userLink = "testLink";
        this.score = 0;
        this.scoreLink = "empty";
    }

}
