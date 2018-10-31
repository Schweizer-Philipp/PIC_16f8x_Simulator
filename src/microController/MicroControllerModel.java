package microController;

import util.RowList;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' microController
 * ' Mike Bruder
 * ' 27.10.2018
 */
public class MicroControllerModel {
    private RowList<PinModel> pins;

    public MicroControllerModel() {
        this.pins = new RowList<>();
        initializePins();
    }

    private void initializePins() {
        final String[][] pinNames = {
                {"RA2 <-->", "<--> RA1"},
                {"RA3 <-->", "<--> RA0"},
                {"RA2/T0CKI <-->", "<--- OSC1/CLKIN"},
                {"MCLR --->", "---> OSC2/CLKOUT"},
                {"Vss --->", "<--- Vdd"},
                {"RB0/INT <-->", "<--> RB7"},
                {"RB1 <-->", "<--> RB6"},
                {"RB2 <-->", "<--> RB5"},
                {"RB3 <-->", "<--> RB4"},
        };

        for (int i = 0; i < pinNames.length; i++) {
            PinModel leftPin = new PinModel(pinNames[i][0], (i + 1));
            PinModel rightPin = new PinModel(pinNames[i][1], (pinNames.length * 2) - i);
            pins.add(leftPin, rightPin);
        }

    }

    public RowList<PinModel> getPins() {
        return pins;
    }
}
