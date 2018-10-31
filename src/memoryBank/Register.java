package memoryBank;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' memoryBank
 * ' Mike Bruder
 * ' 27.10.2018
 */
public class Register implements IRegisterView {
    private StringProperty name;

    private IntegerProperty address;

    public Register(int address, String name) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleIntegerProperty(address);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
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
