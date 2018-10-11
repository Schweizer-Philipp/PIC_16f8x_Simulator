import controller.FileReader;
import controller.RootLocator;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AppSimulator {

    public static void main(String[] args) throws IOException {
        System.out.println("App Simulator");
        FileReader fileReader = new FileReader();
        fileReader.readLine();
    }
}
