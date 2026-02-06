package org.padadak.gui.graf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.padadak.client.classes.Request;
import org.padadak.client.objects.ShipTypeInPort;
import org.padadak.client.objects.ShipsInPorts;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Graf {

    public void ShowChart(String port, String year) {

        Request req = new Request();
        ShipsInPorts[] data = req.PortRequest();

        ObservableList<ShipsInPorts> portList = FXCollections.observableArrayList(data);
        ObservableList<ShipsInPorts> filtered = portList.filtered(sp -> sp.getDate().startsWith(year));

        Stage stage = new Stage();
        stage.setTitle("Ship in " + port + " in " + year);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Days");
        yAxis.setLabel("Quantity of ships");


        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Ships in " + port + " in " + year);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(port);

        for (ShipsInPorts sp : filtered) {
            LocalDate list = LocalDate.parse(sp.getDate().substring(0, 10));
            int day = list.getDayOfYear();

            int value = switch (port.toLowerCase()) {
                case "gdansk" -> sp.getGdansk();
                case "gdynia" -> sp.getGdynia();
                case "szczecin" -> sp.getSzczecin();
                case "swinoujscie" -> sp.getSwinoujscie();
                default -> 0;
            };
            series.getData().add(new XYChart.Data<>(day, value));
        }

        chart.getData().add(series);
        Scene scene = new Scene(chart, 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void ShowTypeChart(String port, String year) {

        String[] shipTypes = {"Pasażerski", "Towarowy", "Tankowiec", "Pozostały"};
        Request req = new Request();
        Map<String, ShipTypeInPort[]> all = new HashMap<>();

        for (String t : shipTypes) {
            all.put(t, req.PortTypeRequest(t));
        }

        Stage stage = new Stage();
        stage.setTitle("Types of ship in " + port + " in " + year);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Days");
        yAxis.setLabel("Quantity of ships");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Types of ship in " + port + " in " + year);

        for (String t : shipTypes) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(t);

            ShipTypeInPort[] list = all.get(t);

            for (ShipTypeInPort st : list) {
                if (!st.getDate().startsWith(year))
                    continue;

                LocalDate date = LocalDate.parse(st.getDate().substring(0, 10));
                int day = date.getDayOfYear();

                int value = switch (port.toLowerCase()) {
                    case "gdansk" -> st.getGdansk();
                    case "gdynia" -> st.getGdynia();
                    case "szczecin" -> st.getSzczecin();
                    case "swinoujscie" -> st.getSwinoujscie();
                    default -> 0;
                };

                series.getData().add(new XYChart.Data<>(day, value));
            }
            chart.getData().add(series);
        }
        stage.setScene(new Scene(chart, 900, 600));
        stage.show();
    }
}
