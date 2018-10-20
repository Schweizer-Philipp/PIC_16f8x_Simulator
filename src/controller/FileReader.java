package controller;

import java.io.*;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class FileReader {


    public static void readLine(MicroController m) {

        RootLocator rootLocator = new RootLocator();

        InputStream is = FileReader.class.getResourceAsStream("/TPicSim2.LST");

        try (FileInputStream fileInputStream = new FileInputStream(rootLocator.getRoot(2));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("windows-1252")))) {

            m.getCommands().addAll(bufferedReader.lines().filter(line -> !line.startsWith(" ")).map(CommandParser::commandParser).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
