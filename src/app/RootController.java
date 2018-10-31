package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.FileReader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' app
 * ' Mike Bruder
 * ' 18.10.2018
 */
public class RootController implements Controlable, Initializable
{
	 @FXML private Label selectedFile;

	 @FXML private SplitPane innerSplitPane;

	 @FXML private AnchorPane rootAnchor;

	 private Stage stage;

	 private RootModel model = new RootModel();

	 @FXML public void handleOpenFileEvent()
	 {
		  final FileChooser fileChooser = new FileChooser();
		  File file = fileChooser.showOpenDialog(stage);

		  if (file != null) {
				model.setFile(String.valueOf(file));
				selectedFile.setText(String.valueOf(model.getFile()));
				FileReader.setCurrentFile(model.getFile());

				innerSplitPane.getItems().clear();

				loadMicroControllerView();
				loadMemoryBankView();

		  } else {
				model.setFile("");
				selectedFile.setText("Not Selected");
		  }
	 }

	 @FXML public void handleStartEvent()
	 {
			ControlsController.getInstance().start();
	 }

	 @FXML public void handleStopEvent()
	 {
		  ControlsController.getInstance().stop();
	 }

	 @FXML public void handleStepEvent()
	 {
		  ControlsController.getInstance().step();
	 }

	 @FXML public void handleRestartEvent()
	 {
		  ControlsController.getInstance().restart();
	 }

	 private void loadMicroControllerView()
	 {
		  innerSplitPane.getItems().add(ViewLoader.load("/microController/microController.fxml", stage));
	 }

	 private void loadMemoryBankView()
	 {
		  innerSplitPane.getItems().add(ViewLoader.load("/memoryBank/memoryBank.fxml", stage));
	 }

	 public void setStage(Stage stage)
	 {
		  this.stage = stage;

		  innerSplitPane.getItems().clear();

		  loadMicroControllerView();
		  loadMemoryBankView();
	 }

	 @Override public void initialize(URL location, ResourceBundle resources)
	 {
		  innerSplitPane.maxWidthProperty().bind(rootAnchor.widthProperty().multiply(.5d));
		  innerSplitPane.minWidthProperty().bind(rootAnchor.widthProperty().multiply(.125d));
		  innerSplitPane.setDividerPosition(0, .5);
		  model.setFile(FileReader.getCurrentFile());
		  selectedFile.setText(String.valueOf(model.getFile()));
		  createMemoryBank();
	 }

	 private void createMemoryBank()
	 {

	 }

}
