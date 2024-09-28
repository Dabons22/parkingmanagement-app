package com.parkingmanagement.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/parkmanagement")
    public String parkManagement() {
        return "parkmanagement.html";
    }
}
