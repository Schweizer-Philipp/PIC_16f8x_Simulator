package memoryBank;

public class MemoryBankDataModel {

    private int[] register = new int[80];

    private static MemoryBankDataModel bankZero;
    private static MemoryBankDataModel bankOne;

    private MemoryBankDataModel() {
        initialize();
    }

    public static MemoryBankDataModel getInstanceBankZero() {

        if (bankZero == null) {

            bankZero = new MemoryBankDataModel();
        }

        return bankZero;
    }

    public static MemoryBankDataModel getInstanceBankOne() {

        if (bankOne == null) {

            bankOne = new MemoryBankDataModel();
        }

        return bankOne;
    }

    public int[] getRegister() {
        return register;
    }

    public void initialize() {
        //TODO Mike setzt alle werte beim anschalten
        register = new int[80];
    }
}
