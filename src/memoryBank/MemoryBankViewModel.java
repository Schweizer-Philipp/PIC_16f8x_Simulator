package memoryBank;

import app.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.RegisterDataParser;
import util.RowElement;
import util.RowList;

/**
 * hso.ra.java.simulator.pic16f8x
 * memoryBank
 * Mike Bruder, Philipp Schweizer
 * 27.10.2018
 */
public class MemoryBankViewModel implements Model
{
	 private static final int REGISTER_AMOUNT = 0x50;

	 private static final int REGISTER_PADDING = 0x80;

	 private ObservableList<RowElement<IRegisterView>> registerData;

	 private DetailStatusRegisterController detailStatusRegisterController;

	 public MemoryBankViewModel()
	 {
		  initializeRegisters();
	 }

	 private void initializeRegisters()
	 {

        /*RowList<IRegisterView> register = new RowList<>();
        final String[][] registerNames = {{"Indirect addr.", "Indirect addr."}, {"TMR0", "OPTION"},
                {"PCL", "PCL"}, {"STATUS", "STATUS"}, {"FSR", "FSR"}, {"PORTA", "TRISA"}, {"PORTB", "TRISB"},
                {"", ""}, {"EEDATA", "EECON1"}, {"EEADR", "EECON2"}, {"PCLATH", "PCLATH"},
                {"INTCON", "INTCON"}};

        // RegisterNamen
        for (int i = 0; i < registerNames.length; i++) {

            Register bankZero = new Register(i, registerNames[i][0], false);

            Register bankOne = new Register(i + REGISTER_PADDING, registerNames[i][1], false);

            register.add(bankZero, bankOne);
        }

        // "die ohne titel"
        for (int i = registerNames.length; i < REGISTER_AMOUNT; i++) {

            Register bankZero = new Register(i, "SRAM", false);

            Register bankOne = new Register(i + REGISTER_PADDING, "MAPPING", false);

            register.add(bankZero, bankOne);

        }*/
		  registerData = FXCollections.observableArrayList(RegisterDataParser
				  .getRegisterModel(MemoryBankDataModel.getInstanceBankZero().getRegister(),
						  MemoryBankDataModel.getInstanceBankOne().getRegister()).toList());
	 }

	 public void setDetailStatusRegisterController(DetailStatusRegisterController detailStatusRegisterController)
	 {
		  this.detailStatusRegisterController = detailStatusRegisterController;
	 }

	 public void setRegisterData(ObservableList<RowElement<IRegisterView>> registerData)
	 {
		  this.registerData = registerData;
	 }

	 public ObservableList<RowElement<IRegisterView>> getRegisterData()
	 {

		  return registerData;
	 }

	 public void changeListData(RowList<IRegisterView> register)
	 {

		  registerData.clear();
		  registerData.addAll(register.toList());

		  if (detailStatusRegisterController != null) {
				detailStatusRegisterController.update();
		  }
	 }

	 public StringProperty getStatusBit(int bitOffset)
	 {

		  int statusRegister = Integer.decode(registerData.get(3).getLeftElement().nameProperty().get());

		  return new SimpleStringProperty(String.valueOf((statusRegister >> bitOffset) & 0x1));
	 }
}
