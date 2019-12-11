package edu.kea.kab.service;

import edu.kea.kab.model.Authorization;
import edu.kea.kab.model.Role;
import edu.kea.kab.model.User;
import edu.kea.kab.repository.AuthorizationRepository;
import edu.kea.kab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    AuthorizationRepository authorizationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // Add a user to the DB, giving that user a ROLE_USER in the authorization table
    public void addUser(User user) {

        // Set the password to the password given by the user and encode it
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        // Instantiating a Authorization object and setting its userId to match the new user and role to user
        Authorization authorization = new Authorization();
        authorization.setUserId(user.getId());        authorization.setRole(Role.ROLE_USER);
        authorizationRepository.save(authorization);

    }

    public Long getId(String email) {
        return userRepository.getByEmail(email).getId();
    }

}
