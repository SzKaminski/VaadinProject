package com.szkaminski.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String login;
    private String password;
    private String phoneNumber;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> commentsList;

    public User(String email, String login, String password, String phoneNumber) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
