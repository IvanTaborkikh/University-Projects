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

    public void showChart(String port, String year) {

        Request req = new Request();
        ShipsInPorts[] data = req.requestPorts();

        ObservableList<ShipsInPorts> portList = FXCollections.observableArrayList(data);
        ObservableList<ShipsInPorts> filtered = portList.filtered(sp -> sp.getDate().startsWith(year));

        Stage stage = new Stage();
        stage.setTitle("Ships in " + port + " in " + year);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Days");
        yAxis.setLabel("Quantity of ships");


        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Ships in " + port + " in " + year);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(port);

        for (ShipsInPorts sp : filtered) {
            LocalDate dateList = LocalDate.parse(sp.getDate().substring(0, 10));
            int day = dateList.getDayOfYear();

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

    public void showTypeChart(String port, String year) {

        String[] shipTypes = {"Passenger", "Cargo", "Tanker", "Other"};
        Request req = new Request();
        Map<String, ShipTypeInPort[]> allData = new HashMap<>();

        for (String type : shipTypes) {
            String polishtype = switch (type) {
                case "Passenger" -> "Pasażerski";
                case "Cargo"     -> "Towarowy";
                case "Tanker"    -> "Tankowiec";
                case "Other"     -> "Pozostały";
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };

            allData.put(type, req.requestPortTypes(polishtype));
        }

        Stage stage = new Stage();
        stage.setTitle("Ship types in " + port + " in " + year);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Days");
        yAxis.setLabel("Quantity of ships");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Ship types in " + port + " in " + year);

        for (String type : shipTypes) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(type);

            ShipTypeInPort[] list = allData.get(type);

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

        javafx.scene.Node legend = chart.lookup(".chart-legend");

        if (legend != null) {
            legend.setStyle("-fx-background-color: transparent; " +
                    "-fx-padding: 10px; " +
                    "-fx-hgap: 20px; " +
                    "-fx-alignment: center;");
        }
    }
}
