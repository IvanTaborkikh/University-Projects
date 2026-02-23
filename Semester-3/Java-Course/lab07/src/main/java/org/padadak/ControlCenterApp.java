package org.padadak;

import interfaces.IControlCenter;
import interfaces.IRetensionBasin;
import interfaces.ITailor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ControlCenterApp extends Application implements IControlCenter {
    String ccname;
    private ObservableList<IRetensionBasin> basins = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>();

    protected ControlCenterApp(String ccname) throws RemoteException {
        this.ccname = ccname;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(15));

        Label label = new Label("Basin Monitoring:");

        TextField dischargeField = new TextField();
        dischargeField.setPromptText("New discharge (m3/s)");
        Button btnSet = new Button("Set discharge");

        btnSet.setOnAction(e -> {
            int selectedIdx = listView.getSelectionModel().getSelectedIndex();
            if (selectedIdx >= 0) {
                try {
                    int val = Integer.parseInt(dischargeField.getText());
                    basins.get(selectedIdx).setWaterDischarge(val);
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        root.getChildren().addAll(label, listView, dischargeField, btnSet);

        // RMI Registration
        IControlCenter cc = (IControlCenter) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        ITailor tailor = (ITailor) registry.lookup("Tailor");
        tailor.register(this.getName(), cc);

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Control Center");
        stage.show();

        startMonitoring();
    }

    private void startMonitoring() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(() -> {
                        ObservableList<String> items = FXCollections.observableArrayList();
                        for (IRetensionBasin b : basins) {
                            try {
                                items.add(b.getName() + ": Filling " + b.getFillingPercentage() + "%, Discharge: " + b.getWaterDischarge());
                            } catch (RemoteException e) { items.add("Basin unavailable"); }
                        }
                        listView.setItems(items);
                    });
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }).start();
    }

    @Override
    public void assignRetensionBasin(IRetensionBasin irb) throws RemoteException {
        basins.add(irb);
    }

    @Override
    public String getName() throws RemoteException {
        return this.ccname;
    }
}