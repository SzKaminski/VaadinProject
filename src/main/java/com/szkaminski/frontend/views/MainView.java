package com.szkaminski.frontend.views;

import com.szkaminski.backend.model.User;
import com.szkaminski.backend.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "")
public class MainView extends Div {

    private H2 welcome;
    private Grid<User> userGrid = new Grid<>();
    private HorizontalLayout navbar;
    private Dialog registerDialog, loginDialog;


    public MainView(@Autowired UserService userService) {
        welcome = new H2("Hello World... I mean User");
        navbar = new HorizontalLayout();
        navbar.setWidth("90%");

        add(welcome);

        HorizontalLayout menu = new HorizontalLayout();

        menu.add(login(userService));
        menu.add(register(userService));

        navbar.add(menu);
        add(navbar);
        addContent();
        List<User> userList = userService.getAllUsers();
        userGrid.setItems(userList);
    }

    private void addContent() {
        VerticalLayout container = new VerticalLayout();
        container.setClassName("view-container");
        container.setAlignItems(FlexComponent.Alignment.STRETCH);

        userGrid.addColumn(User::getId)
                .setHeader("Id")
                .setWidth("4em");
        userGrid.addColumn(User::getEmail)
                .setHeader("Email")
                .setWidth("12em");
        userGrid.addColumn(User::getLogin)
                .setHeader("Login")
                .setWidth("12em");
        userGrid.addColumn(User::getCommentsList)
                .setHeader("Comments")
                .setWidth("12em");
        userGrid.addColumn(new ComponentRenderer<>(this::createNewCommentButton))
                .setHeader("Add Comment")
                .setWidth("5em");
        userGrid.setSelectionMode(Grid.SelectionMode.NONE);

        container.add(userGrid);
        add(container);
    }

    private Button createNewCommentButton() {
        Button newCommentButton = new Button("Add", event -> new CommentList().addComment(this.userGrid.getColumnByKey("Id")));
        newCommentButton.setIcon(new Icon(VaadinIcon.COMMENT));
        newCommentButton.addClassName("call_button");

        return newCommentButton;
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
                    loginDialog.close();

                    UI.getCurrent().getPage().reload();
                    Notification.show("Login correct");
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
}
