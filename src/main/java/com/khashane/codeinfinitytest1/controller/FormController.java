package com.khashane.codeinfinitytest1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

 //   @GetMapping("/user-form")
    public String showUserForm() {
        return "user-form.html";  // ✅ Returns the static HTML file
    }
}