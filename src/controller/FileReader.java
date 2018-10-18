package controller;

import java.io.*;

public class FileReader {

    private BufferedReader bufferedReader;
    private FileInputStream fileInputStream;
    private RootLocator rootLocator;
    private String line;

    public FileReader() throws FileNotFoundException {
        rootLocator = new RootLocator();
        fileInputStream = new FileInputStream(rootLocator.getRoot(1));
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
    }

    public void readLine(MicroController m) throws IOException {
        while ((line = bufferedReader.readLine()) != null) {
            if (line.charAt(0) != ' ') {

                m.getCommands().add(CommandParser.commandParser(line));
            }
        }
        bufferedReader.close();
    }
}
