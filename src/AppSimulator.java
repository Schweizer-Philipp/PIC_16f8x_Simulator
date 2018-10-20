import controller.FileReader;
import controller.MicroController;

import java.io.IOException;

public class AppSimulator {

    public static void main(String[] args){

        System.out.println("App Simulator");
        MicroController m = new MicroController();
        FileReader.readLine(m);

        for (int i = 0; i <100000 ; i++) {

            System.out.println(m.toString());
            m.executeCommand(m.getCommands().get(m.getProgramCounter()));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
