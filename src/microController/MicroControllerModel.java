package microController;

import app.ControlsController;
import memoryBank.MemoryBankDataModel;
import util.RegisterDataParser;
import util.RowElement;
import util.RowList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * hso.ra.java.simulator.pic16f8x
 * microController
 * Mike Bruder, Philipp Schweizer
 * 27.10.2018
 */
public class MicroControllerModel {

    private static final int TRIS_A = 5;

    private static final int TRIS_B = 6;

    private static final int PORT_A = 5;

    private static final int PORT_B = 6;

    private RowList<PinModel> pins;

    public MicroControllerModel() {
        this.pins = new RowList<>();
        initializePins();
    }

    private void initializePins() {
        final String[][] pinNames = {{"RA2 <-->", "<--> RA1"}, {"RA3 <-->", "<--> RA0"},
                {"RA4/T0CKI <-->", "<--- OSC1/CLKIN"},

                {"MCLR --->", "---> OSC2/CLKOUT"}, {"Vss --->", "<--- Vdd"}, {"RB0/INT <-->", "<--> RB7"},

                {"RB1 <-->", "<--> RB6"}, {"RB2 <-->", "<--> RB5"}, {"RB3 <-->", "<--> RB4"},};

        PinModel leftPin = new PinModel(pinNames[0][0], 1, true, "RA2",false);
        PinModel rightPin = new PinModel(pinNames[0][1], 18, true, "RA1",false);
        pins.add(leftPin, rightPin);

        leftPin = new PinModel(pinNames[1][0], 2, true, "RA3",false);
        rightPin = new PinModel(pinNames[1][1], 17, true, "RA0",false);
        pins.add(leftPin, rightPin);

        leftPin = new PinModel(pinNames[2][0], 3, true, "RA4",false);
        rightPin = new PinModel(pinNames[2][1], 16, false, null,false);
        pins.add(leftPin, rightPin);

        leftPin = new PinModel(pinNames[3][0], 4, false, null,false);
        rightPin = new PinModel(pinNames[3][1], 15, false, null,false);
        pins.add(leftPin, rightPin);

        leftPin = new PinModel(pinNames[4][0], 5, false, null,false);
        rightPin = new PinModel(pinNames[4][1], 14, false, null,false);
        pins.add(leftPin, rightPin);

        leftPin = new PinModel(pinNames[5][0], 6, true, "RB0",false);
        rightPin = new PinModel(pinNames[5][1], 13, true, "RB7",false);
        pins.add(leftPin, rightPin);

        leftPin = new PinModel(pinNames[6][0], 7, true, "RB1",false);
        rightPin = new PinModel(pinNames[6][1], 12, true, "RB6",false);
        pins.add(leftPin, rightPin);

        leftPin = new PinModel(pinNames[7][0], 8, true, "RB2",false);
        rightPin = new PinModel(pinNames[7][1], 11, true, "RB5",false);
        pins.add(leftPin, rightPin);

        leftPin = new PinModel(pinNames[8][0], 9, true, "RB3",false);
        rightPin = new PinModel(pinNames[8][1], 10, true, "RB4",false);
        pins.add(leftPin, rightPin);

    }

    public RowList<PinModel> getPins() {
        return pins;
    }

    public PinModel getIOPin(int index, String letter) {

        String search = "R" + letter + index;

        List<PinModel> listOfPins = pins.stream().map(RowElement::getRightElement).filter(PinModel::isIOPin)
                .collect(Collectors.toList());
        listOfPins.addAll(
                pins.stream().map(RowElement::getLeftElement).filter(PinModel::isIOPin).collect(Collectors.toList()));

        return listOfPins.stream().filter(p -> p.getName().contains(search)).findFirst().get();
    }

    public void updateIOPins() {
        MemoryBankDataModel bankZero = MemoryBankDataModel.getInstanceBankZero();
        MemoryBankDataModel bankOne = MemoryBankDataModel.getInstanceBankOne();

        int port_a = bankZero.getRegister()[PORT_A];
        int port_b = bankZero.getRegister()[PORT_B];
        int tris_a = bankOne.getRegister()[TRIS_A];
        int tris_b = bankOne.getRegister()[TRIS_B];

        port_a = port_a & 0xFF;
        port_b = port_b & 0xFF;
        tris_a = tris_a & 0xFF;
        tris_b = tris_b & 0xFF;

        //TRIS_A & PORT_A Register
        for (int i = 0; i < 5; i++) {
            PinModel model = getIOPin(i, "A");
            model.setIsOutputProperty(((tris_a >> i) & 0x1) == 1);
            model.setStatus(((port_a >> i) & 0x1) == 1);
        }
        //TRIS_B & PORT_B Register
        for (int i = 0; i < 8; i++) {
            PinModel model = getIOPin(i, "B");
            model.setIsOutputProperty(((tris_b >> i) & 0x1) == 1);
            model.setStatus(((port_b >> i) & 0x1) == 1);
        }

        ControlsController.getMemoryBankViewModel()
                .changeListData(RegisterDataParser.getRegisterModel(bankZero.getRegister(), bankOne.getRegister()));

    }

    public void updatePortRegister() {

        checkTimer0();

        MemoryBankDataModel bankZero = MemoryBankDataModel.getInstanceBankZero();
        int newPortA = 0; // 00000
        int newPortB = 0; // 00000


        for (int i = 0; i < 5; i++) {
            PinModel model = getIOPin(i, "A");
            if (model.isOn()) {
                newPortA |= (1 << i);
            }
        }
        bankZero.getRegister()[PORT_A] = newPortA;

        for (int i = 0; i < 8; i++) {
            PinModel model = getIOPin(i, "B");
            if (model.isOn()) {
                newPortB |= (1 << i);
            }
        }
        bankZero.getRegister()[PORT_B] = newPortB;

        ControlsController.getMemoryBankViewModel().changeListData(RegisterDataParser
                .getRegisterModel(bankZero.getRegister(), MemoryBankDataModel.getInstanceBankOne().getRegister()));

    }

    private void checkTimer0() {

        if (isPinInput()) {

            if (didPinChange()) {

                if (rightEdge()) {

                    ControlsController.getInstance().getMicroController().IncTimer0(1,1);
                }
            }
        }
    }

    private boolean rightEdge() {

        int TOSE = (MemoryBankDataModel.getInstanceBankOne().getRegister()[1] & 0x10) >> 4;

        int newValue = (getIOPin(4, "A").isOn()) ? 1 : 0;

        // 1 1 -> 0 = true
        // 0 0->  1 = true

        return TOSE != newValue;
    }

    private boolean didPinChange() {

        int oldValue = (MemoryBankDataModel.getInstanceBankZero().getRegister()[PORT_A] & 0x10) >> 4;

        int newValue = (getIOPin(4, "A").isOn()) ? 1 : 0;

        return oldValue != newValue;
    }

    private boolean isPinInput() {

        int tris_a = (MemoryBankDataModel.getInstanceBankOne().getRegister()[TRIS_A] & 0x10) >> 4;

        return tris_a == 1;
    }
}
