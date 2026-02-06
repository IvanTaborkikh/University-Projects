package org.padadak;

import org.padadak.interfaces.IRiverSection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RiverSection implements IRiverSection {

    private  Socket clientSocket;
    private  ServerSocket server;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    private int water;
    private int late;
    private int Env;
    private int port;
    private int waterWithEnv;
    private int basinPort;
    private String basinHost;

    public RiverSection(int water, int port, int waterWithEnv, int Env, int late) {
        this.water = water;
        this.port = port;
        this.waterWithEnv = waterWithEnv;
        this.Env = Env;
        this.late = late;
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        int late = Integer.parseInt(args[1]);
        RiverSection rs1 = new RiverSection(0, port, 0, 3001, late);

        rs1.connect();
        rs1.serverStart();
    }

    public void connect() {
        try {

            clientSocket = new Socket("localhost", this.Env);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            out.write("ars:" + this.port + ",localhost" + "\n");
            out.flush();
            String serverWord = in.readLine();
            System.out.println("Env: " + serverWord);
            setRainfall(Integer.parseInt(serverWord));

            out.close();
            in.close();
            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clientStart() {
        try {
            try {
                clientSocket = new Socket(this.basinHost, this.basinPort);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                while (true) {
                    Thread.sleep(late);
                    int waters = this.water + this.waterWithEnv;
                    out.write("swi:" + waters + ","+ this.port + "\n");
                    System.out.println("swi:" + waters + ","+ this.port + "\n");
                    out.flush();
                    String serverWord = in.readLine();
                    System.out.println(serverWord);
                }


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public void serverStart() {
        try {
            ServerSocket server = new ServerSocket(this.port);
            System.out.println("River port" + this.port);

            while (true) {
                Socket socket = server.accept();

                new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        BufferedWriter out = new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream()));

                        while (true) {
                            String word = in.readLine();
                            if (word == null) break;

                            System.out.println("River got: " + word);

                            if (word.startsWith("srd:")) {
                                int value = Integer.parseInt(word.substring(4));
                                setRealDischarge(value);
                                out.write("OK\n");
                            } else if (word.startsWith("srf:")) {
                                int value = Integer.parseInt(word.substring(4));
                                setRainfall(value);
                                out.write("rainfall\n");
                                Thread.sleep(1000);
                            }else if (word.startsWith("arb:")) {
                                String data = word.substring(4);
                                String[] parts = data.split(",");

                                int basinPort = Integer.parseInt(parts[0]);
                                String basinHost = parts[1];

                                assignRetensionBasin(basinPort, basinHost);

                                out.write("OK\n");
                                System.out.println(basinPort + " is connected");
                                new Thread(() -> clientStart()).start();

                            }
                            else {
                                out.write("Error\n");
                            }
                            out.flush();
                        }

                        socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void setRealDischarge(int realDischarge) {
        this.water = realDischarge;
        System.out.println("River: " + realDischarge);
    }

    @Override
    public synchronized void setRainfall(int env) {
        this.waterWithEnv = env;
    }

    @Override
    public void assignRetensionBasin(int port, String host) {
        this.basinPort = port;
        this.basinHost = host;
    }
}
