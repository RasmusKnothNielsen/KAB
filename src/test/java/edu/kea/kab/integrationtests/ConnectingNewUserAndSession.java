package edu.kea.kab.integrationtests;

import edu.kea.kab.model.Consumption;
import edu.kea.kab.model.User;
import edu.kea.kab.repository.ConsumptionRepository;
import edu.kea.kab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ConnectingNewUserAndSession {

    @Autowired
    MockMvc mvc;

    @Autowired
    ConsumptionRepository consumptionRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void ifUserRegistersAfterInputTheInputIsAssignedToThatUser() throws Exception {

        final String USER_EMAIL="niki@mail.com";

        MockHttpSession session = new MockHttpSession();

        mvc.perform(post("/input").with(csrf().asHeader())
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("videoHours", "2")
                .param("musicHours", "3")
                .param("mobileHours", "5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());

        mvc.perform(post("/adduser").with(csrf().asHeader())
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("email", USER_EMAIL)
                .param("password", "Hunter2")
                .param("enabled", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Iterator<Consumption> consumptionIterator = consumptionRepository.findAll().iterator();
        Consumption consumption = consumptionIterator.next();

        assertThat(consumption.getUser().getEmail()).isEqualTo(USER_EMAIL);

    }
}
