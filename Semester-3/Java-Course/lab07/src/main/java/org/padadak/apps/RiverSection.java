package org.padadak.apps;

import interfaces.IEnvironment;
import interfaces.IRetensionBasin;
import interfaces.IRiverSection;
import interfaces.ITailor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RiverSection extends Application implements IRiverSection {
    private String name;
    private int h;
    private String basinName;
    private int env = 0;
    private int water = 0;

    private IRetensionBasin basin;
    private Label infoLabel = new Label();

    public RiverSection(int h, String name, String basinName) {
        this.name = name;
        this.basinName = basinName;
        this.h = h;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(20));
        root.getChildren().addAll(new Label(getName()), infoLabel);

        IRiverSection cc = (IRiverSection) UnicastRemoteObject.exportObject(this, 0);
        Registry rmiRegistry = LocateRegistry.getRegistry("localhost", 1099);
        ITailor tailor = (ITailor) rmiRegistry.lookup("Tailor");

        tailor.register(getName(), cc);

        IEnvironment envObj = (IEnvironment) tailor.getRemote("Environment");
        if (envObj != null) envObj.assignRiverSection(cc);

        stage.setScene(new Scene(root, 300, 150));
        stage.setTitle(getName());
        stage.show();

        startLogic(tailor);
    }

    private void startLogic(ITailor tailor) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(this.h);
                    int totalFlow = env + water;

                    if (basin == null) {
                        IRetensionBasin target = (IRetensionBasin) tailor.getRemote(basinName);
                        if (target != null) {
                            this.assignRetensionBasin(target);
                        }
                    }

                    if (basin != null) {
                        basin.setWaterInflow(totalFlow, this);
                    }

                    Platform.runLater(() -> infoLabel.setText(
                            String.format("Rainfall: " + env + " | From Basin: " +water + "\nAll: " + totalFlow)
                    ));
                } catch (Exception e) {
                    System.out.println("Waiting for " + basinName);
                }
            }
        }).start();
    }

    @Override public void setRealDischarge(int realDischarge) throws RemoteException {
        this.water = realDischarge;
    }
    @Override public void setRainfall(int rainfall) throws RemoteException {
        this.env = rainfall;
    }

    @Override
    public void assignRetensionBasin(IRetensionBasin irb) throws RemoteException {
        this.basin = irb;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }
}