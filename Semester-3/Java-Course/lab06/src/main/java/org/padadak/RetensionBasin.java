package org.padadak;

import org.padadak.interfaces.IRetensionBasin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RetensionBasin implements IRetensionBasin {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    int vMax;
    int vNow;
    int watergoingOut;
    int watergoingIn;
    int port;
    int centerPort;
    String RiverHost;
    int RiverPort;

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]);
        RetensionBasin rs = new RetensionBasin(5000, port, 3000, 0, 0);
        rs.clientStart();
        rs.serverStart();
    }

    public RetensionBasin(int vMax, int port, int centerPort, int watergoingOut, int watergoingIn) {
        this.vMax = vMax;
        this.vNow = vMax / 2;
        this.port = port;
        this.centerPort = centerPort;
        this.watergoingOut = watergoingOut;
        this.watergoingIn = watergoingIn;
    }

    public void clientStart() {
        try {

            clientSocket = new Socket("localhost", this.centerPort);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            out.write("arb:" + this.port + ",localhost" + "\n");
            out.flush();
            String serverWord = in.readLine();
            System.out.println("Center: " + serverWord);

            out.close();
            in.close();
            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waterStart()
    {
        try {

            clientSocket = new Socket(this.RiverHost, this.RiverPort);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            out.write("srd:" + this.watergoingOut + "\n");
            out.flush();
            String serverWord = in.readLine();
            System.out.println("River: " + serverWord);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void serverStart() throws Exception {
        startSimulation();

        server = new ServerSocket(this.port);
        System.out.println("Server is working");

        while (true) {
            Socket clientSocket = server.accept();

            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream()));

                    while (true) {
                        String word = in.readLine();
                        if (word == null) break;

                        System.out.println(word);
                        String response;

                        if (word.equals("gwd")) {
                            response = String.valueOf(getWaterDischarge());
                        } else if (word.startsWith("swd:")) {
                            int value = Integer.parseInt(word.substring(4));
                            setWaterDischarge(value);
                            if (this.RiverHost != null && this.RiverPort != 0) {
                                waterStart();
                            }
                            response = "OK";
                        }
                        else if (word.startsWith("swi:")) {
                            String data = word.substring(4);
                            String[] parts = data.split(",");
                            int inflow = Integer.parseInt(parts[0]);
                            int riverPort = Integer.parseInt(parts[1]);
                            setWaterInflow(inflow, riverPort);
                            response = "OK";
                            Thread.sleep(1000);
                        }else if (word.startsWith("ars:")) {
                            String data = word.substring(4);
                            String[] parts = data.split(",");

                            int riverPort = Integer.parseInt(parts[0]);
                            String riverHost = parts[1];

                            assignRiverSection(riverPort, riverHost);
                            response = "OK";
                        }
                        else if (word.equals("gpf")) {
                            response = String.valueOf(getFillingPercentage());
                        } else if (word.equals("exit")) {
                            response = "bye";
                            out.write(response + "\n");
                            out.flush();
                            break;
                        } else {
                            response = "Error";
                        }

                        out.write(response + "\n");
                        out.flush();
                    }

                    in.close();
                    out.close();
                    clientSocket.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


    private void startSimulation() {
        Thread simulationThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (this) {
                    vNow = vNow - watergoingOut;
                    if (vNow < 0) {
                        vNow = 0;
                    }
                    if (vNow < watergoingOut) {
                        setWaterDischarge(vNow);
                        if (this.RiverHost != null && this.RiverPort != 0) {
                            waterStart();
                        }
                    }
                    System.out.println("Simulation: vNow = " + vNow);
                }
            }
        });
        simulationThread.setDaemon(true);
        simulationThread.start();
    }



    @Override
    public synchronized int getWaterDischarge() {
        return this.vNow;
    }

    @Override
    public synchronized long getFillingPercentage() {
        return this.watergoingOut;
    }

    @Override
    public synchronized void setWaterDischarge(int watergoing) {
        this.watergoingOut = watergoing;
    }

    @Override
    public synchronized void setWaterInflow(int watergoing, int port) {
        this.vNow += watergoing;
    }

    @Override
    public void assignRiverSection(int port, String host) {
        this.RiverPort = port;
        this.RiverHost = host;
        this.waterStart();
        System.out.println("River assigned: " + host + ":" + port);
    }
}
