package commands;

public class RollCommand extends Command{
	public RollCommand() {
		super();
		setCommandName("roll");
		setArgs("(opt)minValue", "maxValue");
	}
	@Override
	public boolean execute(String[] args) {
		try {
			switch(args.length) {
			case 1:		
				oneArg(Integer.parseInt(args[0]));								
				break;
			case 2:		
				readingArg = 1;
				int x = Integer.parseInt(args[0]);
				readingArg = 2;
				int y = Integer.parseInt(args[1]);
				twoArg(x, y);	
				break;
			default:	
				throw new Exception();
			}
		}catch(Exception e) {
			help();
			return false;
		}
		
		return true;
	}
	private void oneArg(int roll) {
		say("Rolling a number between 1 and " + roll);
		say(1,"You rolled: " + ((int)(Math.random() * roll + 1)));
	}
	private void twoArg(int min, int max) {
		say(String.format("Rolling a number between %d and %d", min, max));
		say(1, String.format("You rolled: %d", ((int)(Math.random() * (max-min + 1) + min))));
	}
}
