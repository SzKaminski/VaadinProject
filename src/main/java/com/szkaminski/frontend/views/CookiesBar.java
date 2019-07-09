package com.szkaminski.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinService;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class CookiesBar {

    public static HorizontalLayout getAcceptCookies() {
        HorizontalLayout cookiesLayout = new HorizontalLayout();
        Anchor cookiesPolicyAnchor = new Anchor("https://www.google.com/search?q=cookies+policy", "cookies policy");
        cookiesLayout.add(new Label("Please, accept"), cookiesPolicyAnchor, new Label("to continue"));
        Button acceptButton = new Button("Accept");
        cookiesLayout.add(acceptButton);
        cookiesLayout.setClassName("cookies-bar");

        if (VaadinService.getCurrentRequest().getCookies()!=null) {
            if (getCookieByName("AcceptCookie") != null) {
                cookiesLayout.setVisible(false);
            } else {
                cookiesLayout.setVisible(true);
            }
        }

        acceptButton.addClickListener(event -> {
            cookiesLayout.setVisible(false);
            Cookie acceptCookie = new Cookie("AcceptCookie", UUID.randomUUID().toString());
            VaadinService.getCurrentResponse().addCookie(acceptCookie);
        });

        acceptButton.setClassName("cookies-button");
        return cookiesLayout;
    }

    private static Cookie getCookieByName(String name) {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
}
