package microController;

import commandLine.CommandCode;
import commandLine.CommandLineModel;
import memoryBank.MemoryBankDataModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Predicate;

public class MicroChipController {

    private int registerW;

    private static int FLAG_REGISTER_W = -1;

    private static int STATUS_REGISTER = 0x03;

    private ArrayList<CommandLineModel> commands;

    private MemoryBankDataModel bankZero;

    private MemoryBankDataModel bankOne;

    private Deque<Integer> tos;

    private CommandCode code;

    private int programCounter = 0; // Programm Counter

    private int[] equalRegister = {0x02, 0x03, 0x04, 0x0A, 0x0B};

    private int[] fiveBitRegister = {0x05, 0x0A, 0x85, 0x88, 0x8A};

    public MicroChipController() {

        commands = new ArrayList<>();

        bankZero = MemoryBankDataModel.getInstanceBankZero();

        bankOne = MemoryBankDataModel.getInstanceBankOne();

        tos = new LinkedList<>();

        initialize();
    }

    private void initialize() {
        //TODO Mike bits setzen vom controller beim start oder restart
    }

    public void restart() {

        programCounter = 0;
        //setPCL();
        bankOne.restart();
        bankZero.restart();
        initialize();
    }

    public void executeCommand(CommandLineModel command) {

        int result;
        int twoCompliment;
        code = command.getCommandCode();

        switch (command.getCommandCode()) {

            case ADDWF:

                result = add(getRegisterValue(command.getCommandArg() & 0x7F), registerW);

                safeValueInRegister(command, result, true);
                programmcounterInc();
                break;

            case ANDWF:

                result = registerW & getRegisterValue((command.getCommandArg() & 0x7F));
                checkZeroFlag(result);

                safeValueInRegister(command, result, true);
                programmcounterInc();
                break;

            case CLRF:

                setRegisterValue(0x00, command.getCommandArg(), getCurrentBank(), true);
                checkZeroFlag(0);
                programmcounterInc();
                break;

            case CLRW:

                setRegisterValue(0x00, FLAG_REGISTER_W, null, true);
                checkZeroFlag(0);
                programmcounterInc();
                break;

            case COMF:

                result = (~getRegisterValue((command.getCommandArg() & 0x7F)) & 0xFF);
                checkZeroFlag(result);
                safeValueInRegister(command, result, true);
                programmcounterInc();
                break;

            case DECF:

                result = (getRegisterValue((command.getCommandArg() & 0x7F)) - 1) & 0xFF;
                checkZeroFlag(result);

                safeValueInRegister(command, result, true);

                programmcounterInc();
                break;

            case DECFSZ:

                result = (getRegisterValue((command.getCommandArg() & 0x7F)) - 1) & 0xFF;

                safeValueInRegister(command, result, false);

                if (result == 0) {

                    commands.add(programCounter + 1, new CommandLineModel(0, CommandCode.NOP, -1, 0, null));
                }

                programmcounterInc();

                break;

            case INCF:

                result = (getRegisterValue((command.getCommandArg() & 0x7F)) + 1) & 0xFF;
                checkZeroFlag(result);

                safeValueInRegister(command, result, true);

                programmcounterInc();
                break;

            case INCFSZ:

                result = (getRegisterValue((command.getCommandArg() & 0x7F)) + 1) & 0xFF;

                safeValueInRegister(command, result, false);

                if (result == 0) {

                    commands.add(programCounter + 1, new CommandLineModel(0, CommandCode.NOP, -1, 0, null));
                }

                programmcounterInc();
                break;

            case IORWF:

                result = registerW | getRegisterValue((command.getCommandArg() & 0x7F));
                checkZeroFlag(~result);

                safeValueInRegister(command, result, true);
                programmcounterInc();
                break;

            case MOVF:

                result = getRegisterValue((command.getCommandArg() & 0x7F));
                checkZeroFlag(result);

                safeValueInRegister(command, result, true);
                programmcounterInc();
                break;

            case MOVWF:

                if (command.getCommandArg() < 0x50) {

                    if (Arrays.stream(equalRegister).filter(r -> r == command.getCommandArg()).count() > 0) {
                        setRegisterValue(registerW, command.getCommandArg(), bankZero, false);
                        setRegisterValue(registerW, command.getCommandArg(), bankOne, false);
                    } else {
                        setRegisterValue(registerW, command.getCommandArg(), getCurrentBank(), false);
                    }
                }
                programmcounterInc();
                break;

            case NOP:

                if (command.getCommandArg() == -1)
                    commands.remove(programCounter);

                programmcounterInc();
                break;

            case RLF:

                result = (getRegisterValue((command.getCommandArg() & 0x7F)) & 0xFF);
                result = result << 1;
                result = result + (getRegisterValue(STATUS_REGISTER) & 0x1);

                bankZero.getRegister()[STATUS_REGISTER] = (getRegisterValue(STATUS_REGISTER) & 0xFE) + (result >> 8);
                bankOne.getRegister()[STATUS_REGISTER] = (getRegisterValue(STATUS_REGISTER) & 0xFE) + (result >> 8);

                result = result & 0xFF;

                safeValueInRegister(command, result, true);

                programmcounterInc();
                break;

            case RRF:

                result = (getRegisterValue((command.getCommandArg() & 0x7F)) & 0xFF);
                result = result + ((getRegisterValue(STATUS_REGISTER) & 0x1) << 8);

                bankZero.getRegister()[STATUS_REGISTER] = (bankZero.getRegister()[STATUS_REGISTER] & 0xFE) + (result & 0x1);
                bankOne.getRegister()[STATUS_REGISTER] = (bankZero.getRegister()[STATUS_REGISTER] & 0xFE) + (result & 0x1);

                result = result >> 1;

                safeValueInRegister(command, result, true);

                programmcounterInc();
                break;

            case SUBWF:

                twoCompliment = (~registerW) + 1;

                twoCompliment = twoCompliment & 0x000000FF;
                result = add(getRegisterValue(command.getCommandArg() & 0x7F), twoCompliment);

                safeValueInRegister(command, result, true);
                programmcounterInc();
                break;

            case SWAPF:

                int tempResult = (getRegisterValue(command.getCommandArg() & 0x7F) & 0xF0) >> 4;

                result = ((getRegisterValue(command.getCommandArg() & 0x7F) & 0xF) << 4) + tempResult;

                safeValueInRegister(command, result, false);
                programmcounterInc();

                break;

            case XORWF:

                result = registerW ^ getRegisterValue((command.getCommandArg() & 0x7F));
                checkZeroFlag(result);

                safeValueInRegister(command, result, true);
                programmcounterInc();
                break;

            case BCF:

                if ((command.getCommandArg() & 0x7F) <= 80) {

                    if (Arrays.stream(equalRegister).anyMatch(r -> r == (command.getCommandArg() & 0x7F))) {

                        unsetBit(command.getCommandArg() & 0x7F, ((command.getCommandArg() & 0x380) >> 7), bankZero);
                        unsetBit(command.getCommandArg() & 0x7F, ((command.getCommandArg() & 0x380) >> 7), bankOne);
                    } else {
                        unsetBit(command.getCommandArg() & 0x7F, ((command.getCommandArg() & 0x380) >> 7), getCurrentBank());
                    }
                }

                programmcounterInc();

                break;

            case BSF:

                if ((command.getCommandArg() & 0x7F) <= 80) {

                    if (Arrays.stream(equalRegister).anyMatch(r -> r == (command.getCommandArg() & 0x7F))) {

                        setBit(command.getCommandArg() & 0x7F, ((command.getCommandArg() & 0x380) >> 7), bankZero);
                        setBit(command.getCommandArg() & 0x7F, ((command.getCommandArg() & 0x380) >> 7), bankOne);
                    } else {
                        setBit(command.getCommandArg() & 0x7F, ((command.getCommandArg() & 0x380) >> 7), getCurrentBank());
                    }
                }

                programmcounterInc();
                break;

            case BTFSC:

                result = ((getRegisterValue(command.getCommandArg() & 0x7F) >> ((command.getCommandArg() & 0x380) >> 7))) & 0x1;

                if (result == 0) {

                    commands.add(programCounter + 1, new CommandLineModel(0, CommandCode.NOP, -1, 0, null));
                }

                programmcounterInc();
                break;

            case BTFSS:

                result = ((getRegisterValue(command.getCommandArg() & 0x7F) >> ((command.getCommandArg() & 0x380) >> 7))) & 0x1;

                if (result == 1) {

                    commands.add(programCounter + 1, new CommandLineModel(0, CommandCode.NOP, -1, 0, null));
                }

                programmcounterInc();
                break;

            case ADDLW:

                result = add(registerW, command.getCommandArg());
                setRegisterValue(result, FLAG_REGISTER_W, null, true);
                programmcounterInc();
                break;

            case ANDLW:

                registerW = registerW & command.getCommandArg();
                checkZeroFlag(registerW);
                programmcounterInc();
                break;

            case CALL:

                tos.push(programCounter + 1);
                programCounter = ((getRegisterValue(10) & 0x18) << 8) + command.getCommandArg();
                setPCL();
                break;

            case CLRWDT:

                //TODO
                break;

            case GOTO:

                programCounter = ((getRegisterValue(10) & 0x18) << 8) + command.getCommandArg();
                setPCL();
                break;

            case IORLW:

                registerW = registerW | command.getCommandArg();
                checkZeroFlag(registerW);
                programmcounterInc();
                break;

            case MOVLW:

                registerW = command.getCommandArg();
                checkZeroFlag(registerW);
                programmcounterInc();
                break;

            case RETFIE:

                //TODO
                break;

            case RETLW:

                registerW = command.getCommandArg();
                programCounter = tos.pop();
                setPCL();
                break;

            case RETURN:

                programCounter = tos.pop();
                setPCL();
                break;

            case SLEEP:

                //TODO
                break;

            case SUBLW:

                // literal - w

                twoCompliment = (~registerW) + 1;

                twoCompliment = twoCompliment & 0x000000FF;
                result = add(twoCompliment, command.getCommandArg());

                setRegisterValue(result, FLAG_REGISTER_W, null, true);
                programmcounterInc();
                break;

            case XORLW:

                registerW = registerW ^ command.getCommandArg();
                checkZeroFlag(registerW);
                programmcounterInc();
                break;

            case DEFAULT:
                //TODO
                break;

        }

    }

