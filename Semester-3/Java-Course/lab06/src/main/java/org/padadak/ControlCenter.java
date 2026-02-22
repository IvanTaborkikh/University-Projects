package org.padadak;

import org.padadak.interfaces.IControlerCenter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ControlCenter implements IControlerCenter {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    private int port = 3000;
    private Map<Integer, String> basins = new HashMap<>();

    public static void main(String[] args){
        ControlCenter controlCenter = new ControlCenter();

        new Thread(() -> {
            try {
                controlCenter.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            controlCenter.clientStart();
        }

    }

    public void clientStart() {
        int basinPort = 0;
        while (true) {
            System.out.println("Please enter a port: ");
            reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                String input = reader.readLine();
                int p = Integer.parseInt(input);

                if (basins.containsKey(p)) {
                    basinPort = p;
                    break;
                } else {
                    System.out.println("Port not found. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number! Try again.");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }

        try {
            try {
                clientSocket = new Socket(basins.get(basinPort), basinPort);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                while (true) {
                    String word = reader.readLine();
                    out.write(word + "\n");
                    out.flush();
                    String serverWord = in.readLine();
                    System.out.println(serverWord);
                    if(word.equals("exit"))
                        break;
                }
            } finally {
                if (clientSocket != null) clientSocket.close();
                if (in != null) in.close();
                if (out != null) out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void start() throws Exception {

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

                        if (word.startsWith("arb:")) {
                            String data = word.substring(4);
                            String[] parts = data.split(",");

                            int basinPort = Integer.parseInt(parts[0]);
                            String basinHost = parts[1];

                            assignRetensionBasin(basinPort, basinHost);
                            response = "OK";
                            System.out.println(basinPort + " is connected");

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


    @Override
    public void assignRetensionBasin(int port, String host) {
        basins.put(port, host);
    }
}