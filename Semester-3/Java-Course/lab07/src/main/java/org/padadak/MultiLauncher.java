package org.padadak;

import javafx.application.Application;
import javafx.stage.Stage;
import org.padadak.apps.ControlCenter;
import org.padadak.apps.Enviroment;
import org.padadak.apps.RetentionBasin;
import org.padadak.apps.RiverSection;

public class MultiLauncher extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        new ControlCenter("ControlCenter").start(new Stage());
        new Enviroment("Enviroment").start(new Stage());

        new RiverSection(1000, "Dnipro", "Vanya").start(new Stage());
        new RetentionBasin("Vanya", "Siena").start(new Stage());
        new RiverSection(3000, "Siena", "Lalala").start(new Stage());
        new RetentionBasin("Lalala", "River_3003").start(new Stage());
    }
}
