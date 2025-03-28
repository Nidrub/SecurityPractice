package com.alibou.security.repo;

import com.alibou.security.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//<class,primary key>
@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    //to find the username
    Users findByUsername(String username);

}
