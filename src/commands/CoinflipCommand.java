package commands;

public class CoinflipCommand extends Command {
	public CoinflipCommand() {
		super();
		this.setCommandName("coinflip");
		addAlias("coin", "flip");
	}
	@Override
	public boolean execute(String[] args) {
		say("Just a second, flipping a coin");
		say(1, "The result is: " + ( Math.random() > 0.5 ? "Heads!" : "Tails!") );
		return true;
	}
	
}
