package com.szkaminski.frontend.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AuthorView {

    public static VerticalLayout getAuthorInfo() {
        VerticalLayout authorInfo = new VerticalLayout();
        authorInfo.add(new H3("About author"));

        authorInfo.add(new Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus arcu metus, " +
                        "faucibus vitae ornare a, laoreet mattis purus. Morbi eget nibh lacus. " +
                        "Suspendisse sit amet nunc aliquam, lacinia lorem eget, finibus orci. " +
                        "In volutpat viverra lacus vel maximus. Fusce id mi felis. Etiam eu aliquam dui, " +
                        "eget hendrerit libero. Proin imperdiet eget lectus eu efficitur. " +
                        "Donec sed tortor vitae nunc vulputate dignissim quis sed ipsum."
        ));
        authorInfo.add(new Label("If you stand for nothing, Burr, what’ll you fall for?"));
        authorInfo.add(new Text(
        "Fusce at dui at enim posuere vestibulum ut ut quam. In sed sagittis nunc. " +
                        "Donec eget eros in tellus egestas consequat in et leo. " +
                        "Suspendisse porttitor tempor libero eu finibus. Fusce orci augue, pellentesque in ipsum eu," +
                        " tempus iaculis elit. Donec tempor commodo turpis, ut efficitur libero laoreet quis. " +
                        "Maecenas hendrerit, sem at dignissim pellentesque, libero risus auctor est, " +
                        "nec sollicitudin justo arcu sit amet risus. Sed quis mauris mattis, blandit ante quis, " +
                        "elementum tellus. Morbi venenatis lectus sit amet eros fringilla, non placerat ex aliquet. " +
                        "Cras sem nisl, auctor vel nibh eu, dignissim laoreet magna. Nullam iaculis et mi sit amet mollis. " +
                        "Vestibulum ac odio mollis, tincidunt nisi ac, posuere tortor."
        ));
        return authorInfo;

    }
}
