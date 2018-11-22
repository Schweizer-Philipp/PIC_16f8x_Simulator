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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * hso.ra.java.simulator.pic16f8x
 * memoryBank
 * Mike Bruder, Philipp Schweizer
 * 7.11.2018
 */

public class WRegisterController implements Controlable, Initializable {

    @FXML
    private TableView<List<String>> wRegisterTable;

    private Stage stage;

    private List<String> list;

    @Override
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list = new ArrayList<>();

        wRegisterTable.getColumns().clear();

        TableColumn<List<String>, String> column_WRegister = new TableColumn<>("W-Regsiter");
        TableColumn<List<String>, String> column_Cycles = new TableColumn<>("Cycles");
        column_WRegister.setCellValueFactory(l -> new SimpleStringProperty(l.getValue().get(0)));
        column_WRegister.prefWidthProperty().bind(wRegisterTable.widthProperty().multiply(0.5d));
        column_Cycles.setCellValueFactory(l -> new SimpleStringProperty(l.getValue().get(1)));
        column_Cycles.prefWidthProperty().bind(wRegisterTable.widthProperty().multiply(0.5d));
        wRegisterTable.getColumns().add(column_WRegister);
        wRegisterTable.getColumns().add(column_Cycles);

        list.add("0x00");
        list.add("0");

        wRegisterTable.getItems().addAll(list);
        ControlsController.getInstance().setwRegisterController(this);
    }

    public void update(String wRegister, String cycles) {

        list.clear();
        list.add(wRegister);
        list.add(cycles);
        wRegisterTable.getItems().clear();
        wRegisterTable.getItems().addAll(list);
    }
}
