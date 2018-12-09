import app.ViewLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * hso.ra.java.simulator.pic16f8x
 * PACKAGE_NAME
 * Mike Bruder, Philipp Schweizer
 * 18.10.2018
 */
public class AppSimulator extends Application {

    private static final String APP_TITLE = "pic16f8x Simulator";

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {

//		  if (Desktop.isDesktopSupported()) {
//				File file = new File(System.getProperty("user.home") + "/datasheet.pdf");
//				if (!file.exists()) {
//					 try (InputStream is = getClass().getResourceAsStream("./datasheet.pdf");
//							 OutputStream os = new FileOutputStream(file)) {
//
//						  byte[] buffer = new byte[1024];
//						  int length;
//						  while ((length = is.read(buffer)) > 0) {
//								os.write(buffer, 0, length);
//						  }
//					 }
//				}
//				Desktop.getDesktop().open(file);
//		  }

        this.primaryStage = primaryStage;

        primaryStage.setOnCloseRequest(t -> System.exit(0));

        AnchorPane rootPane = loadRootPane();

        primaryStage.setScene(new Scene(rootPane, 1100, 560));
        primaryStage.setTitle(APP_TITLE);
        primaryStage.show();


    }

    private AnchorPane loadRootPane() {
        return (AnchorPane) ViewLoader.load("/app/root.fxml", primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
//		  MicroChipController microChipController = new MicroChipController();
//		  FileReader.getCommandLineModelList(microChipController);

        /*MicroChipController m = new MicroChipController();
        m.getCommands().addAll(FileReader.getCommandLineModelList());

        for (int i = 0; i < 1; i++) {

            m.executeCommand(m.getCommands().get(m.getProgramCounter()));
            System.out.println(m.toString() + "\r\n");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
