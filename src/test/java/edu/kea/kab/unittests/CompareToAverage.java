package edu.kea.kab.unittests;


import edu.kea.kab.repository.ConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CompareToAverage {

    @Autowired
    MockMvc mvc;

    @Autowired
    ConsumptionRepository consumptionRepository;

    // Test if the right percentage is calculated with a given entry
    @Test
    void correctCalculation() throws Exception {

        MockHttpSession session = new MockHttpSession();
        String stringSessionId = session.getId();

        mvc.perform(post("/input").with(csrf().asHeader())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param("videoHours", "10")
            .param("musicHours", "10")
            .param("mobileHours", "10")
            .param("session", stringSessionId)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isFound());

        // TODO implement the rest of calculation check


    }
}
