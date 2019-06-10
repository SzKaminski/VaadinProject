package com.szkaminski.frontend.views;

import com.szkaminski.backend.model.Comment;
import com.szkaminski.backend.model.User;
import com.szkaminski.backend.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sun.applet.Main;

import java.time.LocalDateTime;

@Component
public class CommentList extends UI {

    final
    UserService userService;

    @Autowired
    public CommentList(UserService userService) {
        this.userService = userService;
    }

    public void addComment() {
        Dialog commentDialog = new Dialog();
        commentDialog.open();

        TextArea text = new TextArea(null, "Type comment");
        Button saveButton = new Button("ADD");
        commentDialog.add(text, saveButton);

        saveButton.addClickListener(e -> {
            Comment comment = new Comment();
            comment.setContent(text.getValue());
            comment.setUser(MainView.getLoggedUser());
            comment.setLocalDateTime(LocalDateTime.now());
            userService.addComment(comment);

            commentDialog.close();
        });

    }

    public void getComments(Long id) {
        Dialog getComDialog = new Dialog();
        getComDialog.open();

        TextArea commentsArea = new TextArea();
        getComDialog.add(commentsArea);
        for (Comment e : userService.getById(id).getCommentsList()) {
            commentsArea.setValue(e.getContent() + " " + e.getLocalDateTime());
        }
    }
}
