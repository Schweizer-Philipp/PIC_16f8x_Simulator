package controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.CommandCode;
import model.CommandLineModel;
import org.jetbrains.annotations.Nullable;

public class CommandParser {


    public static CommandLineModel commandParser(String line) {
		
		String[] splitetLine = line.split(" ");

		splitetLine = Stream.of(splitetLine).filter(s -> !s.equals("")).toArray(String[]::new);

        splitetLine = buildComment(splitetLine);

		int commandAdress = Integer.valueOf(splitetLine[0]);

		int lineNumber = Integer.valueOf(splitetLine[2]);

        int[] commandCodeData = getCommandCodeData(Integer.decode("0x"+splitetLine[1]));

        CommandCode comandCode = CommandCode.getCommandCodesByValue(commandCodeData[0]);

        int commandArg = getCommandArg(Integer.decode("0x"+splitetLine[1]),commandCodeData[1]);

		String label = String.valueOf(splitetLine[3]);

		return new CommandLineModel(commandAdress,comandCode,commandArg,lineNumber,label);
	}

    private static int getCommandArg(int number, int mask) {

        return number&mask;
    }

    private static int[] getCommandCodeData(int number) {

        int[] commandCodeData = new int[2];
        int[] mask = {0xFFFF,0xFF80,0xFF00,0xFE00,0xFC00,0xF800};

        for (int selectedMask : mask) {

            if(CommandCode.DEFAULT!=CommandCode.getCommandCodesByValue(number&selectedMask)){

                commandCodeData[0] = number&selectedMask;
                commandCodeData[1] = ~selectedMask;
                return commandCodeData;
            }
        }

        commandCodeData[0] = -1;
        commandCodeData[1] = -2;

        return commandCodeData;
    }

    private static String[] buildComment(String[] splitetLine) {

        String[] splitetString = Arrays.copyOfRange(splitetLine, 0, 4);

        splitetString[3] = Arrays.stream(Arrays.copyOfRange(splitetLine, 3, splitetLine.length))
                .collect(Collectors.joining(" "));

        return splitetString;
    }

}
