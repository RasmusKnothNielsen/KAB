package edu.kea.kab.integrationtests;

import edu.kea.kab.model.Authorization;
import edu.kea.kab.model.Role;
import edu.kea.kab.model.User;
import edu.kea.kab.repository.AuthorizationRepository;
import edu.kea.kab.repository.UserRepository;
import edu.kea.kab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorizationRepository authorizationRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
    @Test
    void canSaveUserToDatabaseWithUserInput() throws Exception {

        // count is equal to 0 because it's a mock database and therefore the database is empty on startup.
        assertThat(userRepository.count()).isEqualTo(0);

        // Run the controller method "/adduser"
        // .param() is the object parameter
        mvc.perform(post("/adduser").with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("email", "niki@mail.com")
                .param("password", "Hunter2")
                .param("enabled", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // count is equal to 1 if the user has been created, because it's a mock database and therefore the database
        // is empty on startup.
        assertThat(userRepository.count()).isEqualTo(1);

        Iterable<User> users = userRepository.findAll();

        // Iterate through the Users table and test that the table Iterable isn't empty
        assertThat(users.iterator().hasNext()).isTrue();

        // Save the next object (The only one in the table)
        User savedUser = users.iterator().next();

        // Check to make sure the user is not null
        assertThat(savedUser).isNotNull();

        // Compare the user in the table with the original saved object.
        assertThat(savedUser.getEmail()).isEqualTo("niki@mail.com");
        assertThat(bCryptPasswordEncoder.matches("Hunter2", savedUser.getPassword()));
        assertThat(savedUser.isEnabled()).isEqualTo(true);

        // Iterate through the Authorization table and test that is is not empty
        Iterable<Authorization> authorizations = authorizationRepository.findAll();

        // Iterate through the Users table and test that the it isn't empty
        assertThat(authorizations.iterator().hasNext()).isTrue();

        // Save the next object (The only one in the table)
        Authorization savedAuthorization = authorizations.iterator().next();

        // Compare the Authorization in the table with the original saved object.
        assertThat(savedAuthorization.getRole()).isEqualTo(Role.ROLE_USER);
        assertThat(savedAuthorization.getUserId()).isEqualTo(savedUser.getId());

    }
*/
    @Test
    void canSaveUserToDatabase() throws Exception {

        // create a user
        User user = new User();
        user.setEmail("niki@mail.com");
        user.setPassword("Hunter2");
        user.setEnabled(true);

        // save user to the users table
        userService.addUser(user);

        Iterable<User> users = userRepository.findAll();

        // Iterate through the Users table and test that the it isn't empty
        assertThat(users.iterator().hasNext()).isTrue();

        // Save the next object (The only one in the table)
        User savedUser = users.iterator().next();


        // Compare the user in the table with the original saved object.
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("niki@mail.com");
        assertThat(bCryptPasswordEncoder.matches("Hunter2", savedUser.getPassword()));
        assertThat(savedUser.isEnabled()).isEqualTo(true);

        // Iterate through the Authorization table and test that is is not empty
        Iterable<Authorization> authorizations = authorizationRepository.findAll();

        // Iterate through the Users table and test that the it isn't empty
        assertThat(authorizations.iterator().hasNext()).isTrue();

        // Save the next object (The only one in the table)
        Authorization savedAuthorization = authorizations.iterator().next();

        // Compare the Authorization in the table with the original saved object.
        assertThat(savedAuthorization.getRole()).isEqualTo(Role.ROLE_USER);
        assertThat(savedAuthorization.getUserId()).isEqualTo(savedUser.getId());

    }

}
