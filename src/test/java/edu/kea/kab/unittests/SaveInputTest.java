package edu.kea.kab.unittests;

import edu.kea.kab.model.Categories;
import edu.kea.kab.model.Consumption;
import edu.kea.kab.repository.ConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class SaveInputTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ConsumptionRepository consumptionRepository;

    // Test if a newly created Consumption object is saved to the consumption table
    @Test
    void canSaveInputToDatabase() throws Exception {

        // count is equal to 0 because it's a mock database and therefore the database is empty on startup.
        assertThat(consumptionRepository.count()).isEqualTo(0);

        // Run the controller method "/input"
        // .param() is the object parameter
        mvc.perform(post("/input").with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("category", "VIDEO")
                .param("hoursStreamed", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());

        // count is equal to 1 because it's a mock database and therefore the database is empty on startup.
        assertThat(consumptionRepository.count()).isEqualTo(1);

        Iterable<Consumption> consumptions = consumptionRepository.findAll();

        // iterate through the Consumption table
        assertThat(consumptions.iterator().hasNext()).isTrue();

        // save the next object.
        Consumption savedConsumptions = consumptions.iterator().next();
        assertThat(savedConsumptions).isNotNull();

        // compare the object to the original saved object
        assertThat(savedConsumptions.getCategory()).isEqualTo(Categories.VIDEO);
        assertThat(savedConsumptions.getHoursStreamed()).isEqualTo(10);
    }





}
