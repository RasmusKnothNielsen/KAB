package edu.kea.kab.unittests;

import edu.kea.kab.controller.MainController;
import edu.kea.kab.repository.ConsumptionRepository;
import edu.kea.kab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
public class InputControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ConsumptionRepository consumptionRepository;

    @MockBean
    UserService userService;

    @MockBean
    DataSource dataSource;

    @Test
    void canLoadInputPage() throws Exception{
        mvc
                .perform(get("/input"))
                .andExpect(status().isOk());
    }

    @Test
    void inputPageIsHtml() throws Exception {
        mvc
                .perform((get("/input")))
                .andExpect(content().contentTypeCompatibleWith("text/html"));
    }

    @Test
    void inputPageReturnInputView() throws Exception {
        mvc
                .perform((get("/input")))
                .andExpect(MockMvcResultMatchers.view().name("input"));
    }


}
