package edu.kea.kab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/input")
    public String input() {
        return "/input";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "/privacy";
    }
}
