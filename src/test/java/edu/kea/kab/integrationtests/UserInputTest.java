package edu.kea.kab.integrationtests;

import edu.kea.kab.model.Consumption;
import edu.kea.kab.repository.ConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;

import javax.transaction.Transactional;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class UserInputTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ConsumptionRepository consumptionRepository;


    //TODO add test to see if the correct data is extracted from the database.
    @Test
    void canRetrieveUserData() throws Exception {

        String stringSessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        // count is equal to 0 because it's a mock database and therefore the database is empty on startup.
        long count= consumptionRepository.count();

        // Run the controller method "/input"
        // .param() is the object parameter
        mvc.perform(post("/input").with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("videoHours", "2")
                .param("musicHours", "3")
                .param("mobileHours", "5")
                .param("session", stringSessionId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        // count is equal to 1 because it's a mock database and therefore the database is empty on startup.
        assertThat(consumptionRepository.count()).isEqualTo(count+1);


        // Pull the consumption object from the database
        Iterator iterator = consumptionRepository.findAll().iterator();
        Consumption consumption = (Consumption) iterator.next();

        // Create mock object, to compare with consumption object from database
        Consumption mockConsumption = new Consumption();
        mockConsumption.setSession(stringSessionId);
        mockConsumption.setVideoHours(2);
        mockConsumption.setMusicHours(3);
        mockConsumption.setMobileHours(5);

        assertThat(consumption.equals(mockConsumption));

        // Delete consumption entity from test database
        consumptionRepository.delete(consumption);

    }
}