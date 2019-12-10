package edu.kea.kab.controller;

import edu.kea.kab.model.Consumption;
import edu.kea.kab.repository.ConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@Controller
public class MainController {

    ConsumptionRepository consumptionRepository;

    @Autowired
    MainController(ConsumptionRepository consumptionRepository){
        this.consumptionRepository=consumptionRepository;
    }

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
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.WEEK_OF_YEAR);

        consumption.setSession(sessionId);
        consumption.setYear(2019);
        consumption.setWeek(week);
        consumptionRepository.save(consumption);
        return "redirect:/input";
    }

    @GetMapping("/adduser")
    public String userCreationSite() {
        return "adduser";
    }

    @GetMapping("/presentationofusage")
    public String getPresentationOfUsage(Model model) {

        // TODO delete when tests are done and SessionId/input is implemented
        Long sessionId = Long.valueOf(2);
        // TODO: uncomment when ready for deploy
        //String stringSessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        //Long sessionId = Long.valueOf(stringSessionId);
        Optional<Consumption> consumptionOptional = consumptionRepository.findById(sessionId);
        System.out.println(consumptionOptional);
        Consumption consumption = consumptionOptional.get();

        // Convert hours of streaming into km in diesel car
        double videoConsumption = consumption.getVideoHours() * 100;
        double musicConsumption = consumption.getMusicHours() * 10;
        double mobileConsumption = consumption.getMobileHours() * 5;
        double sum = videoConsumption + mobileConsumption + mobileConsumption;
        model.addAttribute("consumption", sum);
        return "presentationofusage";
    }
}
