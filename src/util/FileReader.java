package util;

import javafx.beans.property.StringProperty;
import microController.MicroChipController;

import java.io.*;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class FileReader
{

    private static StringProperty currentFile;

    public static void readLine(MicroChipController microChipController) {

        try (FileInputStream fileInputStream = new FileInputStream(new File(currentFile.get()));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, Charset.forName("windows-1252")))) {

            microChipController.getCommands().addAll(bufferedReader.lines().filter(line -> !line.startsWith(" ")).map(CommandParser::commandParser).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCurrentFile(String currentFile)
    {
        FileReader.currentFile.set(currentFile);
    }
}
