package org.padadak.client;

import org.padadak.client.classes.Request;
import org.padadak.client.classes.SSLUtils;
import org.padadak.client.objects.ShipsInPorts;

public class Test {
    public static void main(String[] args) {
        SSLUtils.disableSSLVerification();
        Request req = new Request();
        ShipsInPorts[] list = req.requestPorts();

        for (ShipsInPorts port : list) {
            System.out.println("Date: " + port.getDate() +
                    " | Gdynia: " + port.getGdynia() +
                    " | Gdansk: " + port.getGdansk());
        }

        req.requestPortTypes("Passenger");
        req.requestPortTypes("Tanker");
    }
}