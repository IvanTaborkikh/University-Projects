module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires client;
    requires jdk.httpserver;

    exports org.padadak.gui;
}