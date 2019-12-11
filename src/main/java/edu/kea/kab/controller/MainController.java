package edu.kea.kab.controller;

import edu.kea.kab.model.Consumption;
import edu.kea.kab.model.Role;
import edu.kea.kab.model.User;
import edu.kea.kab.repository.ConsumptionRepository;
import edu.kea.kab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;

import java.security.Principal;
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

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("/input")
    public String input(Model model) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        model.addAttribute("weeknumber", calendar.get(Calendar.WEEK_OF_YEAR));
        return "input";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "/privacy";
    }

    @PostMapping("/input")
    public String inputFromUser(@ModelAttribute Consumption consumption) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        consumption.setSession(sessionId);
        consumption.setYear(year);
        consumption.setWeek(week);
        consumptionRepository.save(consumption);
        return "redirect:/presentationofusage";
    }

    @GetMapping("/adduser")
    public String userCreationSite() {
        return "adduser";
    }

    // add a new user to the users table
    @PostMapping("/adduser")
    public String addUser(User user) {
        userService.addUser(user);
        return "/adduser";
    }

    @GetMapping("/presentationofusage")
    public String getPresentationOfUsage(Model model, @AuthenticationPrincipal org.springframework.security.core.
            userdetails.User user, Principal principal) {
        // if the current user is already a registered user
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(Role.ROLE_USER)) {
                // save the total consumption in a double so it can referenced with thymeleaf
                double sumOfTotalConsumption = consumptionRepository.sumOfTotalConsumption(userService.getId(
                        principal.getName()));
                model.addAttribute("totalSum", sumOfTotalConsumption);
            }
        }

        return "presentationofusage";

    @GetMapping("/results")
    public String getPresentationOfUsage(Model model)
    {

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Consumption consumption = consumptionRepository.findBySession(sessionId);
        System.out.println(consumption);

        if (consumption == null)
        {
            return "redirect:/";
        }
        // Convert hours of streaming into km in diesel car
        double videoConsumption = consumption.getVideoHours() * 100;
        double musicConsumption = consumption.getMusicHours() * 10;
        double mobileConsumption = consumption.getMobileHours() * 5;
        double sum = videoConsumption + mobileConsumption + mobileConsumption;
        model.addAttribute("consumption", sum);
        return "results";
    }
}


