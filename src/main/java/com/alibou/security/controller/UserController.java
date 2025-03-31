package com.alibou.security.controller;

import com.alibou.security.model.Users;
import com.alibou.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//this is going to be part 4 where we encrypt our password
@RestController
public class UserController {
    //other part odf 4 we are going to add the UserServicew
    @Autowired
    private UserService service;


//normally we use User (also requestbody it receives data from the client) also
    //we are adding Postmapping because we are submitting data from the client
    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
         //we are returning the user data as it is
        return service.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        //print the data of the user
        System.out.println(user);
        //we are then going to verify if the user exist
        return service.verify(user);
    }
}
