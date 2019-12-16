package edu.kea.kab.unittests;

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
public class UserServiceTest {

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

    @Test
    void canSaveUserToDatabase() {

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
