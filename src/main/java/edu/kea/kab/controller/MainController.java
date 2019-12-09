package edu.kea.kab.controller;

import edu.kea.kab.model.Consumption;
import edu.kea.kab.model.User;
import edu.kea.kab.repository.ConsumptionRepository;
import edu.kea.kab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
public class MainController {

    @Autowired
    ConsumptionRepository consumptionRepository;

    @Autowired
    UserService userService;


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/input")
    public String input(Model model) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        model.addAttribute("weeknumber",calendar.get(Calendar.WEEK_OF_YEAR));
        return "input";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "/privacy";
    }

    @PostMapping("/input")
    public String inputFromUser(@ModelAttribute Consumption consumption) {
        consumptionRepository.save(consumption);
        return "redirect:/input";
    }

    // add a new user to the users table
    @PostMapping("/adduser")
    public String addUser(User user) {
        userService.addUser(user);
        return "/adduser";
    }
}
