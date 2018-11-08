package memoryBank;

/**
 * hso.ra.java.simulator.pic16f8x
 * memoryBank
 * Mike Bruder, Philipp Schweizer
 * 7.11.2018
 */

public class MemoryBankDataModel {

    private int[] register = new int[80];

    private static MemoryBankDataModel bankZero;
    private static MemoryBankDataModel bankOne;

    private MemoryBankDataModel(boolean isBankZero) {
        initialize(isBankZero);
    }

    public static MemoryBankDataModel getInstanceBankZero() {

        if (bankZero == null) {

            bankZero = new MemoryBankDataModel(true);
        }

        return bankZero;
    }

    public static MemoryBankDataModel getInstanceBankOne() {

        if (bankOne == null) {

            bankOne = new MemoryBankDataModel(false);
        }

        return bankOne;
    }

    public int[] getRegister() {
        return register;
    }

    public void initialize(boolean isBankZero) {
    	
    	int[] bankZeroRegister = new int[80];
    	int[] bankOneRegister = new int[80];
    	
    	bankZeroRegister[3] = 24;
    	bankOneRegister[1] = 255;
    	bankOneRegister[3] = 24;
    	bankOneRegister[5] = 31;
    	bankOneRegister[6] = 255;
    	
        if(isBankZero){
            setRegister(bankZeroRegister);
        }
        else{
            setRegister(bankOneRegister);
        }
    }
    
    private void setRegister(int[] newRegister) {
    	
    	register = newRegister;
    }
}
