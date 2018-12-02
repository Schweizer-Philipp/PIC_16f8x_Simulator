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

    private SimpleBooleanProperty ioPinProperty;

    private String IOPinName;

    public PinModel(String name, int nr, boolean IOPin, String IOPinName) {
        this.ioPinProperty = new SimpleBooleanProperty(IOPin);
        this.IOPinName = IOPinName;
        this.nameProperty = new SimpleStringProperty(name);
        this.pinNumberProperty = new SimpleIntegerProperty(nr);
        this.statusProperty = new SimpleBooleanProperty(true);
    }

    public boolean isIOPin() {
        return ioPinProperty.get();
    }

    public String getIOPinName() {
        return IOPinName;
    }

    public void toggle() {
        this.statusProperty.set(!statusProperty.get());
    }

    public void setStatus(boolean status) {
        this.statusProperty.set(status);
    }


    public SimpleBooleanProperty statusProperty()
    {
        return statusProperty;
    }

    public SimpleBooleanProperty ioPinProperty()
    {
        return ioPinProperty;
    }

    public void setIoPinProperty(boolean ioPinProperty)
    {
        this.ioPinProperty.set(ioPinProperty);
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

    public boolean isOn() {
        return statusProperty.get();
    }

    @Override
    public String toString() {
        return getName() + " " + getNr();
    }
}
