package util;

import app.ControlsController;
import commandLine.CommandLineModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {

    private static StringProperty currentFile = new SimpleStringProperty(ControlsController.class.getResource("/test/TPicSim4.LST").getFile());

    public static List<CommandLineModel> getCommandLineModelList() {

        try (FileInputStream fileInputStream = new FileInputStream(new File(currentFile.get()));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, Charset.forName("windows-1252")))) {
            return bufferedReader.lines().filter(line -> !line.startsWith(" ")).map(CommandParser::commandParser).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setCurrentFile(String currentFile) {
        FileReader.currentFile.set(currentFile);
    }

    public static String getCurrentFile() {
        return currentFile.get();
    }
}
