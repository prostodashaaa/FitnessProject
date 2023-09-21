package com.example.fitnesproject.controllers;

import com.example.fitnesproject.domain.FitnessMembership;
import com.example.fitnesproject.services.FitnessMembershipService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Controller
@RequestMapping("/")
public class MainController {
    private static final String SEARCH_STRING_ATTR = "search_string";
    private static final String MEMBERSHIP_LIST_ATTR = "membership_list";
    private static final String ID_PATH_VAR = "id";
    private static final String SORT_COL_NUM_PATH_VAR = "col_name";

    private static final Map<String, Function<FitnessMembership, Comparable>> fieldNameToExtractor
            = Map.of("name", FitnessMembership::getName,
            "phone", FitnessMembership::getPhone,
            "mail", FitnessMembership::getMail,
            "boughtDate", FitnessMembership::getAbonimentBoughtDate,
            "trainsLeft", FitnessMembership::getTrainsLeft,
            "option", FitnessMembership::getOption,
            "cost", FitnessMembership::getCost,
            "payed", FitnessMembership::getPayedSum,
            "id", FitnessMembership::getId);


    private final FitnessMembershipService fitnessMembershipService;

    @Autowired
    public MainController(FitnessMembershipService fitnessMembershipService) {
        this.fitnessMembershipService = fitnessMembershipService;
    }

    @GetMapping
    public String viewHomePage(Model model,
                               @Param(SEARCH_STRING_ATTR) String keyword,
                               HttpServletRequest request) {

        List<FitnessMembership> fitnessMembershipList = keyword == null ?
                fitnessMembershipService.getAll()
                : fitnessMembershipService.getFitnessMembershipsWithOwner(keyword);


        fitnessMembershipList.sort(Comparator.comparing(
                fieldNameToExtractor.getOrDefault(
                        Objects.requireNonNullElse(request.getParameter(SORT_COL_NUM_PATH_VAR),
                                "id"),
                        FitnessMembership::getId)));

        model.addAttribute(MEMBERSHIP_LIST_ATTR, fitnessMembershipList);
        model.addAttribute(SEARCH_STRING_ATTR, keyword);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteMemberShipById(@PathVariable(name = ID_PATH_VAR) Long id) {
        fitnessMembershipService.deleteByID(id);
        return "redirect:/";
    }
}

