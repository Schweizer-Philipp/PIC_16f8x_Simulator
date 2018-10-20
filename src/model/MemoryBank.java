package model;

public class MemoryBank {

    int[] register = new int[80];

    private static MemoryBank bankZero;
    private static MemoryBank bankOne;

    public static MemoryBank getInstanceBankZero(){

        if(bankZero ==null){

            bankZero = new MemoryBank();
        }

        return bankZero;
    }

    public static MemoryBank getInstanceBankOne(){

        if(bankOne ==null){

            bankOne = new MemoryBank();
        }

        return bankOne;
    }

    public int[] getRegister() {
        return register;
    }
}
