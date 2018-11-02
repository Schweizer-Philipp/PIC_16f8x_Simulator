package app;

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
 * ' hso.ra.java.simulator.pic16f8x
 * ' app
 * ' Mike Bruder
 * ' 18.10.2018
 */
public class RootController implements Controlable, Initializable {
    @FXML
    private Label selectedFile;

    @FXML
    private SplitPane innerSplitPane;

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
            innerSplitPane.getItems().clear();

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
        innerSplitPane.getItems().add(ViewLoader.load("/microController/microController.fxml", stage));
    }

    private void loadMemoryBankView() {
        innerSplitPane.getItems().add(ViewLoader.load("/memoryBank/memoryBank.fxml", stage, memoryBankViewModel, MemoryBankViewModel.class));
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        innerSplitPane.getItems().clear();

        loadMicroControllerView();
        loadMemoryBankView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ControlsController.setMemoryBankViewModel(memoryBankViewModel);
        innerSplitPane.maxWidthProperty().bind(rootAnchor.widthProperty().multiply(.5d));
        innerSplitPane.minWidthProperty().bind(rootAnchor.widthProperty().multiply(.125d));
        innerSplitPane.setDividerPosition(0, .5);
        model.setFile(FileReader.getCurrentFile());
        selectedFile.setText(String.valueOf(model.getFile()));
        createMemoryBank();
    }

    private void createMemoryBank() {

    }

}
