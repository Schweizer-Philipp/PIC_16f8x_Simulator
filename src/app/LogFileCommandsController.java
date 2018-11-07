package app;

import commandLine.CommandLineModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LogFileCommandsController implements Controlable, Initializable {

    private Stage stage;

    private static int logNumber = 0;

    @FXML
    private TextArea logArea;

    @Override
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ControlsController.getInstance().setLogFileCommandsController(this);
        logArea.textProperty().addListener((obs, oldVal, newVal) -> logArea.setScrollTop(Double.MAX_VALUE));
    }

    public void update(CommandLineModel commandLineModel) {
        logNumber++;
        logArea.appendText(String.valueOf(logNumber) + ". " + commandLineModel.toStringSmall());
        logArea.appendText("\n\n");
    }

    public void reset() {

        logNumber = 0;
        logArea.clear();
    }

}
