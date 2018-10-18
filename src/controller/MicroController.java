package controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import model.MemoryBank;
import model.CommandLineModel;

import java.util.ArrayList;
import java.util.BitSet;

public class MicroController {


    int registerW;

    ArrayList<CommandLineModel> commands;

    MemoryBank bankZero;

    MemoryBank bankOne;

    int PC = 0; // Programm counter


    public MicroController(){

        commands = new ArrayList<>();

        bankZero = new MemoryBank();

        bankOne = new MemoryBank();
    }

    public void executeCommand(CommandLineModel command){


        switch (command.getCommandCode()){

            case ADDWF :

                //TODO
                break;

            case ANDWF:

                //TODO
                break;

            case CLRF:

                //TODO
                break;

            case CLRW:

                //TODO
                break;

            case COMF:

                //TODO
                break;

            case DECF:

                //TODO
                break;

            case DECFSZ:

                //TODO
                break;

            case INCF:

                //TODO
                break;

            case INCFSZ:

                //TODO
                break;

            case IORWF:

                //TODO
                break;

            case MOVF:

                //TODO
                break;

            case MOVWF:

                //TODO
                break;

            case NOP:

                //TODO
                break;

            case RLF:

                //TODO
                break;

            case RRF:

                //TODO
                break;

            case SUBWF:

                //TODO
                break;

            case SWAPF:

                //TODO
                break;

            case XORWF:

                //TODO
                break;

            case BCF:

                //TODO
                break;

            case BSF:

                //TODO
                break;

            case BTFSC:

                //TODO
                break;

            case BTFSS:

                //TODO
                break;

            case ADDLW:

                if(checkOverflowBetweenBitFourAndFive(registerW,command.getCommandArg())){

                    setBit(3,1,bankOne);
                    setBit(3,1,bankZero);
                }
                else{

                    unsetBit(3,1,bankOne);
                    unsetBit(3,1,bankZero);
                }
                registerW = registerW + command.getCommandArg();
                if(registerW>255){

                    setBit(3,0,bankZero);
                    setBit(3,0,bankOne);
                }
                else{

                    unsetBit(3,0,bankOne);
                    unsetBit(3,0,bankZero);
                }
                registerW = registerW & 0x000000FF;
                PC++;
                break;

            case ANDLW:

                registerW = registerW & command.getCommandArg();
                checkZeroFlag(registerW);
                PC++;
                break;

            case CALL:

                //TODO
                break;

            case CLRWDT:

                //TODO
                break;

            case GOTO:

                PC = command.getCommandArg();
                break;

            case IORLW:

                registerW = registerW | command.getCommandArg();
                checkZeroFlag(registerW);
                PC++;
                break;

            case MOVLW:

                registerW = command.getCommandArg();
                checkZeroFlag(registerW);
                PC++;
                break;

            case RETFIE:

                //TODO
                break;

            case RETLW:

                //TODO
                break;

            case RETURN:

                //TODO
                break;

            case SLEEP:

                //TODO
                break;

            case SUBLW:

                // literal - w

                int twoCompliment = (~registerW)+1;

                System.out.println(twoCompliment);

                twoCompliment = twoCompliment & 0x000000FF;

                System.out.println(twoCompliment);

                if(!checkOverflowBetweenBitFourAndFive(command.getCommandArg(),twoCompliment)){

                    setBit(3,1,bankOne);
                    setBit(3,1,bankZero);
                }
                else{

                    unsetBit(3,1,bankOne);
                    unsetBit(3,1,bankZero);
                }
                registerW = command.getCommandArg() + twoCompliment;
                if(!(registerW>255)){

                    setBit(3,0,bankZero);
                    setBit(3,0,bankOne);
                }
                else{

                    setBit(3,0,bankZero);
                    setBit(3,0,bankOne);
                }
                registerW = registerW & 0x000000FF;
                PC++;
                break;

            case XORLW:

                registerW = registerW ^ command.getCommandArg();
                checkZeroFlag(registerW);
                PC++;
                break;

            case DEFAULT:

                //TODO
                break;

        }


    }

    private boolean checkOverflowBetweenBitFourAndFive(int number1, int number2) {

        number1 = number1 & 0x0000000F;
        number2 = number2 & 0x0000000F;

        return (number1+number2) >15;
    }

    private void checkZeroFlag(int number){

        if(number==0){

            setBit(3,2,bankOne);
            setBit(3,2,bankZero);
        }
    }

    private void setBit(int register,int indexBit,MemoryBank bank) {


        bank.getRegister()[register] = bank.getRegister()[register] & 0x000000FF;
        bank.getRegister()[register] = bank.getRegister()[register] | (0x00000001<<indexBit);

    }

    private void unsetBit(int register,int indexBit,MemoryBank bank) {


        bank.getRegister()[register] = bank.getRegister()[register] & 0x000000FF;
        bank.getRegister()[register] = bank.getRegister()[register] & (0xFFFFFFFE<<indexBit);

    }

    public int getRegisterW() {
        return registerW;
    }

    public ArrayList<CommandLineModel> getCommands() {
        return commands;
    }

    public MemoryBank getBankZero() {
        return bankZero;
    }

    public MemoryBank getBankOne() {
        return bankOne;
    }

    public int getPC() {
        return PC;
    }

    @Override
    public String toString() {
        return "MicroController{" + "registerW=" + registerW + ", PC=" + PC + ", flags= "+bankOne.getRegister()[3]+ '}';
    }
}
