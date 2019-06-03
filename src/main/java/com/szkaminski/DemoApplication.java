package com.szkaminski;

import com.szkaminski.backend.model.User;
import com.szkaminski.backend.repositories.UserRepository;
import com.szkaminski.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

    }
}

@Component
class DemoCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.addUser(new User("test@email.com", "test", "test123", "608608608"));
        System.out.println("Runner!");
    }
}