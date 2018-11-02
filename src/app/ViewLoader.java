package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' app
 * ' Mike Bruder
 * ' 20.10.2018
 */
public final class ViewLoader {


    private static final String PACKAGE_PATH = "/de/robatsky/microcontrollerui/";

    private ViewLoader() {
    }

    public static Pane load(String fxmlPath, Stage stage) {
        return loadFXML(fxmlPath, stage, null, null);
    }

    public static Pane load(String fxmlPath, Stage stage, Model model, Class<?> clazz) {
        return loadFXML(fxmlPath, stage, model, clazz);
    }

    private static Pane loadFXML(String fxmlPath, Stage stage, Model model, Class<?> clazz) {
        Objects.requireNonNull(fxmlPath);
        Objects.requireNonNull(stage);

        if (fxmlPath.isEmpty()) {
            throw new IllegalArgumentException("Path to FXML File must not be empty");
        }


        Pane viewToLoad = new Pane();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewLoader.class.getResource(fxmlPath));

            if (clazz != null && model != null) {
                loader.setControllerFactory(param -> tryToCreateInstance(model, clazz, param));
            }

            viewToLoad = loader.load();

            Controlable controller = loader.getController();
            controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return viewToLoad;
    }

    private static Object tryToCreateInstance(Model model, Class<?> modelClazz, Class<?> param) {
        try {
            for (Constructor<?> c : param.getConstructors()) {
                if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == modelClazz) {
                    return c.newInstance(modelClazz.cast(model)); // new MemoryBankController((MemoryBankModel)model));
                }
            }
            return param.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException();
        }
    }
}
