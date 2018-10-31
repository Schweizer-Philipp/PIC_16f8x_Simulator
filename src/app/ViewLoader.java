package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' app
 * ' Mike Bruder
 * ' 20.10.2018
 */
public final class ViewLoader {
    private ViewLoader() {
    }

    public static Pane load(String fxml, Stage stage) {

        Pane pane = new Pane();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewLoader.class.getResource(fxml));
            pane = loader.load();

            Controlable controller = (Controlable) loader.getController();
            controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pane;
    }
}
