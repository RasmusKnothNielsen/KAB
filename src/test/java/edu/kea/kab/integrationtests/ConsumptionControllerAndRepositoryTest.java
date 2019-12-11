package edu.kea.kab.integrationtests;

import edu.kea.kab.model.Consumption;
import edu.kea.kab.model.User;
import edu.kea.kab.repository.ConsumptionRepository;
import edu.kea.kab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ConsumptionControllerAndRepositoryTest {

    @Autowired
    WebApplicationContext context;

    @Autowired
    MockMvc mvc;

    @Autowired
    ConsumptionRepository consumptionRepository;

    @Autowired
    UserService userService;

//    @Test
//    void canPullSumFromConsumptionTableAndShow() throws Exception {
//
//        MockHttpSession session = new MockHttpSession();
//        // instantiating a user object
//        User user = new User();
//        user.setEmail("nikiryom@mail.com");
//        user.setPassword("Hunter2");
//        user.setEnabled(true);
//        userService.addUser(user);
//        // Instantiating a consumption object and setting its values
//        Consumption consumption = new Consumption();
//        consumption.setVideoHours(5);
//        consumption.setMusicHours(10);
//        consumption.setMobileHours(2);
//        consumption.setUser(user);
//        consumption.setSession(session.getId());
//        consumptionRepository.save(consumption);
//
//        // Iterate through the Consumption table and save the consumption as an Optional
//        Optional<Consumption> consumptionById = consumptionRepository.findById(consumption.getId());
//
//        assertThat(consumptionById.equals(consumption));
//
//        mvc.perform(get("/results").with(user("nikiryom@mail.com").roles("USER")).with(csrf().asHeader()).session(session))
//                .andExpect(model().attributeExists("totalSum"));
//        // TODO: Finish this test
//    }

}
