package com.szkaminski.backend.service;

import com.szkaminski.backend.model.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Test
    public void getAllUsers() {
        //given
        UserService userService = mock(UserService.class);
        when(userService.getAllUsers()).thenReturn(prepareMockData());
        //when
        List<User> allUsers = userService.getAllUsers();
        //then
        Assert.assertThat(allUsers, Matchers.hasSize(1));
    }

    private List<User> prepareMockData() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("test@email.com", "test", "test123", "608608608"));
        return userList;
    }

    @Test
    public void addUser() {
        //given
        UserService userService = mock(UserService.class);
        Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(new User("test@email.com", "test", "test123", "608608608"));
        //when
        User user = userService.addUser(new User());
        //then
        Assert.assertEquals(user.getEmail(),"test@email.com");
    }

    @Test
    public void getByLogin() {
        //given
        UserService userService = mock(UserService.class);
        Mockito.when(userService.getByLogin(Mockito.anyString())).thenReturn(new User("test@email.com", "test", "test123", "608608608"));
        //when
        User user = userService.getByLogin(Mockito.anyString());
        //then
        Assert.assertEquals(user.getLogin(),"test");
    }
}