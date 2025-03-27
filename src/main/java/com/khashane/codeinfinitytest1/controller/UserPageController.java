package com.khashane.codeinfinitytest1.controller;

import com.khashane.codeinfinitytest1.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/user-form")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
       // return "user-form"; // Maps to user-form.html
        return "redirect:/user-form.html";
    }
}