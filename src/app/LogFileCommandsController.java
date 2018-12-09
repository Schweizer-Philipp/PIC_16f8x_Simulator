package app;

import commandLine.CommandLineModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * hso.ra.java.simulator.pic16f8x
 * app
 * Mike Bruder, Philipp Schweizer
 * 7.11.2018
 */

public class LogFileCommandsController implements Controlable, Initializable {

    private Stage stage;
    @FXML
    private TableView<String> tableView;
    @FXML
    private TableColumn<String, String> columnText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ControlsController.getInstance().setLogFileCommandsController(this);
        columnText = new TableColumn<>();
        columnText.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()));
        tableView.getColumns().add(columnText);
        tableView.setRowFactory(createRowFactory());

    }

    public void jumpToRow(int row) {

        if(row == -1){

            tableView.scrollTo(tableView.getItems().size()-1);
            tableView.getSelectionModel().select(tableView.getItems().size()-1);
        }
        else{

            tableView.scrollTo(row);
            tableView.getSelectionModel().select(row);
        }

    }

    @Override
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    public void setList(List<String> newList){

        tableView.getItems().clear();
        tableView.getItems().addAll(newList);
        jumpToRow(0);
    }

    private Callback<TableView<String>, TableRow<String>> createRowFactory()
    {
        return view -> createRowWithClickListener();
    }

    private TableRow<String> createRowWithClickListener() {
        TableRow<String> row = new TableRow<>();
        row.setPrefHeight(50);
        return row;
    }
}
