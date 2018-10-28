package memoryBank;

import app.Controlable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import util.RowElement;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' memoryBank
 * ' Mike Bruder
 * ' 20.10.2018
 */
public class MemoryBankController implements Controlable, Initializable
{
	 private Stage stage;

	 @FXML private TableView<RowElement<IRegisterView>> table;

	 @FXML private TableColumn<RowElement<IRegisterView>, String> leftFileAddress;

	 @FXML private TableColumn<RowElement<IRegisterView>, String> rightFileAddress;

	 @FXML private TableColumn<RowElement<IRegisterView>, String> bankOne;

	 @FXML private TableColumn<RowElement<IRegisterView>, String> bankZero;

	 private MemoryBankViewModel model;

	 @Override public void setStage(Stage stage)
	 {
		  this.stage = stage;
	 }

	 @Override public void initialize(URL location, ResourceBundle resources)
	 {
		  this.model = new MemoryBankViewModel();

		  leftFileAddress.prefWidthProperty().bind(table.widthProperty().multiply(.2d));
		  bankZero.prefWidthProperty().bind(table.widthProperty().multiply(.3d));
		  bankOne.prefWidthProperty().bind(table.widthProperty().multiply(.3d));
		  rightFileAddress.prefWidthProperty().bind(table.widthProperty().multiply(.2d));

		  initializeTableData();
	 }

	 private void initializeTableData()
	 {
		  final ObservableList<RowElement<IRegisterView>> data = FXCollections
				  .observableArrayList(model.getRegister().toList());

		  leftFileAddress.setCellValueFactory(
				  rowData -> new SimpleStringProperty(formatAddress(rowData.getValue().getLeftElement().getAddress())));
		  bankZero.setCellValueFactory(rowData -> rowData.getValue().getLeftElement().nameProperty());
		  bankOne.setCellValueFactory(rowData -> rowData.getValue().getRightElement().nameProperty());
		  rightFileAddress.setCellValueFactory(
				  rowData -> new SimpleStringProperty(formatAddress(rowData.getValue().getRightElement().getAddress())));

		  table.setItems(data);

//		  data.get(0).getLeftElement().setName("0x" + Integer.toHexString(42).toUpperCase());
	 }

	 private String formatAddress(int address)
	 {
		  String addressStr = String.format("%2s", Integer.toHexString(address)).replaceAll(" ", "0");
		  return String.format("%sh", addressStr.toUpperCase());
	 }

}
