package com.example.fitnesproject.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Table
@Data
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
    String userName;
    String password;
    UserRole userRole;

    public Users(){}

    @Builder
    public Users(Long id, String userName, String password, UserRole userRole) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }
}
