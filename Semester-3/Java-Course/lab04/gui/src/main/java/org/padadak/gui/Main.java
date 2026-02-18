package org.padadak.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.padadak.client.classes.SSLUtils;
import org.padadak.gui.graf.Graf;


public class Main extends Application {

    public void start(Stage stage) {

        Graf graph = new Graf();
        Label title = new Label("Ship Detector by Year");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        ComboBox<String> portSelector = new ComboBox<>();
        portSelector.getItems().addAll("Gdansk", "Gdynia", "Szczecin", "Swinoujscie");
        portSelector.setPromptText("Select Port");

        ComboBox<String> yearSelector = new ComboBox<>();
        yearSelector.getItems().addAll( "2019", "2020", "2021", "2022", "2023");
        yearSelector.setPromptText("Select Year");

        Button loadBtn = new Button("Load Data");
        Button loadTypeBtn = new Button("Load Ship Type Data");

        HBox controls = new HBox(15, portSelector, yearSelector, loadBtn, loadTypeBtn);
        controls.setPadding(new Insets(10));

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setPrefHeight(300);

        VBox topArea = new VBox(title, controls);
        topArea.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(topArea);
        root.setBottom(output);

        loadBtn.setOnAction(e -> {
            String selectedPort = portSelector.getValue();
            String selectedYear = yearSelector.getValue();

            if (selectedPort != null && selectedYear != null) {
                output.appendText("\nData for port: " + selectedPort + " year: " + selectedYear);
                graph.showChart(selectedPort, selectedYear);
            }else {
                output.appendText("\nNo year or port name found");
            }
        });

        loadTypeBtn.setOnAction(e -> {
            String selectedPort = portSelector.getValue();
            String selectedYear = yearSelector.getValue();

            if (selectedPort != null && selectedYear != null) {
                output.appendText("\nData for port by ship type: " + selectedPort + " year: " + selectedYear);
                graph.showTypeChart(selectedPort, selectedYear);
            } else {
                output.appendText("\nNo year or port name found");
            }
        });

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Ship Detector by Year");
        stage.show();
    }

    public static void main(String[] args) {
        // WARNING: DO NOT USE IN PRODUCTION. This disables security checks.
        SSLUtils.disableSSLVerification();
        launch();
    }
}