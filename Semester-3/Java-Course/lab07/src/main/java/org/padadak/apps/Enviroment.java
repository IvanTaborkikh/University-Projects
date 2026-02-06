package org.padadak.apps;

import interfaces.IEnvironment;
import interfaces.IRiverSection;
import interfaces.ITailor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enviroment extends Application implements IEnvironment {
    String ccname;
    private List<IRiverSection> rivers = new ArrayList<>();
    private TextArea log = new TextArea();

    public Enviroment(String ccname) throws RemoteException {
        this.ccname = ccname;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(10);
        root.getChildren().addAll(new Label("Enviroment"), log);

        IEnvironment cc = (IEnvironment) UnicastRemoteObject.exportObject(this, 0);
        Registry rmiRegistry = LocateRegistry.getRegistry("localhost", 1099);
        ITailor tailor = (ITailor) rmiRegistry.lookup("Tailor");
        tailor.register("Environment", cc);

        stage.setScene(new Scene(root, 300, 300));
        stage.setTitle("Environment");
        stage.show();

        startWeatherSimulation();
    }

    private void startWeatherSimulation() {
        new Thread(() -> {
            Random r = new Random();
            while (true) {
                try {
                    Thread.sleep(5000);
                    int rainfall = r.nextInt(20);
                    for (IRiverSection river : rivers) {
                        river.setRainfall(rainfall);
                    }
                    Platform.runLater(() -> log.appendText("Rainfall: " + rainfall + " m3/s\n"));
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }

    @Override
    public void assignRiverSection(IRiverSection irs) throws RemoteException {
        rivers.add(irs);
    }

    @Override
    public String getName() throws RemoteException {
        return this.ccname;
    }
}