    private void safeValueInRegister(CommandLineModel command, int result, boolean b) {
        if ((command.getCommandArg() & 0x80) > 0) {

            if (Arrays.stream(equalRegister).anyMatch(r -> r == (command.getCommandArg() & 0x7F))) {

                setRegisterValue(result, command.getCommandArg() & 0x7F, bankZero, b);
                setRegisterValue(result, command.getCommandArg() & 0x7F, bankOne, b);
            } else {
                setRegisterValue(result, command.getCommandArg() & 0x7F, getCurrentBank(), b);
            }

        } else {
            setRegisterValue(result, FLAG_REGISTER_W, null, b);
        }
    }

    private void programmcounterInc() {

        programCounter++;
        setPCL();
    }

    private void setPCL() {

        setRegisterValue(programCounter & 0xFF, 2, bankZero, false);
        setRegisterValue(programCounter & 0xFF, 2, bankOne, false);
    }

    private int getRegisterValue(int register) {

        MemoryBankDataModel bank = getCurrentBank();

        if (register == 0) {
            register = bank.getRegister()[4] & 0x7F;
            bank = (bank.getRegister()[4] & 0x80) > 0 ? bankOne : bankZero;
        }
        if (register != 0) {

            final int newFiveBitRegister = (bank == bankZero) ? register : register + 0x80;

            if (Arrays.stream(fiveBitRegister).filter(r -> r == newFiveBitRegister).count() > 0) {
                return bank.getRegister()[register] & 0x1F;

            } else if (register == 0x07) {
                return 0;

            } else if (register < 0x80) {
                return bank.getRegister()[register];
            } else {
                return 0;
            }
        }

        return 0;
    }

