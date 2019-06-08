package com.szkaminski.backend.repositories;


import com.szkaminski.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User where login like %:login%")
    User findByLogin(@Param("login") String login);

    User getById(Long id);
}
