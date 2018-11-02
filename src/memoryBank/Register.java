package memoryBank;

import javafx.beans.property.*;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' memoryBank
 * ' Mike Bruder
 * ' 27.10.2018
 */
public class Register implements IRegisterView {
    private StringProperty name;

    private IntegerProperty address;
    private BooleanProperty valueChanged;

    public Register(int address, String name, boolean valueChanged) {
        this.valueChanged = new SimpleBooleanProperty(valueChanged);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleIntegerProperty(address);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public boolean isValueChanged() {
        return valueChanged.get();
    }

    public BooleanProperty valueChangedProperty() {
        return valueChanged;
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    public int getAddress() {
        return address.get();
    }

    public IntegerProperty addressProperty() {
        return address;
    }

    @Override
    public String toString() {
        return "name=" + name.get() + ", address=" + address.get();
    }
}
