package com.alibou.security.service;

import com.alibou.security.model.Users;
import com.alibou.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//part 4 user service for the password encryption
@Service
public class UserService {

    //we are going to reuse the Userrepo also @autowire to not have to create
    //the object
    @Autowired
    private UserRepo repo;
    //part 5 authentication manager, so that we cau se that object
    @Autowired
    AuthenticationManager authManager;

    //create object for the JWT generator
    @Autowired
    private JWTService jwtService;

    //Brcypt to edit password
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);//strength of the bcrypt we are going to do 12 (default is -1)


    public Users register(Users user){
        //second part of bellow (change the password using bcrypt library) get the password of the user
        user.setPassword(encoder.encode(user.getPassword()));

        //this will save this data in database and return the user
       return repo.save(user);
    }

    //we verify if the user exists part 5
    public String verify(Users user) {
        //to authenticate
        Authentication authentication =
                //we pass an object to ge if it exists
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        //verify if the user is real
        if(authentication.isAuthenticated()){
            //so now that we have checked that this works, then we are going to generate a token
            return jwtService.generateToken(user.getUsername());
        }
        //if it does not exist
        return "Fail";
    }
}
