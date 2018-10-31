package util;

import memoryBank.IRegisterView;
import memoryBank.Register;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' util
 * ' Mike Bruder
 * ' 31.10.2018
 */
public class RegisterDataParser {

    private static final int BANK_ZERO_START = 0x00;
    private static final int BANK_ONE_START = 0x80;

    public static RowList<IRegisterView> getRegisterModel(int[] bankZero, int[] bankOne) {

        RowList<IRegisterView> rowList = new RowList<>();

        for (int i = 0; i < bankOne.length; i++) {

            rowList.add(new Register((BANK_ZERO_START + i), String.valueOf(bankZero[i])), new Register((BANK_ONE_START + i), String.valueOf(bankOne[i])));

        }

        return rowList;
    }
}
