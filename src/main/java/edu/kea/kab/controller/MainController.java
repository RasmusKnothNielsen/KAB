package edu.kea.kab.controller;

import edu.kea.kab.model.Consumption;
import edu.kea.kab.repository.ConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    ConsumptionRepository consumptionRepository;

    @GetMapping("/input")
    public String input() {
        return "/input";
    }

    @PostMapping("/input")
    public String inputFromUser(@ModelAttribute Consumption consumption) {
        System.out.println(consumption.getCategory());
        consumptionRepository.save(consumption);
        return "/input";
    }
}
