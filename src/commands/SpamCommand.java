package commands;

public class SpamCommand extends Command {
	int repeat;
	final int max = 20;
	public SpamCommand() {
		super();
		setCommandName("spam");
	}
	
	@Override
	public boolean execute(String[] args) {
		if(repeat++ < max)
			say(1, "T.spam");
		else return false;
		return true;
	}

}
