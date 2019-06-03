package com.szkaminski.frontend.test;

import com.szkaminski.backend.model.User;
import com.szkaminski.backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = "list")
public class UserList extends Div {

    private Grid<User> userGrid = new Grid<>();

    public UserList(@Autowired UserService userService) {
        HorizontalLayout horizontalGrid = new HorizontalLayout();
        horizontalGrid.add(fillTheGrid());
        horizontalGrid.setSizeFull();

        VerticalLayout vertical = new VerticalLayout();
        vertical.add(horizontalGrid);
        vertical.setSizeFull();
        add(vertical);
        List<User> userList = userService.getAllUsers();
        userGrid.setItems(userList);
    }

    private VerticalLayout fillTheGrid() {
        VerticalLayout container = new VerticalLayout();
        container.setClassName("view-container");
        container.setAlignItems(FlexComponent.Alignment.STRETCH);
        userGrid.addColumn(new ComponentRenderer<>(this::createUserImage))
                .setWidth("5em");
        userGrid.addColumn(User::getEmail)
                .setHeader("Email")
                .setWidth("12em");
        userGrid.addColumn(User::getLogin)
                .setHeader("Login")
                .setWidth("12em");
        userGrid.addColumn(User::getPassword)
                .setHeader("Password")
                .setWidth("12em");
        userGrid.addColumn(new ComponentRenderer<>(this::createCallButton))
                .setHeader("Phone")
                .setWidth("5em");
        userGrid.setSelectionMode(Grid.SelectionMode.NONE);
        container.add(userGrid);
        add(container);
        return container;
    }

    private Icon createUserImage() {
        return new Icon(VaadinIcon.FILE_PICTURE);
    }

    private Button createCallButton() {
        Button callButton = new Button("Call", event -> Notification.show("Not done yet"));
        callButton.setIcon(new Icon(VaadinIcon.PHONE));
        callButton.addClassName("call_button");

        return callButton;
    }
}
