package model;

public class MemoryBank {

    int[] register;


    public MemoryBank(){

        register = new int[79];
    }

    public int[] getRegister() {
        return register;
    }
}
