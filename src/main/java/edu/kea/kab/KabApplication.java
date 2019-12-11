package edu.kea.kab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class KabApplication {

    public static void main(String[] args) {
        SpringApplication.run(KabApplication.class, args);
    }

}
