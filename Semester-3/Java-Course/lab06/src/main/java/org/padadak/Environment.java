package org.padadak;

import org.padadak.interfaces.IEnvironment;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Environment implements IEnvironment {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    static String water = "5";
    private int port = 3001;
    private Map<Integer, String> rivers = new HashMap<>();

    public static void main(String[] args){
        Environment environment = new Environment();

        new Thread(() -> {
            try {
                environment.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            environment.clientStart();
        }
    }

    public void clientStart() {
        int p;
        int basinPort = 0;
        try {
            while (true){
                System.out.println("Please enter a port: ");
                reader = new BufferedReader(new InputStreamReader(System.in));
                p = Integer.valueOf(reader.readLine());
                if (rivers.containsKey(p)) {
                    basinPort = p;
                    break;
                }else{
                    System.out.println("Try again");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            try {
                clientSocket = new Socket(rivers.get(basinPort), basinPort);
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

                        if (word.startsWith("ars:")) {
                            String data = word.substring(4);
                            String[] parts = data.split(",");

                            int basinPort = Integer.parseInt(parts[0]);
                            String basinHost = parts[1];

                            assignRiverSection(basinPort, basinHost);
                            response = water;
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
    public void assignRiverSection(int port, String host) {
        rivers.put(port, host);
    }
}
