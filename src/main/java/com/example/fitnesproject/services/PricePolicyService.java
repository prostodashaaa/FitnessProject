package com.example.fitnesproject.services;

import com.example.fitnesproject.domain.MembershipOption;
import org.springframework.stereotype.Service;

@Service
public class PricePolicyService {
    public double getMembershipCost(MembershipOption option){
        return switch (option){
            case STANDARD -> 6000;
            case ADVANCED -> 8000;
            case VIP -> 12000;
        };
    }
}
