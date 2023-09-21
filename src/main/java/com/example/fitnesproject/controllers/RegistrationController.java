package com.example.fitnesproject.controllers;

import com.example.fitnesproject.domain.Users;
import com.example.fitnesproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final String NEW_USER_ATTR = "new_user";
    private static final String USER_ALREADY_REG_ATTR = "has_already_reg";
    private final UserService userService;
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String registration(Model model){
        model.addAttribute(NEW_USER_ATTR, new Users());
        model.addAttribute(USER_ALREADY_REG_ATTR, false);

        return "registration";
    }

    @PostMapping
    public String addUser(Model model,
                          @ModelAttribute(NEW_USER_ATTR) Users users) {
        if (userService.addUser(users)){
            model.addAttribute(USER_ALREADY_REG_ATTR, false);
            return "redirect:/login";
        } else {
            model.addAttribute(USER_ALREADY_REG_ATTR, true);
            return "registration";
        }
    }
}
