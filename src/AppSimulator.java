import controller.FileReader;
import controller.MicroController;
import controller.RootLocator;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AppSimulator {

    public static void main(String[] args) throws IOException {
        System.out.println("App Simulator");
        FileReader fileReader = new FileReader();
        MicroController m = new MicroController();
        fileReader.readLine(m);

        for (int i = 0; i <100000 ; i++) {

            System.out.println(m.toString());
            m.executeCommand(m.getCommands().get(m.getPC()));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
