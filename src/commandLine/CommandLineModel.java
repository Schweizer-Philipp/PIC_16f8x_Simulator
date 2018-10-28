package commandLine;

public class CommandLineModel
{

	private int commandAdress;
	private CommandCode commandCode;
	private int commandArg;
	private int line;
	private String label;
	
	public CommandLineModel(int commandAdress, CommandCode commandCode, int commandArg, int line, String label) {
		this.commandAdress = commandAdress;
		this.commandCode = commandCode;
		this.commandArg = commandArg;
		this.line = line;
		this.label = label;
	}

	@Override
	public String toString() {
		return "CommandLineModel{" +
				"commandAdress=" + commandAdress +
				", commandCode=" + commandCode +
				", commandArg=" + commandArg +
				", line=" + line +
				", label='" + label + '\'' +
				'}';
	}

	public int getCommandAdress() {return commandAdress;}

	public CommandCode getCommandCode() {return commandCode;}

	public int getCommandArg() {return commandArg;}

	public int getLine() {return line;}

	public String getLabel() {return label;}

}
