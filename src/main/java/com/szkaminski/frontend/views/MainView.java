package com.szkaminski.frontend.views;

import com.szkaminski.backend.model.PageAnaliticsSingleton;
import com.szkaminski.backend.model.User;
import com.szkaminski.backend.service.AnaliticsService;
import com.szkaminski.backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "")
@HtmlImport("frontend://shared-styles.html")
public class MainView extends Div {

    private HorizontalLayout helloCounterBar;
    private int counter;
    private Grid<User> userGrid = new Grid<>();
    private HorizontalLayout navbar;
    private static VerticalLayout userpanel;

    public MainView(@Autowired UserService userService, @Autowired AnaliticsService analiticsService) {
        helloCounterBar = new HorizontalLayout();
        HorizontalLayout counterdiv = new HorizontalLayout();

        // counter = PageAnaliticsSingleton.getINSTANCE().getVisitCounter();
        //todo: null pointer
        try {
            counter = analiticsService.getVisitCounter();
        }catch (Exception e){
            counter = 0;
        }
        PageAnaliticsSingleton.getINSTANCE().setVisitCounter(counter + 1);
        analiticsService.updateAnalitics();

        counterdiv.setClassName("counterdiv");
        Icon thumbupicon = new Icon(VaadinIcon.THUMBS_UP);
        Icon thumbsdownicon = new Icon(VaadinIcon.THUMBS_DOWN);
        thumbupicon.setClassName("icon");
        thumbsdownicon.setClassName("icon");

        counterdiv.add(
                new H4("Page visitor counter: " + counter),
                new H4("" + analiticsService.getLikeCounter()), thumbupicon,
                new H4("" + analiticsService.getNotLikeCounter()), thumbsdownicon);

        H2 welcome = new H2("Hello World... I mean User");
        helloCounterBar.add(welcome, counterdiv);
        navbar = new HorizontalLayout();
        userpanel = new VerticalLayout();
        userpanel.setVisible(false);
        navbar.setWidth("90%");

        add(helloCounterBar);
        add(CookiesBar.getAcceptCookies());

        navbar.add(MenuBar.getContent(userService, analiticsService));
        add(navbar);
        add(userpanel);
        add(AuthorView.getAuthorInfo());
        add(addContent(userService));
        List<User> userList = userService.getAllUsers();
        userGrid.setItems(userList);
    }

    public static void setUserpanelVisible(UserService userService, AnaliticsService analiticsService) {
        userpanel.setVisible(true);
        userpanel.add(new H4("User Panel"));
        HorizontalLayout userPanelHor = new HorizontalLayout();
        userPanelHor.add(new Button(new Icon(VaadinIcon.USER)));
        userPanelHor.add(createNewCommentButton(userService));
        Button facebookButton = new Button(new Icon(VaadinIcon.FACEBOOK));
        Button thumbsUpButton = new Button(new Icon(VaadinIcon.THUMBS_UP), event -> {
            PageAnaliticsSingleton.getINSTANCE().setLikeCounter(PageAnaliticsSingleton.getINSTANCE().getLikeCounter() + 1);
            analiticsService.updateAnalitics();
        });
        Button thumbsDownButton = new Button(new Icon(VaadinIcon.THUMBS_DOWN), event -> {
            PageAnaliticsSingleton.getINSTANCE().setNotLikeCounter(PageAnaliticsSingleton.getINSTANCE().getNotLikeCounter() + 1);
            analiticsService.updateAnalitics();
        });
        RadioButtonGroup<Button> buttonGroup = new RadioButtonGroup<>();
        buttonGroup.add(facebookButton, thumbsUpButton, thumbsDownButton);
        userPanelHor.add(buttonGroup);

        userpanel.add(userPanelHor);
    }

    private VerticalLayout addContent(UserService userService) {
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
        userGrid.setSelectionMode(Grid.SelectionMode.NONE);

        container.add(userGrid);
        return container;
    }

    private Button createGetCommentsButton(UserService userService) {
        Button getCommentsButton = new Button("Show Comments", event -> new CommentList(userService).getComments(1L));
        getCommentsButton.setIcon(new Icon((VaadinIcon.COMMENTS)));
        getCommentsButton.addClassName("call_button");
        return getCommentsButton;
    }

    private static Button createNewCommentButton(UserService userService) {
        Button newCommentButton = new Button("Add", event -> new CommentList(userService).addComment(userService));
        newCommentButton.setIcon(new Icon(VaadinIcon.COMMENT));
        newCommentButton.addClassName("call_button");

        return newCommentButton;
    }


}
