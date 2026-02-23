package org.padadak;

import interfaces.IControlCenter;
import interfaces.IRetensionBasin;
import interfaces.IRiverSection;
import interfaces.ITailor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

public class BasinApp extends Application implements IRetensionBasin {
    private int myPort;
    private String riverName;
    private double vNow = 400;
    private double vMax = 1000;
    private int watergoingOut = 5;
    private int water;

    private ConcurrentHashMap<IRiverSection, Integer> inflows = new ConcurrentHashMap<>();
    private ProgressBar bar = new ProgressBar();
    private Label status = new Label();

    public BasinApp(int myPort, String riverName) {
        this.myPort = myPort;
        this.riverName = riverName;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(20));
        root.getChildren().addAll(new Label(getName()), bar, status);

        IRetensionBasin cc = (IRetensionBasin) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        ITailor tailor = (ITailor) registry.lookup("Tailor");

        tailor.register(getName(), cc);

        IControlCenter contr = (IControlCenter) tailor.getRemote("ControlCenter");
        if (contr != null) contr.assignRetensionBasin(cc);

        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle(getName());
        stage.show();

        startSimulation(tailor);
    }

    private void startSimulation(ITailor tailor) {
        Thread simulationThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    int totalInflow = inflows.values().stream().mapToInt(i -> i).sum();

                    synchronized (this) {

                        if (vNow >= vMax) {
                            vNow = vMax;
                            watergoingOut = totalInflow;
                        }
                        if (vNow < 0)
                            vNow = 0;
                        if (vNow == 0)
                            water = 0;
                        else if (vNow < watergoingOut)
                            water = (int) vNow;
                        else
                            water = watergoingOut;
                        vNow += (totalInflow - water);
                    }


                    IRiverSection targetRiver = (IRiverSection) tailor.getRemote(riverName);
                    if (targetRiver != null) {
                        targetRiver.setRealDischarge(water);
                    }

                    Platform.runLater(() -> {
                        bar.setProgress(vNow / vMax);
                        status.setText("Inflow: " + totalInflow + " | Outflow: " + water + " | vNow: " + vNow);
                    });
                } catch (Exception e) {
                    System.out.println(riverName);
                }
            }
        });
        simulationThread.setDaemon(true);
        simulationThread.start();
    }

    @Override public String getName() { return "Basin_" + myPort; }
    @Override public synchronized void setWaterInflow(int inflow, IRiverSection irs) { inflows.put(irs, inflow); }
    @Override public synchronized void setWaterDischarge(int d) { this.watergoingOut = d; }
    @Override public int getWaterDischarge() { return watergoingOut; }
    @Override public long getFillingPercentage() { return (long)((vNow/vMax)*100); }
    @Override public void assignRiverSection(IRiverSection irs) { }
}