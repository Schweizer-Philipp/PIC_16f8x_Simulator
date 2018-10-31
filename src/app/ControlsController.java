package app;

import microController.MicroChipController;
import util.FileReader;

import java.util.Objects;

/**
 * ' hso.ra.java.simulator.pic16f8x
 * ' app
 * ' Mike Bruder
 * ' 31.10.2018
 */
public class ControlsController
{
	 private static MicroChipController microChipController;

	 private static ControlsController controlsController;

	 private ControlsController()
	 {
		  microChipController = new MicroChipController();
	 }

	 public static ControlsController getInstance()
	 {

		  if (controlsController == null) {

				controlsController = new ControlsController();
		  }

		  return controlsController;
	 }

	 public void start()
	 {
	 	 microChipController.getCommands().addAll(Objects.requireNonNull(FileReader.getCommandLineModelList()));

	 }

	 public void stop()
	 {

	 }

	 public void step()
	 {

	 }

	 public void restart()
	 {

	 }
}
