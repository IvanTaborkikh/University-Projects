package org.padadak.client;

import org.padadak.client.classes.Request;

public class Test {
    public static void main(String[] args) {
        Request req = new Request();
//        ShipsInPorts[] list = req.PortRequest();
//
//        for (ShipsInPorts port : list) {
//            System.out.println("Date: " + port.getDate() +
//                    " | Gdynia: " + port.getGdynia() +
//                    " | Gdansk: " + port.getGdansk());
//        }

        req.PortTypeRequest("Pasażerski");
        req.PortTypeRequest("Tankowiec");
    }
}