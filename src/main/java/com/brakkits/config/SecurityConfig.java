package com.brakkits.config;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.spring.boot.oauth.Okta;
import com.okta.spring.boot.oauth.config.OktaOAuth2Properties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 * Oauth + Spring Security Config
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // authorize image access and rest api doc access
        http.authorizeRequests()
                .antMatchers("/images/**","/swagger-ui/**","/v3/**").permitAll()
                .anyRequest().authenticated()
                    .and()
                .oauth2ResourceServer().jwt();
        // process CORS annotations
        http.cors();

        // force a non-empty     response body for 401's to make the response more browser friendly
        Okta.configureResourceServer401ResponseBody(http);
    }
}
