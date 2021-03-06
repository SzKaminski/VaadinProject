package com.szkaminski.frontend.views;

import com.szkaminski.backend.model.Comment;
import com.szkaminski.backend.model.User;
import com.szkaminski.backend.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentList extends UI {

    final
    UserService userService;

    @Autowired
    public CommentList(UserService userService) {
        this.userService = userService;
    }

    public void addComment(UserService userService) {
        Dialog commentDialog = new Dialog();
        commentDialog.open();

        TextArea text = new TextArea(null, "Type comment");
        Button saveButton = new Button("ADD");
        commentDialog.add(text, saveButton);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User byLogin = userService.getByLogin(name);

        saveButton.addClickListener(e -> {
            Comment comment = new Comment(byLogin,text.getValue(),LocalDateTime.now());
            byLogin.getCommentsList().add(comment);
            commentDialog.close();
        });

    }

    public void getComments(Long id) {
        Dialog getComDialog = new Dialog();
        VerticalLayout vl = new VerticalLayout();
        getComDialog.add(vl);
        getComDialog.open();

        for (Comment e : userService.getById(id).getCommentsList()) {
            TextArea newComment = new TextArea(e.getUser().getLogin());
            newComment.setValue(e.getContent() + "\n" + e.getLocalDateTime());
            vl.add(newComment);
        }
    }
}
