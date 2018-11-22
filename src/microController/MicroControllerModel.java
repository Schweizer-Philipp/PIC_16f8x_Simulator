package microController;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.print.Collation;
import sun.text.resources.sk.CollationData_sk;
import util.RowList;

/**
 * hso.ra.java.simulator.pic16f8x
 * microController
 * Mike Bruder, Philipp Schweizer
 * 27.10.2018
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
                {"RA4/T0CKI <-->", "<--- OSC1/CLKIN"},
                
                {"MCLR --->", "---> OSC2/CLKOUT"},
                {"Vss --->", "<--- Vdd"},
                {"RB0/INT <-->", "<--> RB7"},
                
                {"RB1 <-->", "<--> RB6"},
                {"RB2 <-->", "<--> RB5"},
                {"RB3 <-->", "<--> RB4"},
        };

       
            PinModel leftPin = new PinModel(pinNames[0][0],1,true,"RA2");
            PinModel rightPin = new PinModel(pinNames[0][1],18, true, "RA1");
            pins.add(leftPin, rightPin);
            
            leftPin = new PinModel(pinNames[1][0],2,true,"RA3");
            rightPin = new PinModel(pinNames[1][1],17, true, "RA0");
            pins.add(leftPin, rightPin);
            
            leftPin = new PinModel(pinNames[2][0],3,true,"RA4");
            rightPin = new PinModel(pinNames[2][1],16, false, null);
            pins.add(leftPin, rightPin);
            
            leftPin = new PinModel(pinNames[3][0],4,false, null);
            rightPin = new PinModel(pinNames[3][1],15, false, null);
            pins.add(leftPin, rightPin);
           
            leftPin = new PinModel(pinNames[4][0],5,false,null);
            rightPin = new PinModel(pinNames[4][1],14, false, null);
            pins.add(leftPin, rightPin);
          
            leftPin = new PinModel(pinNames[5][0],6,true,"RB0");
            rightPin = new PinModel(pinNames[5][1],13, true, "RB7");
            pins.add(leftPin, rightPin);
         
            leftPin = new PinModel(pinNames[6][0],7,true,"RB1");
            rightPin = new PinModel(pinNames[6][1],12, true, "RB6");
            pins.add(leftPin, rightPin);
        
            leftPin = new PinModel(pinNames[7][0],8,true,"RB2");
            rightPin = new PinModel(pinNames[7][1],11, true, "RB5");
            pins.add(leftPin, rightPin);
       
            leftPin = new PinModel(pinNames[8][0],9,true,"RB3");
            rightPin = new PinModel(pinNames[8][1],10, true, "RB4");
            pins.add(leftPin, rightPin);
      
    }

    public RowList<PinModel> getPins() {
        return pins;
    }
    
    public PinModel getIOPin(int index, String letter) {
    	
    	String search = "R"+letter+String.valueOf(index);
    	
    	List<PinModel> listOfPins = pins.stream().map(row -> row.getRightElement()).filter(p -> p.isIOPin()==true).collect(Collectors.toList());
    	listOfPins.addAll(pins.stream().map(row -> row.getLeftElement()).filter(p -> p.isIOPin()==true).collect(Collectors.toList()));
    	
    	return listOfPins.stream().filter(p -> p.getName().equals(search)).findFirst().get();
    }
}
