package main;

public class Parser {
	static CommandContainer parse(String raw, String trigger) {
		trigger = trigger.toLowerCase();
		String beheaded = raw.replaceFirst(trigger, "");
		String[] all = (beheaded.split(" "));
		String command = all[0];
		String argsString = beheaded.replaceFirst(command, "");
		System.out.println("args: " + argsString);
		String[] args = null;
		if(argsString.length() == 0);
		else {
			argsString = argsString.replaceFirst(" ", "");
			System.out.println("about to split: " + argsString);
			args = argsString.split(" ");
			System.out.println("first arg: " + args[0]);
		}
		return new CommandContainer(raw, beheaded, command, args);
	} 
	static class CommandContainer{
		public String raw;
		public String beheaded;
		public String commandName;
		public String[] args;
		CommandContainer(String raw, String beheaded, String commandName, String[] args) {
			this.raw = raw;
			this.beheaded = beheaded;
			this.commandName = commandName;
			this.args = args;
		}
	}
}
