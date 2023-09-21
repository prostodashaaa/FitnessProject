package com.example.fitnesproject.services;

import com.example.fitnesproject.domain.MembershipOption;
import org.springframework.stereotype.Service;

@Service
public class TrainCountPolicyService {
    public int getMembershipTrainCount(MembershipOption option){
        return switch (option){
            case STANDARD -> 8;
            case ADVANCED -> 12;
            case VIP -> 16;
        };
    }
}
