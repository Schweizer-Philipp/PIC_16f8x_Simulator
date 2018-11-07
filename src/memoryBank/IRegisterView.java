package memoryBank;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * hso.ra.java.simulator.pic16f8x
 * memoryBank
 * Mike Bruder, Philipp Schweizer
 * 27.10.2018
 */
public interface IRegisterView {
    IntegerProperty addressProperty();

    int getAddress();

    StringProperty nameProperty();

    void setName(String name);
}
