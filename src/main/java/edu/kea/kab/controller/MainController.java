package edu.kea.kab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/input")
    public String input() {
        return "/input";
    }
}