    private int add(int number1, int number2) {

        checkOverflowBetweenBitFourAndFive(number1, number2);

        int sum = number1 + number2;

        checkOverflowAtNineBit(sum);

        sum = sum & 0x000000FF;

        checkZeroFlag(sum);

        return sum;

    }

    private void setRegisterValue(int value, int register, MemoryBankDataModel bank, boolean areFlagsTargeted) {

        value = value & 0xFF;

        if (register == FLAG_REGISTER_W) {

            registerW = value;
        } else {

            if (register == 0) {

                register = bank.getRegister()[4] & 0x7F;
                bank = (bank.getRegister()[4] & 0x80) > 0 ? bankOne : bankZero;
            }

            if (register != 0) {

                if (register == 3) {

                    if (areFlagsTargeted) {

                        // d = value; g = bank.getRegister()[register];
                        // dddd dddd & 1110 0000
                        // ddd0 0000
                        // gggg gggg & 0001 1111
                        // 000g gggg + ddd0 0000
                        // dddg gggg

                        value = value & 0xE0;
                        bank.getRegister()[register] = bank.getRegister()[register] & 0x1F;
                        bank.getRegister()[register] = bank.getRegister()[register] + value;

                    } else {

                        // d = value; g = bank.getRegister()[register];
                        // dddd dddd & 1110 0111
                        // ddd0 0ddd
                        // gggg gggg & 0001 1000
                        // 000g g000 + ddd0 0ddd
                        // dddg gddd

                        value = value & 0xE7;
                        bank.getRegister()[register] = bank.getRegister()[register] & 0x18;
                        bank.getRegister()[register] = bank.getRegister()[register] + value;

                    }

                } else if (register == 2) {

                    bank.getRegister()[register] = value;
                    programCounter = (getRegisterValue(10) << 8) + getRegisterValue(2);
                } else {
                    bank.getRegister()[register] = value;
                }
            }
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

    private void setBit(int register, int indexBit, MemoryBankDataModel bank) {

        bank.getRegister()[register] = bank.getRegister()[register] & 0x000000FF;
        bank.getRegister()[register] = bank.getRegister()[register] | (0x00000001 << indexBit);
    }

    private void unsetBit(int register, int indexBit, MemoryBankDataModel bank) {

        bank.getRegister()[register] = bank.getRegister()[register] & 0x000000FF;

        int mask = (int) (Math.pow(2, indexBit) - 1);

        bank.getRegister()[register] = bank.getRegister()[register] & ((0xFFFFFFFE << indexBit) | mask);
    }

    public MemoryBankDataModel getCurrentBank() {

        int bankStatus = bankOne.getRegister()[3];
        bankStatus = bankStatus & 0x20;

        return (bankStatus > 0) ? bankOne : bankZero;
    }

    public int getRegisterW() {
        return registerW;
    }

    public ArrayList<CommandLineModel> getCommands() {
        return commands;
    }

    public MemoryBankDataModel getBankZero() {
        return bankZero;
    }

    public MemoryBankDataModel getBankOne() {
        return bankOne;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    @Override
    public String toString() {
        return "MicroChipController{" + "registerW=" + registerW + "(0x" + Integer.toHexString(registerW).toUpperCase() + "),(Wert2) Register 0x0D=" + "0x" + Integer.toHexString(getCurrentBank().getRegister()[0x0D]).toUpperCase()
                + ", (Wert1) Register 0x0C=" + "0x" + Integer.toHexString(getCurrentBank().getRegister()[0x0C]).toUpperCase() + ", programCounter=" + programCounter + ", flagC= "
                + (bankOne.getRegister()[3] & 0x1) + ", flagDC= " + ((bankOne.getRegister()[3] & 0x2) >> 1) + ", flag0= "
                + ((bankOne.getRegister()[3] & 0x4) >> 2) + ", BefehlsCode= " + code + '}';
    }
}
