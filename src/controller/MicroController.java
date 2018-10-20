package controller;

import model.MemoryBank;
import model.CommandLineModel;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import java.util.function.Predicate;

public class MicroController {


    private int registerW;

    private static int FLAG_REGISTER_W = -1;

    private ArrayList<CommandLineModel> commands;

    private MemoryBank bankZero;

    private MemoryBank bankOne;

    private Deque<Integer> tos;

    int programCounter = 0; // Programm Counter


    public MicroController() {

        commands = new ArrayList<>();

        bankZero = MemoryBank.getInstanceBankZero();

        bankOne = MemoryBank.getInstanceBankOne();

        tos = new LinkedList<>();
    }

    public void executeCommand(CommandLineModel command) {


        switch (command.getCommandCode()) {

            case ADDWF:

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

                programCounter++;
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

                add(registerW, command.getCommandArg(), FLAG_REGISTER_W);
                programCounter++;
                break;

            case ANDLW:

                registerW = registerW & command.getCommandArg();
                checkZeroFlag(registerW);
                programCounter++;
                break;

            case CALL:

                tos.push(programCounter+1);
                programCounter = command.getCommandArg();
                break;

            case CLRWDT:

                //TODO
                break;

            case GOTO:

                programCounter = command.getCommandArg();
                break;

            case IORLW:

                registerW = registerW | command.getCommandArg();
                checkZeroFlag(registerW);
                programCounter++;
                break;

            case MOVLW:

                registerW = command.getCommandArg();
                checkZeroFlag(registerW);
                programCounter++;
                break;

            case RETFIE:

                //TODO
                break;

            case RETLW:

                registerW = command.getCommandArg();
                programCounter = tos.pop();
                break;

            case RETURN:

                programCounter = tos.pop();
                break;

            case SLEEP:

                //TODO
                break;

            case SUBLW:

                // literal - w

                int twoCompliment = (~registerW) + 1;

                twoCompliment = twoCompliment & 0x000000FF;

                add(twoCompliment, command.getCommandArg(), FLAG_REGISTER_W);

                programCounter++;
                break;

            case XORLW:

                registerW = registerW ^ command.getCommandArg();
                checkZeroFlag(registerW);
                programCounter++;
                break;

            case DEFAULT:

                //TODO
                break;

        }


    }

    private void add(int number1, int number2, int register) {

        checkOverflowBetweenBitFourAndFive(number1, number2);

        int destination = number1 + number2;

        checkOverflowAtNineBit(destination);

        destination = destination & 0x000000FF;

        checkZeroFlag(destination);

        if (register == FLAG_REGISTER_W) {

            registerW = destination;
        } else {

            //TODO
        }

    }

    private void checkOverflowBetweenBitFourAndFive(int number1, int number2) {

        number1 = number1 & 0x0000000F;
        number2 = number2 & 0x0000000F;

        setOrUnsetBitInRegister(3, 1, number1 + number2, e -> e > 15);
    }

    private void checkOverflowAtNineBit(int number) {

        setOrUnsetBitInRegister(3, 0, number, e -> e > 255);
    }

    private void checkZeroFlag(int number) {

        setOrUnsetBitInRegister(3, 2, number, e -> e == 0);
    }

    private void setOrUnsetBitInRegister(int register, int indexBit, int number, Predicate<Integer> condition) {

        if (condition.test(number)) {

            setBit(register, indexBit, bankOne);
            setBit(register, indexBit, bankZero);
        } else {

            unsetBit(register, indexBit, bankOne);
            unsetBit(register, indexBit, bankZero);
        }
    }

    private void setBit(int register, int indexBit, MemoryBank bank) {

        bank.getRegister()[register] = bank.getRegister()[register] & 0x000000FF;
        bank.getRegister()[register] = bank.getRegister()[register] | (0x00000001 << indexBit);
    }

    private void unsetBit(int register, int indexBit, MemoryBank bank) {

        bank.getRegister()[register] = bank.getRegister()[register] & 0x000000FF;

        int mask = (int) (Math.pow(2, indexBit) - 1);

        bank.getRegister()[register] = bank.getRegister()[register] & ((0xFFFFFFFE << indexBit) | mask);
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

    public int getProgramCounter() {
        return programCounter;
    }

    @Override
    public String toString() {
        return "MicroController{" + "registerW=" + registerW + ", programCounter=" + programCounter + ", flagC= " + (bankOne.getRegister()[3] & 0x1) + ", flagDC= " + ((bankOne.getRegister()[3] & 0x2) >> 1) + ", flag0= " + ((bankOne.getRegister()[3] & 0x4) >> 2) + '}';
    }
}
