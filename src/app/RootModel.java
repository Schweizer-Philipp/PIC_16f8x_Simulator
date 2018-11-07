package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * hso.ra.java.simulator.pic16f8x
 * app
 * Mike Bruder, Philipp Schweizer
 * 18.10.2018
 */
public class RootModel {

    private StringProperty file;

    public RootModel() {
        this.file = new SimpleStringProperty();
    }

    public StringProperty fileProperty() {
        return file;
    }

    public String getFile() {
        return file.get();
    }

    public void setFile(String file) {
        this.file.set(file);
    }

}
