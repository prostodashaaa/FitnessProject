package com.example.fitnesproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;



@Data
@Entity
public class FitnessMembership{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String name;
    private  String phone;
    private  String mail;
    private  LocalDate abonimentBoughtDate;
    private  int trainsLeft;
    private  MembershipOption option;
    private  double cost;
    private  double payedSum;


    public FitnessMembership () {}

    @Builder
    public FitnessMembership(String name,
                             String phone,
                             String mail,
                             LocalDate abonimentBoughtDate,
                             int trainsLeft,
                             MembershipOption option,
                             double cost,
                             double payedSum) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.abonimentBoughtDate = abonimentBoughtDate;
        this.trainsLeft = trainsLeft;
        this.option = option;
        this.cost = cost;
        this.payedSum = payedSum;
    }
}
