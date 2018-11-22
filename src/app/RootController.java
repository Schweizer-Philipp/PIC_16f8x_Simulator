package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import memoryBank.MemoryBankViewModel;
import util.FileReader;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * hso.ra.java.simulator.pic16f8x
 * app
 * Mike Bruder, Philipp Schweizer
 * 18.10.2018
 */
public class RootController implements Controlable, Initializable {
    @FXML
    private Label selectedFile;

    @FXML
    private SplitPane leftInnerSplitPane;

    @FXML
    private SplitPane rightInnerSplitPane;

    @FXML
    private SplitPane rightBottomInnerSplitPane;

    @FXML
    private AnchorPane rootAnchor;

    private Stage stage;

    private MemoryBankViewModel memoryBankViewModel = new MemoryBankViewModel();

    private RootModel model = new RootModel();

    @FXML
    public void handleOpenFileEvent() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Datei", "*.LST"));

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            model.setFile(String.valueOf(file));
            selectedFile.setText(String.valueOf(model.getFile()));
            FileReader.setCurrentFile(model.getFile());
            ControlsController.setCommandsForMicroController();
            leftInnerSplitPane.getItems().clear();

            loadMicroControllerView();
            loadMemoryBankView();

        } else {
            model.setFile("");
            selectedFile.setText("Not Selected");
        }
    }

    @FXML
    public void handleStartEvent() {
        ControlsController.getInstance().start();
    }

    @FXML
    public void handleStopEvent() {
        ControlsController.getInstance().stop();
    }

    @FXML
    public void handleStepEvent() {
        ControlsController.getInstance().step();
    }

    @FXML
    public void handleRestartEvent() {
        ControlsController.getInstance().restart();
    }

    private void loadMicroControllerView() {
        leftInnerSplitPane.getItems().add(ViewLoader.load("/microController/microController.fxml", stage));
    }

    private void loadMemoryBankView() {
        leftInnerSplitPane.getItems().add(ViewLoader.load("/memoryBank/memoryBank.fxml", stage, memoryBankViewModel, MemoryBankViewModel.class));
    }

    private void loadDetailStatusRegister() {


        rightBottomInnerSplitPane.getItems().add(ViewLoader.load("/memoryBank/detailStatusRegister.fxml", stage, memoryBankViewModel, MemoryBankViewModel.class));
    }

    private void loadLogfile() {

        rightInnerSplitPane.getItems().add(ViewLoader.load("/app/logFileCommands.fxml", stage));
    }

    private void loadWRegister() {

        rightBottomInnerSplitPane.getItems().add(ViewLoader.load("/memoryBank/wRegister.fxml", stage));
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        stage.showingProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rightInnerSplitPane.setDividerPositions(0.855);
                    rightBottomInnerSplitPane.setDividerPositions(0.8);
                    observable.removeListener(this);
                }
            }

        });

        leftInnerSplitPane.getItems().clear();
        rightInnerSplitPane.getItems().clear();
        rightBottomInnerSplitPane.getItems().clear();

        loadMicroControllerView();
        loadMemoryBankView();

        loadLogfile();
        rightInnerSplitPane.getItems().add(rightBottomInnerSplitPane);
        loadDetailStatusRegister();
        loadWRegister();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ControlsController.setMemoryBankViewModel(memoryBankViewModel);
        //leftInnerSplitPane.maxWidthProperty().bind(rootAnchor.widthProperty().multiply(.5d));
        //leftInnerSplitPane.minWidthProperty().bind(rootAnchor.widthProperty().multiply(.125d));
        //leftInnerSplitPane.setDividerPosition(0, .1); ändert nichts

        //rightInnerSplitPane.maxWidthProperty().bind(rootAnchor.widthProperty().multiply(.5d));
        //rightInnerSplitPane.minWidthProperty().bind(rootAnchor.widthProperty().multiply(.125d));

        //rightBottomInnerSplitPane.maxWidthProperty().bind(rootAnchor.widthProperty().multiply(.5d));
        //rightBottomInnerSplitPane.minWidthProperty().bind(rootAnchor.widthProperty().multiply(.125d));

        model.setFile(FileReader.getCurrentFile());
        selectedFile.setText(String.valueOf(model.getFile()));
    }
}
