package org.padadak.client.classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.padadak.client.objects.ShipTypeInPort;
import org.padadak.client.objects.ShipsInPorts;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {

    public static ShipsInPorts[] requestPorts() {

        ShipsInPorts[] list = new ShipsInPorts[0];

        try {
            URL url = new URL("https://api-transtat.stat.gov.pl/api/v1/C001MInd111p/?format=json");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            } else {

                try (InputStream inputStream = conn.getInputStream()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    list = objectMapper.readValue(inputStream, ShipsInPorts[].class);
                }
            }

        }catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    public static ShipTypeInPort[] requestPortTypes(String type) {

        ShipTypeInPort[] list = new ShipTypeInPort[0];

        try {
            URL url = new URL("https://api-transtat.stat.gov.pl/api/v1/C003MInd113p/SingleParamPl/" + type);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            } else {

                try (InputStream inputStream = conn.getInputStream()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    list = objectMapper.readValue(inputStream, ShipTypeInPort[].class);
                }

            }

        }catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }
}
