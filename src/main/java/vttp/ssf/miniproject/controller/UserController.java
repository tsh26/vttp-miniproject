package vttp.ssf.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserController {
    
    // landing page (User Login) -> goes to user homepage
    @GetMapping({"/", "/index"})
    public String homePage() {

        return "index";        
    }

    // In user homepage, user can:
    // 1. search bustop code to get bus arrival data (form)
    

    // 2. Select their boomark page to retrieve bustops they have


}