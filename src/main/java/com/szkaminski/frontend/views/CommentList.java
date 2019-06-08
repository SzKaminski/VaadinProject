package com.szkaminski.frontend.views;

import com.szkaminski.backend.model.Comment;
import com.szkaminski.backend.model.User;
import com.szkaminski.backend.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentList extends UI {

    @Autowired
    UserService userService;

    public void addComment(Grid.Column<User> userId) {
        Dialog commentDialog = new Dialog();
        commentDialog.open();

        TextArea text = new TextArea(null,"Type comment");
        text.addKeyPressListener(Key.ENTER,event -> {
            Comment comment = new Comment();
            comment.setContent(text.getValue());
            comment.setUser(userService.getById(1L));
            comment.setLocalDateTime(LocalDateTime.now());
            userService.addComment(comment);
        });
        commentDialog.add(text);
    }
}
