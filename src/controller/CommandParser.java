package controller;
import java.util.stream.Stream;
import model.CommandLineModel;

public class CommandParser {

	public static CommandLineModel commandParser(String line) {
		
		String[] splitetLine = line.split(" ");
		
		Stream.of(splitetLine).forEach(s -> s.trim());
		
		int commandAdress = Integer.valueOf(splitetLine[0]);

		return null;
	}

}
