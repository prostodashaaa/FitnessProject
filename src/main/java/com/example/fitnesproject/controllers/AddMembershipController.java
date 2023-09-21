package com.example.fitnesproject.controllers;

import com.example.fitnesproject.domain.FitnessMembershipUserInfo;
import com.example.fitnesproject.services.FitnessMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/newMemberShip")
public class AddMembershipController {
    private static final String NEW_MEMBERSHIP_ATTR = "new_membership";

    private final FitnessMembershipService fitnessMembershipService;
    @Autowired
    public AddMembershipController(FitnessMembershipService fitnessMembershipService) {
        this.fitnessMembershipService = fitnessMembershipService;
    }

    @GetMapping
    public String createNewMembershipPage(Model model){
        model.addAttribute(NEW_MEMBERSHIP_ATTR,
                new FitnessMembershipUserInfo());
        return "new";
    }

    @PostMapping
    public String saveNewMembershipAndReturnMainPage(@ModelAttribute(NEW_MEMBERSHIP_ATTR)
                                                     FitnessMembershipUserInfo newFitMem){

        System.out.println(newFitMem);
        fitnessMembershipService
                .addFitnessMembership(newFitMem);
        return "redirect:/";
    }
}
