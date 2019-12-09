package edu.kea.kab.integrationtests;

import edu.kea.kab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class userServiceIntegrationTest {

    @Autowired
    UserService userService;


    @Test
    void canSaveUserToDatabase() {



    }

}
