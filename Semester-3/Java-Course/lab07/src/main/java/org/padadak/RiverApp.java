package org.padadak;

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

public class RiverApp extends Application implements IRiverSection {
    private int port;
    private int basinPort;
    private int env = 0;
    private int water = 0;

    private Label infoLabel = new Label();

    public RiverApp(int port, int basinPort) {
        this.port = port;
        this.basinPort = basinPort;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(20));
        root.getChildren().addAll(new Label("River (Port: " + port + ")"), infoLabel);

        IRiverSection cc = (IRiverSection) UnicastRemoteObject.exportObject(this, 0);
        Registry rmiRegistry = LocateRegistry.getRegistry("localhost", 1099);
        ITailor tailor = (ITailor) rmiRegistry.lookup("Tailor");

        tailor.register("River_" + port, cc);

        IEnvironment env = (IEnvironment) tailor.getRemote("Environment");
        if (env != null) env.assignRiverSection(cc);

        stage.setScene(new Scene(root, 300, 150));
        stage.setTitle("River " + port);
        stage.show();

        startLogic(tailor);
    }

    private void startLogic(ITailor tailor) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    int totalFlow = env + water;

                    IRetensionBasin targetBasin = (IRetensionBasin) tailor.getRemote("Basin_" + basinPort);
                    if (targetBasin != null) {
                        targetBasin.setWaterInflow(totalFlow, this);
                    }

                    Platform.runLater(() -> infoLabel.setText(
                            String.format("Rain: %d | From basin: %d\nTotal: %d", env, water, totalFlow)
                    ));
                } catch (Exception e) {
                    System.out.println("Waiting for Basin_" + basinPort);
                }
            }
        }).start();
    }

    @Override
    public void setRealDischarge(int realDischarge) throws RemoteException {
        this.water = realDischarge;
    }

    @Override
    public void setRainfall(int rainfall) throws RemoteException {
        this.env = rainfall;
    }

    @Override
    public void assignRetensionBasin(IRetensionBasin irb) throws RemoteException {
    }

    @Override
    public String getName() throws RemoteException {
        return "";
    }
}