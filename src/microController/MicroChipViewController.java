package microController;

import app.Controlable;
import app.ControlsController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import memoryBank.MemoryBankDataModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * hso.ra.java.simulator.pic16f8x
 * microController
 * Mike Bruder, Philipp Schweizer
 * 20.10.2018
 */
public class MicroChipViewController implements Controlable, Initializable {

    private static final double PIN_PANEL_SCALE = .25d;

    private static final double CONTROLLER_SCALE = 1 - (PIN_PANEL_SCALE * 2);

    private static final int PIN_HEIGHT = 25;

    private static final String PIN_LABEL_CSS = "-fx-background-color: %s; -fx-text-fill: %s; -fx-border-color: black;";
    private static final int TRIS_A = 5;
    private static final int TRIS_B = 6;
    private static final int PORT_A = 5;
    private static final int PORT_B = 6;

    private Stage stage;

    @FXML
    private VBox left;

    @FXML
    private Pane middle;

    @FXML
    private VBox right;

    @FXML
    private HBox parent;

    private MicroChipController microChipController;

    private MicroControllerModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model = new MicroControllerModel();

        bindPrefWidthAndHeightProperty(left, PIN_PANEL_SCALE);
        bindPrefWidthAndHeightProperty(right, PIN_PANEL_SCALE);
        bindPrefWidthAndHeightProperty(middle, CONTROLLER_SCALE);

        model.getPins().stream().forEach(row -> {
            addLeftPin(row.getLeftElement());
            addRightPin(row.getRightElement());
        });

        DoubleBinding spacingBinding = parent.heightProperty().subtract(model.getPins().size() * PIN_HEIGHT)
                .divide(model.getPins().size());
        left.spacingProperty().bind(spacingBinding);
        right.spacingProperty().bind(spacingBinding);

        microChipController = ControlsController.getInstance().getMicroController();

        model.getPins().stream().forEach(row -> {
            initializePins(row.getLeftElement());
            initializePins(row.getRightElement());
        });
    }

    private void initializePins(PinModel pin) {
        //TODO pins auf klicks sensitiv machen # alle current �nderungen merken.
        MemoryBankDataModel bankZero = MemoryBankDataModel.getInstanceBankZero();
        MemoryBankDataModel bankOne = MemoryBankDataModel.getInstanceBankOne();

        int port_a = bankZero.getRegister()[PORT_A];
        int port_b = bankZero.getRegister()[PORT_B];
        int tris_a = bankOne.getRegister()[TRIS_A];
        int tris_b = bankOne.getRegister()[TRIS_B];

        port_a = port_a & 0xFF;
        port_b = port_b & 0xFF;
        tris_a = tris_a & 0xFF;
        tris_b = tris_b & 0xFF;

        for (int i = 0; i < 5; i++) {

            int statusTris = (tris_a >> i) & 0x1; // isClickable
            int statusPort = (port_a >> i) & 0x1; // high - low

            pin.setStatusProperty(new SimpleBooleanProperty(statusTris == 1));

        }


    }

    public void dataHasChanged() {
        // TODO change pins
    }

    public void changeDataToTheCurrentStatus() {


    }

    private void bindPrefWidthAndHeightProperty(Pane pane, double scale) {
        pane.prefWidthProperty().bind(parent.widthProperty().multiply(scale));
        pane.prefHeightProperty().bind(parent.heightProperty());
    }

    private void addRightPin(PinModel rightElement) {
        HBox row = createRowBox(Pos.CENTER_LEFT, rightElement);
        right.getChildren().add(row);
    }

    private void addLeftPin(PinModel leftElement) {

        HBox row = createRowBox(Pos.CENTER_RIGHT, leftElement);
        left.getChildren().add(row);
    }

    private HBox createRowBox(Pos alignment, PinModel pin) {
        Label labelName = createNameLabel(pin);
        Label labelPin = createPinLabel(pin, labelName);

        HBox row = new HBox();
        row.setPrefHeight(PIN_HEIGHT);
        row.setMaxHeight(PIN_HEIGHT);
        row.setMinHeight(PIN_HEIGHT);
        row.setAlignment(alignment);

        if (alignment == Pos.CENTER_RIGHT) {
            row.getChildren().addAll(labelName, labelPin);
        } else {
            row.getChildren().addAll(labelPin, labelName);
        }

        return row;
    }

    private Label createPinLabel(PinModel pin, Label labelName) {
        Label labelPin = new Label();

        labelPin.textProperty().bind(pin.pinNumberProperty().asString());

        labelPin.styleProperty().bind(getPinLabelStyleBinding(pin));

        labelPin.setAlignment(Pos.CENTER);
        labelPin.setMinWidth(PIN_HEIGHT);

        labelPin.setOnMouseClicked(e -> pin.toggle());

        HBox.setHgrow(labelName, Priority.NEVER);

        return labelPin;
    }

    private Label createNameLabel(PinModel pin) {
        Label labelName = new Label();

        labelName.textProperty().bind(pin.nameProperty());
        labelName.setAlignment(Pos.CENTER);

        HBox.setHgrow(labelName, Priority.ALWAYS);

        return labelName;
    }

    private StringBinding getPinLabelStyleBinding(PinModel pin) {
        return Bindings.when(pin.statusProperty().isEqualTo(new SimpleBooleanProperty(true)))
                .then(String.format(PIN_LABEL_CSS, "black", "white"))
                .otherwise(String.format(PIN_LABEL_CSS, "white", "black"));
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
