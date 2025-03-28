package com.alibou.security.service;

import com.alibou.security.model.Users;
import com.alibou.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//part 4 user service for the password encryption
@Service
public class UserService {

    //we are going to reuse the Userrepo also @autowire to not have to create
    //the object
    @Autowired
    private UserRepo repo;
    public Users register(Users user){
        //this will save this data in database and return the user
       return repo.save(user);
    }
}
