package com.szkaminski;

import com.szkaminski.backend.model.Comment;
import com.szkaminski.backend.model.User;
import com.szkaminski.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

    }

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("test@email.com", "test", "test123", "608608608");
        List<Comment> comments = new ArrayList<>();
        Comment testComment = new Comment();
        testComment.setLocalDateTime(LocalDateTime.now());
        testComment.setContent("testowy");
        testComment.setUser(user);
        comments.add(testComment);
        user.setCommentsList(comments);

        userService.addUser(user);
        System.out.println("Runner!");
    }
}