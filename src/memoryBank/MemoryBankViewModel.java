package memoryBank;

import util.RowList;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' memoryBank
 * ' Mike Bruder
 * ' 27.10.2018
 */
public class MemoryBankViewModel {
    private static final int REGISTER_AMOUNT = 0x50;

    private static final int REGISTER_PADDING = 0x80;

    private RowList<IRegisterView> register;

    public MemoryBankViewModel() {
        this.register = new RowList<>();
        initializeRegisters();
    }

    private void initializeRegisters() {
        System.out.println(REGISTER_AMOUNT);

        final String[][] registerNames = {{"Indirect addr.", "Indirect addr."}, {"TMR0", "OPTION"},
                {"PCL", "PCL"}, {"STATUS", "STATUS"}, {"FSR", "FSR"}, {"PORTA", "TRISA"}, {"PORTB", "TRISB"},
                {"", ""}, {"EEDATA", "EECON1"}, {"EEADR", "EECON2"}, {"PCLATH", "PCLATH"},
                {"INTCON", "INTCON"}};

        // RegisterNamen
        for (int i = 0; i < registerNames.length; i++) {

            Register bankZero = new Register(i, registerNames[i][0]);

            Register bankOne = new Register(i + REGISTER_PADDING, registerNames[i][1]);

            register.add(bankZero, bankOne);
        }

        // "die ohne titel"
        for (int i = registerNames.length; i < REGISTER_AMOUNT; i++) {

            Register bankZero = new Register(i, "SRAM");

            Register bankOne = new Register(i + REGISTER_PADDING, "MAPPING");

            register.add(bankZero, bankOne);

        }
    }

    public void setRegister(RowList<IRegisterView> register) {
        this.register = register;
    }

    public RowList<IRegisterView> getRegister() {
        return register;
    }
}
