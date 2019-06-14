package com.szkaminski.backend.service;

import com.szkaminski.backend.model.CustomUserDetails;
import com.szkaminski.backend.model.User;
import com.szkaminski.backend.repositories.UserRepository;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByLogin(login));
        optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("login not found"));
        return optionalUser
                .map(CustomUserDetails::new).get();
    }

    public User update(User driver){
        if(userRepository.existsById(driver.getId())){
            return userRepository.save(driver);
        }
        else throw new NotFoundException("User not found");
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }
}
