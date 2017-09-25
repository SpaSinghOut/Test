package commands;

public class PingPongCommand extends Command{
	public PingPongCommand() {
		super();
		setCommandName("ping");
	}
	@Override
	public boolean execute(String[] args) {
		say("Pong");
		return true;
	}

}
