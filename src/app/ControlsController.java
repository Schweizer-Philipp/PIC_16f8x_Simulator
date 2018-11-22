package app;

import memoryBank.MemoryBankViewModel;
import memoryBank.WRegisterController;
import microController.MicroChipController;
import util.FileReader;
import util.RegisterDataParser;

import java.util.Objects;

/**
 * hso.ra.java.simulator.pic16f8x
 * app
 * Mike Bruder, Philipp Schweizer
 * 31.10.2018
 */
public class ControlsController {

    private MicroChipController microChipController = new MicroChipController();

    private static ControlsController controlsController;

    private LogFileCommandsController logFileCommandsController;

    private static MemoryBankViewModel memoryBankViewModel;

    private WRegisterController wRegisterController;

    private boolean startThreadActive = false;

    private ControlsController() {
        setCommandsForMicroController();
    }

    public static ControlsController getInstance() {

        if (controlsController == null) {

            controlsController = new ControlsController();
        }

        return controlsController;
    }

    public void start() {

        startThreadActive = true;
        new Thread(() -> {

            while (startThreadActive) {

                microChipController.executeCommand(microChipController.getCommands().get(microChipController.getProgramCounter()));
                memoryBankViewModel.changeListData(RegisterDataParser.
                        getRegisterModel(microChipController.getBankZero().getRegister(), microChipController.getBankOne().
                                getRegister()));

                logFileCommandsController.update(microChipController.getLastExecutedCommand());
                wRegisterController.update("0x" + Integer.toHexString(microChipController.getRegisterW()), String.valueOf(microChipController.getCycle()));
                System.out.println(microChipController.toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }

    public void stop() {

        startThreadActive = false;
    }

    public void step() {

        startThreadActive = false;
        microChipController.executeCommand(microChipController.getCommands().get(microChipController.getProgramCounter()));
        memoryBankViewModel.changeListData(RegisterDataParser.
                getRegisterModel(microChipController.getBankZero().getRegister(), microChipController.getBankOne().
                        getRegister()));
        logFileCommandsController.update(microChipController.getLastExecutedCommand());
        wRegisterController.update("0x" + Integer.toHexString(microChipController.getRegisterW()), String.valueOf(microChipController.getCycle()));
        System.out.println(microChipController.toString());
    }

    public void restart() {
        startThreadActive = false;
        microChipController.restart();
        logFileCommandsController.reset();
        memoryBankViewModel.changeListData(RegisterDataParser.
                getRegisterModel(microChipController.getBankZero().getRegister(), microChipController.getBankOne().
                        getRegister()));
        wRegisterController.update("0x" + Integer.toHexString(microChipController.getRegisterW()), String.valueOf(microChipController.getCycle()));
    }

    public static void setMemoryBankViewModel(MemoryBankViewModel memoryBankViewModel) {

        ControlsController.memoryBankViewModel = memoryBankViewModel;
    }

    public void setCommandsForMicroController() {

        microChipController.getCommands().addAll((Objects.requireNonNull(FileReader.getCommandLineModelList())));
    }

    public void setLogFileCommandsController(LogFileCommandsController logFileCommandsController) {
        this.logFileCommandsController = logFileCommandsController;
    }

    public void setwRegisterController(WRegisterController wRegisterController) {
        this.wRegisterController = wRegisterController;
    }

    public MicroChipController getMicroController() {

        return microChipController;
    }
}
