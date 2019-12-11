package edu.kea.kab.unittests;

import edu.kea.kab.controller.MainController;
import edu.kea.kab.model.Consumption;
import edu.kea.kab.model.User;
import edu.kea.kab.repository.ConsumptionRepository;
import edu.kea.kab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class ConsumptionRepositoryTest {

    @Autowired
    ConsumptionRepository consumptionRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void canPullSumOfTotalConsumption() throws Exception {

        long count = consumptionRepository.count();

        // Instantiate a user so it can be used to set user_id in the consumption object
        User user = new User();
        user.setEnabled(true);
        user.setPassword("Hunter2");
        user.setEmail("nikihansen@mail.dk");
        userRepository.save(user);

        // Instantiate a new Consumption and set its values.
        Consumption consumption = new Consumption();
        consumption.setSession("session");
        consumption.setWeek(50);
        consumption.setYear(2019);
        consumption.setMobileHours(10);
        consumption.setMusicHours(5);
        consumption.setVideoHours(2);
        consumption.setUser(user);
        consumptionRepository.save(consumption);

        // the sum of consumption hours
        double mobileHours = consumption.getMobileHours();
        double musicHours = consumption.getMusicHours();
        double videoHours = consumption.getVideoHours();
        double sumOfConsumptionHours = mobileHours + musicHours + videoHours;

        // Test if the consumption table has incremented one row
        assertThat(consumptionRepository.count()).isEqualTo(count + 1);

        // Iterate through the Consumption table and save the consumption as an Optional
        Optional<Consumption> consumptionById = consumptionRepository.findById(consumption.getId());

        // Test if the Optional equals the consumption
        assertThat(consumptionById.equals(consumption));

        // Test if the sumOfTotalConsumption() equals the sum from the consumption object
        assertThat(consumptionRepository.sumOfTotalConsumption(consumptionById.get().getUser().getId()))
                .isEqualTo(sumOfConsumptionHours);

    }

}
