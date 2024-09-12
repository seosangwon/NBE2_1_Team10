package com.example.coffeeshop.maincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {

    @PostMapping("/redirect")
    public RedirectView redirect(@RequestParam String email) {
        if ("admin@example.com".equals(email)) {
            return new RedirectView("/admin");
        } else {
            return new RedirectView("/user");
        }
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage.html";
    }

    @GetMapping("/user")
    public String userPage() {
        return "userPage.html";
    }
}