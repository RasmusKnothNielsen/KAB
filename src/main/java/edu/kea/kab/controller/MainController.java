package edu.kea.kab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
public class MainController {

    @GetMapping("/input")
    public String input(Model model) {
        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
        model.addAttribute("weeknumber",calendar.get(Calendar.WEEK_OF_YEAR));
        return "input";
    }
}
