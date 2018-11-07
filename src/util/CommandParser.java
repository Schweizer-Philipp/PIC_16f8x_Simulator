package util;

import commandLine.CommandCode;
import commandLine.CommandLineModel;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * hso.ra.java.simulator.pic16f8x
 * util
 * Mike Bruder, Philipp Schweizer
 * 7.11.2018
 */

public class CommandParser {


    public static CommandLineModel commandParser(String line) {

        String[] splittedLine = line.split(" ");

        splittedLine = Stream.of(splittedLine).filter(s -> !s.isEmpty()).toArray(String[]::new);
        splittedLine = buildComment(splittedLine);

        int commandAddress = Integer.decode("0x" + splittedLine[0]);
        int lineNumber = Integer.valueOf(splittedLine[2]);

        int[] commandCodeData = getCommandCodeData(Integer.decode("0x" + splittedLine[1]));

        CommandCode commandCode = CommandCode.getCommandCodesByValue(commandCodeData[0]);

        int commandArg = getCommandArg(Integer.decode("0x" + splittedLine[1]), commandCodeData[1]);

        String label = String.valueOf(splittedLine[3]);

        return new CommandLineModel(commandAddress, commandCode, commandArg, lineNumber, label);
    }

    private static int getCommandArg(int number, int mask) {

        return number & mask;
    }

    private static int[] getCommandCodeData(int number) {

        int[] commandCodeData = new int[2];
        int[] mask = {0xFFFF, 0xFF80, 0xFF00, 0xFE00, 0xFC00, 0xF800};

        for (int selectedMask : mask) {

            if (CommandCode.DEFAULT != CommandCode.getCommandCodesByValue(number & selectedMask)) {

                commandCodeData[0] = number & selectedMask;
                commandCodeData[1] = ~selectedMask;
                return commandCodeData;
            }
        }

        commandCodeData[0] = -1;
        commandCodeData[1] = -2;

        return commandCodeData;
    }

    private static String[] buildComment(String[] splitetLine) {

        String[] splittedString = Arrays.copyOfRange(splitetLine, 0, 4);
        splittedString[3] = String.join(" ", Arrays.copyOfRange(splitetLine, 3, splitetLine.length));

        return splittedString;
    }

}
