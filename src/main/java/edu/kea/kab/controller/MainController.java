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
    public String getPresentationOfUsage(Model model)
    {

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Consumption consumption = consumptionRepository.findBySession(sessionId);

        if (consumption == null)
        {
            return "redirect:/";
        }
        // Convert hours of streaming into km in diesel car
        double videoConsumption = consumption.getVideoHours() * 100;
        double musicConsumption = consumption.getMusicHours() * 10;
        double mobileConsumption = consumption.getMobileHours() * 5;
        double sum = videoConsumption + musicConsumption + mobileConsumption;
        int absolutSum = (int) Math.abs(sum);
        model.addAttribute("consumption", absolutSum);

        // Compare to average, which is said to be 3,5 hours pr day, ie 24,5 hours a week.
        double weeklyAverage = 24.5;
        double sumHours = consumption.getVideoHours() + consumption.getMusicHours() + consumption.getMobileHours();

        if (sumHours < weeklyAverage) {
            // You stream less than the average
            // Calculate difference in percent
            double sumPercent = ((weeklyAverage - sumHours) / weeklyAverage) * 100;
            sumPercent = Math.floor(sumPercent);
            model.addAttribute("result", "Du bruger gennemsnitligt " + sumPercent +
                    "% mindre om året end den gennemsnitlige dansker som streamer 7 timer om "
                    + "dagen inkl. tid med mobiltelefonen.");

        } else if (weeklyAverage < sumHours) {
            // You stream more than average
            // Calculate difference in percent
            double sumPercent = ((sumHours/weeklyAverage)-1)*100;
            sumPercent = Math.floor(sumPercent);
            model.addAttribute("result", "Du bruger gennemsnitligt " + sumPercent +
                    "% mere om året end den gennemsnitlige dansker som streamer 7 timer om " +
                    "dagen inkl. tid med mobiltelefonen.");

        } else {
            // You stream exactly as much as the average
            model.addAttribute("result", "Du bruger nøjagtig det samme som en gennemsnitsdansker, "
                    + " 7 timer om dagen inkl. tid med mobiltelefonen.");
        }


        return "results";
    }
}
