package memoryBank;

import app.Controlable;
import app.ControlsController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * hso.ra.java.simulator.pic16f8x
 * memoryBank
 * Mike Bruder, Philipp Schweizer
 * 7.11.2018
 */

public class WRegisterController implements Controlable, Initializable {

    @FXML
    private TableView<String> wRegisterTable;

    private Stage stage;

    @Override
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        wRegisterTable.getColumns().clear();

        TableColumn<String, String> column = new TableColumn<>("W-Regsiter");
        column.setCellValueFactory(s -> new SimpleStringProperty(s.getValue()));
        column.prefWidthProperty().bind(wRegisterTable.widthProperty().multiply(1.d));
        wRegisterTable.getColumns().add(column);

        wRegisterTable.getItems().addAll("0x00");
        ControlsController.getInstance().setwRegisterController(this);
    }

    public void update(String update) {

        wRegisterTable.getItems().clear();
        wRegisterTable.getItems().add(update);
    }
}
