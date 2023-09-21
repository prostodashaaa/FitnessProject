package com.example.fitnesproject.domain;

import lombok.Data;

import java.util.Map;

@Data
public class FitnessMembershipUserInfo {
    private  String name;
    private  String phone;
    private  String mail;
    private  MembershipOption option;
    private  double payedSum;
}
