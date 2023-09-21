package com.example.fitnesproject.services;

import com.example.fitnesproject.domain.FitnessMembership;
import com.example.fitnesproject.domain.FitnessMembershipUserInfo;
import com.example.fitnesproject.repo.FitnessMembershipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FitnessMembershipService {
    private final PricePolicyService pricePolicyService;
    private final TrainCountPolicyService trainCountPolicyService;
    private final FitnessMembershipRepo fitnessMembershipRepo;
    @Autowired
    FitnessMembershipService(PricePolicyService pricePolicyService,
                             TrainCountPolicyService trainCountPolicyService,
                             FitnessMembershipRepo fitnessMembershipRepo){
        this.fitnessMembershipRepo = fitnessMembershipRepo;
        this.trainCountPolicyService = trainCountPolicyService;
        this.pricePolicyService = pricePolicyService;
    }

    public void addFitnessMembership(FitnessMembershipUserInfo userInfo){
        fitnessMembershipRepo.save(FitnessMembership.builder()
                .name(
                        formatName(userInfo.getName())
                )
                .phone(userInfo.getPhone())
                .mail(userInfo.getMail())
                .abonimentBoughtDate(LocalDate.now())
                .trainsLeft(
                        trainCountPolicyService
                                .getMembershipTrainCount(userInfo.getOption())
                )
                .option(userInfo.getOption())
                .cost(
                        pricePolicyService
                                .getMembershipCost(userInfo.getOption())
                )
                .payedSum(userInfo.getPayedSum())
                .build());
    }

    private String formatName(String unformatted){
        return unformatted.length() > 0 ?
                unformatted.substring(0,1).toUpperCase() + unformatted.substring(1)
                : unformatted;
    }

    public List<FitnessMembership> getAll(){
        return fitnessMembershipRepo.findAll();
    }

    public List<FitnessMembership> getFitnessMembershipsWithOwner(String name){
        return fitnessMembershipRepo
                .findAllByNameContainingIgnoreCase(name);
    }

    public Optional<FitnessMembership> getFitnessMembershipByID(Long id){
        return fitnessMembershipRepo.findById(id);
    }
    public void deleteByID(Long id){
        fitnessMembershipRepo.deleteById(id);
    }

    public void save(FitnessMembership editedMembership){
        fitnessMembershipRepo.save(editedMembership);
    }

    public void copyEdited(FitnessMembership from, FitnessMembership to){
        to.setName(from.getName());
        to.setPhone(from.getPhone());
        to.setMail(from.getMail());
        to.setTrainsLeft(from.getTrainsLeft());
        to.setOption(from.getOption());
        to.setCost(from.getCost());
        to.setPayedSum(from.getPayedSum());
    }
}
