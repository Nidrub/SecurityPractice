package com.alibou.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//configuration file to spring, basically customizing my own
//basically th 2 @ are saying we are going this is a configuration and to go with this configuration
//this is the first part of the security (1)

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //inject our service for the database
    @Autowired
    private UserDetailsService userDetailsService;



    //bean for security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //disable csrf (no login restriction) these are in java lambda expressions
    //    http.csrf(customizer ->customizer.disable());

        //this is that every request needs to be authenticated
      //  http.authorizeHttpRequests(requests -> requests.anyRequest().authenticated());
        //we then need to enable the form login because even if they have the username and password there is no way that
        //it is able to do anything
        //thats why we need to use it here (basically this is our filter all of this
 //       http.formLogin(Customizer.withDefaults()); //we can disable this so we can just use postman to directly log in
        //then we got to fix because we are getting form login response, such as the html page and stuff so lets fix that
        //its is not sending Json data like if you request in in Postman and it gnerates a page to log in and see data
        //in a page html
        //so we add this in order to get the http json data back when we request in http
//          http.httpBasic(Customizer.withDefaults());
        //logout will not work

        //here is for stateless session
      //  http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // so every time we are sending an ew session we get in a session ID every time

        //-- rest of the things we are doing
    // the bottom is equivalent ot this ->  http.csrf(customizer ->customizer.disable());
//        Customizer<CsrfConfigurer<HttpSecurity>> custCSrf = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            @Override
//            //change the name to customizer
//            public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//                //inside here is where we do the work to customizes the security
//                customizer.disable();
//            }
//        };
//        //pass tit here
//        http.csrf(custCSrf);

        //we are saying that for Spring boot to go with this an not the default Security

        //here is the code but shorten that he is showing

        //also since we dissable the csrf token for post, you dont need to send it
        return http.csrf(customizer ->customizer.disable())
                .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
        //       http.formLogin(Customizer.withDefaults());
       // return http.build();

    }


    //new one with using the database, the bottom is to learn the concept we are in 1:14:32 (here is authentication provider) 3rd
    @Bean
    public AuthenticationProvider authenticationProvider() {
        //databasae authentication provider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //we are not using a paswword encoder rigth now
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());//NoOpPasswordEncoder.getInstance() (we are in part 4 of encrypting password
        provider.setUserDetailsService(userDetailsService);
        return provider;

    }



//    //this is the second part were it involves the dataset for our user to have a list of users
//    @Bean
//    public UserDetailsService userDetailsService() {
//        //so heres where we start adding our things like our different users (this is used to try to understand how to customize stuff)
//
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("kira")
//                .password("k@123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("harsh")
//                .password("h@123")
//                .roles("ADMIN")
//                .build();
//        //---
//
//
//        //not working because we are isong pour own user detail service
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
}
