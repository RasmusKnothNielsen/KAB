package edu.kea.kab.controller;

import edu.kea.kab.model.Consumption;
import edu.kea.kab.model.Role;
import edu.kea.kab.model.User;
import edu.kea.kab.repository.ConsumptionRepository;
import edu.kea.kab.service.ConsumptionService;
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

    @Autowired
    ConsumptionService consumptionService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
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

    @GetMapping("/profile")
    public String profile() {
        return "/profile";
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

        String session = RequestContextHolder.currentRequestAttributes().getSessionId();

        consumptionService.connectUserWithSession(user,session);

        return "/adduser";
    }

    @GetMapping("/results")
    public String getPresentationOfUsage(Model model, @AuthenticationPrincipal org.springframework.security.core.
            userdetails.User user, Principal principal) {

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Consumption consumption = consumptionRepository.findFirstBySessionOrderById(sessionId);

        if (consumption == null) {
            return "redirect:/";
        }
        // Convert hours of streaming into km in diesel car
        double videoConsumption = consumption.getVideoHours() * 100;
        double musicConsumption = consumption.getMusicHours() * 10;
        double mobileConsumption = consumption.getMobileHours() * 5;
        double sum = videoConsumption + musicConsumption + mobileConsumption;
        int absolutSum = (int) Math.abs(sum);
        model.addAttribute("consumption", absolutSum);

        // if the current user is already a registered user
        if (user != null) {
            if (user.getAuthorities() != null) {
                for (GrantedAuthority authority : user.getAuthorities()) {
                    if (authority.getAuthority().equals(Role.ROLE_USER)) {
                        // save the total consumption in a double so it can referenced with thymeleaf
                        double sumOfTotalConsumption = consumptionRepository.sumOfTotalConsumption(userService.getId(
                                principal.getName()));
                        model.addAttribute("totalSum", sumOfTotalConsumption);
                    }
                }
            }
        }


        // Compare to average, which is said to be 3,5 hours pr day, ie 24,5 hours a week.
        double weeklyAverage = 24.5;
        double sumHours = consumption.getVideoHours() + consumption.getMusicHours() + consumption.getMobileHours();
        System.out.println(sumHours);

        if (sumHours < weeklyAverage) {
            // You stream less than the average
            // Calculate difference in percent
            double sumPercent = ((weeklyAverage - sumHours) / weeklyAverage) * 100;
            sumPercent = Math.floor(sumPercent);
            model.addAttribute("result", "Du bruger gennemsnitligt " + sumPercent + "% mindre om året end den " +
                    "gennemsnitlige dansker som streamer 7 timer om dagen inkl. tid med mobiltelefonen.");


        } else if (weeklyAverage < sumHours) {
            // You stream more than average
            // Calculate difference in percent
            double sumPercent = ((sumHours / weeklyAverage) - 1) * 100;
            sumPercent = Math.floor(sumPercent);
            model.addAttribute("result", "Du bruger gennemsnitligt " + sumPercent + "% mere om året end den " +
                    "gennemsnitlige dansker som streamer 7 timer om dagen inkl. tid med mobiltelefonen.");

        } else {
            // You stream exactly as much as the average
            model.addAttribute("result", "Du bruger gennemsnitligt det samme som den " +
                    "gennemsnitlige dansker som streamer 7 timer om dagen inkl. tid med mobiltelefonen.");

        }
        // Calculate total consumption
        if(user != null) {
            double totalConsumption = consumptionRepository.sumOfTotalConsumption(userService.getId(principal.getName()));
            System.out.println(totalConsumption);
            model.addAttribute("totalConsumption", totalConsumption);
        } else {
                    model.addAttribute("totalConsumption", "Only logged in users can see their total consumption");
                }



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

        } catch (NullPointerException ignored) {}
        return "results";
    }
}