package com.szkaminski.frontend.views;

import com.szkaminski.backend.model.User;
import com.szkaminski.backend.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuBar extends HorizontalLayout {

    private Dialog registerDialog, loginDialog;
    private static User loggedUser;

    public MenuBar(@Autowired UserService userService) {

        HorizontalLayout menu = new HorizontalLayout();

        if (loggedUser != null) {
            menu.add(logout(userService));
        } else {
            menu.add(login(userService));
            menu.add(register(userService));
        }
        add(menu);
    }

    private Button logout(UserService userService) {
        Button logoutButton = new Button("Logout",
                e -> {
                    loggedUser = null;
                    UI.getCurrent().getPage().reload();
                });

        return logoutButton;
    }

    public Button login(UserService userService) {
        loginDialog = new Dialog();
        loginDialog.setWidth("auto");
        loginDialog.add(loginPattern(userService));
        Button loginButton = new Button("Login",
                e -> loginDialog.open());
        return loginButton;
    }

    public Button register(UserService userService) {
        registerDialog = new Dialog();
        registerDialog.setWidth("auto");
        registerDialog.add(registerPattern(userService));
        Button registerButton = new Button("Register");
        registerButton.addClickListener(event -> registerDialog.open());
        return registerButton;
    }

    public VerticalLayout registerPattern(UserService userService) {
        VerticalLayout registerLayout = new VerticalLayout();
        TextArea type_email = new TextArea("type email");
        TextArea type_login = new TextArea("type login");
        TextArea type_phoneNumber = new TextArea("type phone number");
        PasswordField type_password = new PasswordField("type password");
        PasswordField confirm_password = new PasswordField("confirm password");
        Checkbox checkbox = new Checkbox("Correct");
        Div passwordConfirm = new Div(confirm_password, checkbox);
        Button registerButton = new Button("Register");
        registerLayout.add(type_email, type_login, type_phoneNumber, type_password, passwordConfirm, registerButton);
        checkbox.setReadOnly(true);
        registerButton.addClickListener(e -> {
            if (!type_password.getValue().equals(confirm_password.getValue())) {
                Notification.show("Password uncorrect");
                checkbox.setIndeterminate(true);
            } else {
                checkbox.setValue(true);
                userService.addUser(new User(
                        type_email.getValue(),
                        type_login.getValue(),
                        type_password.getValue(),
                        type_phoneNumber.getValue()));
                registerDialog.close();
                UI.getCurrent().getPage().reload();
            }
        });
        return registerLayout;
    }

    public VerticalLayout loginPattern(UserService userService) {
        VerticalLayout loginLayout = new VerticalLayout();
        TextArea type_login = new TextArea("type login");
        PasswordField type_password = new PasswordField("type password");

        Button loginButton = new Button("Login");

        loginButton.addClickListener(e -> {
            String value = type_login.getValue();
            User userFound = userService.getByLogin(value);
            try {
                if (userFound.getPassword().equals(type_password.getValue())) {
                    /*Authentication auth = new UsernamePasswordAuthenticationToken(type_login.getValue(),type_password.getValue());
                    Authentication authenticated = webSecurityConfiguration.authenticationProvider().authenticate(auth);
                    SecurityContextHolder.getContext().setAuthentication(authenticated);*/
                    loggedUser = userFound;
                    Notification.show("Login correct");
                    loginDialog.close();
                    UI.getCurrent().getPage().reload();
                } else {
                    Notification.show("Password uncorrect");
                }
            } catch (NullPointerException exception) {
                Notification.show("User not found");
            }
        });

        loginLayout.add(type_login, type_password, loginButton);
        return loginLayout;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

}
