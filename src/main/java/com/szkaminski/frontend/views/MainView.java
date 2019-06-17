package com.szkaminski.frontend.views;

import com.szkaminski.backend.configurations.WebSecurityConfiguration;
import com.szkaminski.backend.model.User;
import com.szkaminski.backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "")
@HtmlImport("frontend://shared-styles.html")
public class MainView extends Div {

    private H2 welcome;
    private Grid<User> userGrid = new Grid<>();
    private HorizontalLayout navbar;
    private WebSecurityConfiguration webSecurityConfiguration;



    public MainView(@Autowired UserService userService) {
        webSecurityConfiguration = new WebSecurityConfiguration();
        welcome = new H2("Hello World... I mean User");
        navbar = new HorizontalLayout();
        navbar.setWidth("90%");

        add(welcome);
        add(CookiesBar.getAcceptCookies());

        navbar.add(new MenuBar(userService));
        add(navbar);
        addContent(userService);
        List<User> userList = userService.getAllUsers();
        userGrid.setItems(userList);
    }

    private void addContent(UserService userService) {
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
        userGrid.addColumn(new ComponentRenderer<>(() -> createGetCommentsButton(userService)))
                .setHeader("Get Comment")
                .setWidth("5em");
        userGrid.addColumn(new ComponentRenderer<>(() -> createNewCommentButton(userService)))
                .setHeader("Add Comment")
                .setWidth("5em");
        userGrid.setSelectionMode(Grid.SelectionMode.NONE);

        container.add(userGrid);
        add(container);
    }

    private Button createGetCommentsButton(UserService userService) {
        Button getCommentsButton = new Button("Show Comments", event -> new CommentList(userService).getComments(1L));
        getCommentsButton.setIcon(new Icon((VaadinIcon.COMMENTS)));
        getCommentsButton.addClassName("call_button");
        return getCommentsButton;
    }

    private Button createNewCommentButton(UserService userService) {
        Button newCommentButton = new Button("Add", event -> new CommentList(userService).addComment());
        newCommentButton.setIcon(new Icon(VaadinIcon.COMMENT));
        newCommentButton.addClassName("call_button");

        return newCommentButton;
    }


}
