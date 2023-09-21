package com.example.fitnesproject.services;

import com.example.fitnesproject.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JdbcUserDetailsManager users;

    @Autowired
    UserService(JdbcUserDetailsManager users){
        this.users = users;
    }

    public boolean addUser(Users new_user) {
        if (!users.userExists(new_user.getUserName())) {

            UserDetails userDetails = User.builder()
                    .username(new_user.getUserName())
                    .password("{noop}" + new_user.getPassword())
                    .authorities(
                            new_user.getUserRole().getDbString()
                    )
                    .build();

            //System.out.println(userDetails);
            users.createUser(userDetails);

            return true;
        }
        return false;
    }
}