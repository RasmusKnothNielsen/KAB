package edu.kea.kab.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kea.kab.model.Consumption;
import edu.kea.kab.repository.ConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataJpa
@AutoConfigureMockMvc
@SpringBootTest
public class SaveInputTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ConsumptionRepository consumptionRepository;

    private ObjectMapper mapper = new ObjectMapper();

    //Test if a newly created Consumption object is saved to the consumption table
    @Test
    void canSaveInputToDatabase() throws Exception {
        mvc.perform(post("/input").with(csrf().asHeader())
                .content(mapper.writeValueAsString(new Consumption())))
                .andExpect(status().isOk());
    }



}
