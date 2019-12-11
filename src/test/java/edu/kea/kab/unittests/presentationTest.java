package edu.kea.kab.unittests;

import edu.kea.kab.controller.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
public class presentationTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MainController mainController;

    @MockBean
    DataSource dataSource;

    @Test
    void canLoadAddUserPage() throws Exception{
        mvc
                .perform(get("/presentationofusage"))
                .andExpect(status().isOk());
    }
}