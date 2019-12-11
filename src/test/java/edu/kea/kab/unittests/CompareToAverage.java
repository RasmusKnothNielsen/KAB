package edu.kea.kab.unittests;

import edu.kea.kab.controller.MainController;
import edu.kea.kab.repository.ConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CompareToAverage {

    @Autowired
    MockMvc mvc;

    @Autowired
    ConsumptionRepository consumptionRepository;

    @Autowired
    MainController mainController;

    // Test if the right percentage is calculated with a given entry
    @Test
    public void correctCalculation() {


    }
}
