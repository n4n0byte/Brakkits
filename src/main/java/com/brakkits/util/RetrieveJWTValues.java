package com.brakkits.util;

import com.brakkits.models.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 3/1/2020
 **/
public class RetrieveJWTValues {

    /**
     * Parses jwt for properties
     * @param token
     * @param propertyName
     * @return
     */
    public static Optional<String> getValueByName(JwtAuthenticationToken token, String propertyName){

        Map<String,String> a = (Map<String, String>) token.getTokenAttributes().get("userInfo");

        if (a.containsKey(propertyName))
        {
            return Optional.of(a.get(propertyName));
        }
        return Optional.empty();
    }

    /**
     * Parses jwt for properties
     * @param token
     * @param properties
     * @return
     */
    public static List<String> getValuesByName(JwtAuthenticationToken token, String... properties){
        List<String> values = new ArrayList<>();
        Map<String,String> a = (Map<String, String>) token.getTokenAttributes().get("userInfo");
        if (a.containsKey("userTag"))
        {
            for (String prop : properties){
                if (a.containsKey(prop))
                {
                    values.add(a.get(prop));
                }
            }
        }
        return values;
    }

    /**
     * parses jwt and makes a user
     * @param token Token
     * @return User
     */
    public static User makeUser(JwtAuthenticationToken token){

        List<String> props = RetrieveJWTValues.getValuesByName(
                token,
                "login", "userTag", "firstName", "lastName", "displayName", "status");
        return User.builder()
                        .email(props.get(0))
                        .tag(props.get(1))
                        .displayName(props.get(4))
                        .status(props.get(5))
                    .build();
    }

}
