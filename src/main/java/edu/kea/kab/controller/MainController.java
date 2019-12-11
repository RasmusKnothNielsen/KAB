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
import org.springframework.web.context.request.RequestContextHolder;

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
        return "redirect:/results";
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


    @GetMapping("/results")
    public String getPresentationOfUsage(Model model) {

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Consumption consumption = consumptionRepository.findBySession(sessionId);

        if (consumption == null) {
            return "redirect:/";
        }
        // Convert hours of streaming into km in diesel car
        double videoConsumption = consumption.getVideoHours() * 100;
        double musicConsumption = consumption.getMusicHours() * 10;
        double mobileConsumption = consumption.getMobileHours() * 5;
        double sum = videoConsumption + mobileConsumption + mobileConsumption;
        model.addAttribute("consumption", sum);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int lastWeek = calendar.get(Calendar.WEEK_OF_YEAR)-1;
        //TODO - handle first week of year
        int year = calendar.get(Calendar.YEAR);

        try {
            long userId = consumption.getUser().getId();
            Consumption lastConsumption = consumptionRepository.findByUserIdAndAndWeek(userId, lastWeek, year);

            double lastVideoCarbon = lastConsumption.getVideoHours() * 100;
            double lastMusicCarbon = lastConsumption.getMusicHours() * 10;
            double lastMobileCarbon = lastConsumption.getMobileHours() * 5;
            double lastSum = lastVideoCarbon + lastMusicCarbon + lastMobileCarbon;

            double diff_mobile = lastConsumption.getMobileHours() - consumption.getMobileHours();
            double diff_music = lastConsumption.getMusicHours() - consumption.getMusicHours();
            double diff_video = lastConsumption.getVideoHours() - consumption.getVideoHours();

            String mobile = "";
            if(diff_mobile < 0) {
                mobile += "sænket din mobil skærmtid med " + (-1*diff_mobile) + "timer.";
            } else if (diff_mobile == 0) {
                mobile = "ikke ændret din mobil skærmtid.";
            } else if (diff_mobile > 0) {
                mobile += "øget mobil skærmtid med " + diff_mobile + "timer.";
            }
            model.addAttribute("mobile",mobile);

            String music = "";
            if(diff_music < 0) {
                music += "sænket din musik streaming med " + (-1*diff_music) + "timer.";
            } else if (diff_music == 0) {
                music = "ikke ændret din musik streaming.";
            } else if (diff_music > 0) {
                music += "øget musik streaming med " + diff_music + "timer.";
            }
            model.addAttribute("music",music);

            String video = "";
            if(diff_video < 0) {
                video += "sænket din video streaming med " + (-1*diff_video) + "timer.";
            } else if (diff_video == 0) {
                video = "ikke ændret din video streaming.";
            } else {
                video += "øget video streaming med " + diff_video + "timer.";
            }
            model.addAttribute("video",video);

            String total = "Din samlede CO<sub>2</sub> udledning er ";
            double percentChangeTotal = Math.abs(lastSum - sum) / lastSum * 100;
            if(sum < lastSum) {
                total += "faldet med" + percentChangeTotal + "%";
            } else if (sum == lastSum) {
                total += "uændret.";
            } else {
                total += "steget med" + percentChangeTotal + "%";
            }
            model.addAttribute("total",total);

        } catch (NullPointerException ignored) {
        }
        return "results";
    }
}
