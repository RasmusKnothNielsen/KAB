package edu.kea.kab.unittests;

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

    @Test
    void canSaveInputToDatabase() throws Exception {
        mvc.perform(post("/input").with(csrf().asHeader()).content().contentType(MediaType.))
                .andExpect(status().isOk());
    }

}
