package com.example.fitnesproject.controllers;

import com.example.fitnesproject.domain.FitnessMembership;
import com.example.fitnesproject.services.FitnessMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/edit")
public class EditController {
    private static final String MEMBERSHIP_TO_EDIT_ATTR = "membership_to_edit";
    private static final String ID_SEARCH_ERROR_ATTR = "no_such_id";

    private final FitnessMembershipService fitnessMembershipService;

    @Autowired
    public EditController(FitnessMembershipService fitnessMembershipService) {
        this.fitnessMembershipService = fitnessMembershipService;
    }
    @GetMapping
    public String editMemberShipPage(Model model){
        model.addAttribute(MEMBERSHIP_TO_EDIT_ATTR, new FitnessMembership());
        return "edit";
    }

    @PostMapping
    public String editMemberShipPost(Model model,
                                     @ModelAttribute(MEMBERSHIP_TO_EDIT_ATTR)
                                     FitnessMembership editingMemberShip){

        System.out.println(editingMemberShip);

        AtomicReference<Boolean> noMembershipFound = new AtomicReference<>(false);

        fitnessMembershipService
                .getFitnessMembershipByID(editingMemberShip.getId())
                .ifPresentOrElse(fm -> {
                            fitnessMembershipService.copyEdited(
                                    editingMemberShip,  fm);
                            fitnessMembershipService.save(fm);
                        },
                        () -> noMembershipFound.set(true));

        model.addAttribute(ID_SEARCH_ERROR_ATTR, noMembershipFound.get());

        return noMembershipFound.get() ?
                "edit"
                : "redirect:/";
    }
}
