package com.example.RedditClone.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reddit/home")
public class BaseController {

    @GetMapping("")
    public String getBaseController(){
        return "Reddit Home page Base Controller";
    }

    @PostMapping("")
    public String postBaseController(){
        return "Ans and and ";
    }
}
