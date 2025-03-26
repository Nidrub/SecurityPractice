package com.alibou.security.service;

import com.alibou.security.model.UserPrincipal;
import com.alibou.security.model.Users;
import com.alibou.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//this is for the database service in the Security config 3rd
@Service
public class MyUserDetailService implements UserDetailsService {
    //we then are going to get from the database UserRepo
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        //check if the user is there
        if (user == null) {
            System.out.println("user not found");
            //let the system not found
            throw new UsernameNotFoundException("user not found");
        }
        //if it is found
        return new UserPrincipal(user);
    }
}
