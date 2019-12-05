package edu.kea.kab.unittests;

import edu.kea.kab.controller.MainController;
import edu.kea.kab.repository.ConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
public class InputControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ConsumptionRepository consumptionRepository;

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
