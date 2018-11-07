package microController;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * hso.ra.java.simulator.pic16f8x
 * microController
 * Mike Bruder, Philipp Schweizer
 * 20.10.2018
 */
public class PinModel {

    private SimpleStringProperty nameProperty;

    private SimpleIntegerProperty pinNumberProperty;

    private SimpleBooleanProperty statusProperty;

    public PinModel(String name, int nr) {
        this.nameProperty = new SimpleStringProperty(name);
        this.pinNumberProperty = new SimpleIntegerProperty(nr);
        this.statusProperty = new SimpleBooleanProperty(true);
    }

    public void toggle() {
        this.statusProperty.set(!statusProperty.get());
    }

    public SimpleStringProperty nameProperty() {
        return nameProperty;
    }

    public String getName() {
        return nameProperty.get();
    }

    public SimpleIntegerProperty pinNumberProperty() {
        return pinNumberProperty;
    }

    public int getNr() {
        return pinNumberProperty.get();
    }

    public SimpleBooleanProperty statusProperty() {
        return statusProperty;
    }

    public boolean isOn() {
        return statusProperty.get();
    }

    @Override
    public String toString() {
        return getName() + " " + getNr();
    }
}
