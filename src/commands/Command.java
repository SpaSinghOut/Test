package commands;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import main.GeneralBot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/**
 * The command superclass that all command subclasses inherit from. When creating a new command subclass it must 
 * <ul>
 * <li><b>extend</b> this class
 * <li> implement the {@link #execute(String[])} method
 * <li> have a constructor that calls <b>super()</b>
 * 
 * <h6>Use:</h6>once a command message has been received and parsed use an instance of the proper command subclass 
 * to
 * </ul>
 * @author spartak
 *
 */
public abstract class Command {
	private boolean hasName; 
	/**
	 * The minimum number of arguments that this command requires. Default value is 0. Automatically changed when {@link #setArgs(String...)} is called.
	 */
	private int minArgs;
	/**
	 * The maximum number of arguments that this command allows. Default value is 0. Automatically changed when {@link #setArgs(String...)} is called.
	 */
	private int maxArgs;
	/**
	 * The place of the current argument that is being processed in the list of all the arguments given with this command.
	 * Used for proper error reporting in case an exception is thrown while processing arguments.
	 */
	protected int readingArg;
	
	/**
	 * The message event that contains the message that triggered this command
	 */
	protected MessageReceivedEvent event;
	/**
	 * 
	 */
	private String helpMessage;
	protected JDA jda = GeneralBot.jda;
	private String args, aliases;
	private String commandName;
	private ArrayList<String> aliasList = new ArrayList<String>();
	/**
	 * The only constructor in the class. It is required that this constructor is called from the constructors of subclasses.
	 * Otherwise various help message and error report bugs will occur.
	 */
	protected Command() {
		setHelpMessage();
		args = "";
		aliases = "";
		commandName = "name not set";
		minArgs = 0;
		maxArgs = 0;
		readingArg = 1;
	}
	/**
	 * Takes in a {@link MessageReceivedEvent} and sets it as this command's event for the execution of the following commands. 
	 * Needs to be called prior to calling {@link #execute(String[])}
	 * @param event - the event of the message that triggered this command
	 */
	public void setEvent(MessageReceivedEvent event) {
		this.event = event;
	}
	/**
	 * Makes the bot send the automatically generated help message in the text channel that the command was received in.
	 */
	public void help() {
		say(helpMessage);
	}
	/**
	 * Executes this command using the arguments provided by args and the last event given my {@link #setEvent(MessageReceivedEvent)}.
	 * @param args - the arguments that follwed this command.
	 * @return always returns <b> true</b>
	 * @throws Exception any error that may have thrown an exception while attempting to execute this command.
	 */
	public abstract boolean execute(String[] args) throws Exception;
	/** Takes in a String value and sets the as
	 * the name of this command as well as adds it to the list of this command's aliases. Can only be called one time. Consecutive calls will be ignored.
	 * @param commandName - the name of the command
	 * @return <b>true</b> if this is the first time that this method has been called allowing this method to execute correctly
	 * <br><b>false</b> if the command name had already been set and this method was not executed.
	 */
	protected boolean setCommandName(String commandName) {
		boolean returnValue = !hasName;
		hasName = true;
		this.commandName = commandName;
		this.addAlias(commandName);
		return returnValue;
	}
	/**
	 * Outputs the given message in the text channel of the event set by {@link #setEvent(MessageReceivedEvent)}
	 * @param message - the message that is to be written in the text channel
	 */
	protected void say(String message) {
		event.getTextChannel().sendMessage(message).complete();
	}
	/**
	 * Outputs the given message in the text channel of the event set by {@link #setEvent(MessageReceivedEvent)}.
	 * <br>Works the same way as {@link #say(String)} but with a delay in seconds set by secondsDelay.
	 * @param secondsDelay - the delay in seconds after which the message is to be written.
	 * @param message - the message that is to be written in the text channel.
	 */
	protected void say(int secondsDelay, String message) {
		event.getTextChannel().sendMessage(message).completeAfter(secondsDelay, TimeUnit.SECONDS);
	}
	/**
	 * Return all the aliases of this command in the form of a single String value.
	 * @return all the aliases of this command	 */
	public String getAliases() {
		return aliases;
	}
	/**
	 * Return the primary name of this command as it was set when calling {@link #setCommandName(String)}.
	 * @return the name of this command.
	 */
	public String getCommandName() {
		return commandName;
	}
	/**
	 * Returns all of the argument requirements of this command as they were set when calling {@link #setArgs(String...)}.
	 * @return - all the argument requirements of this command
	 */
	public String getArgs() {
		return args;
	}
	/**
	 * Takes in a list of String values that represent the argument requirements of this command.<h6>
	 * What it does:</h6>
	 * <ul>
	 * <li>Changes the <b>minimum</b> number of arguments for this command by the number of <b>required</b> arguments
	 * <li>Changes the <b>maximum</b> number of arguments for this command by the <b>total</b> number of arguments
	 * <li>Updates help message to show what both the required and the optional arguments are
	 * </ul>
	 * To designate that an argument is optional put "(opt)" at the beggining of the argument name. <br>
	 * <br>For example: setArgs("requiredNum", "(opt)optionalString");
	 * <br><br>Does not need to be called if there are no argument requirements. 
	 * 
	 * @param args - all the argument requirements of this command
	 */
	protected void setArgs(String... args) {
		this.args = "";
		for(String s : args) {
			this.args = this.args.concat("[" + s + "]");
			maxArgs++;
			if(!s.startsWith("(opt)"))
				minArgs++;
		}
		setHelpMessage();
	}
	/**
	 * Adds an alias to the list of aliases that can trigger this command. Updates the help message to show the aliases.
	 * @param aliases - a list of aliases that identify this command.
	 */
	protected void addAlias(String... aliases) {
		for(String alias : aliases) {
			this.aliases = this.aliases.concat("'" + alias + "' ");
			aliasList.add(alias);
		}
		setHelpMessage();
	}
	/**
	 * Return all of the aliases of this command in the form of a String array.
	 * @return a String array that contains all the aliases of this command
	 */
	public String[] getAliasList(){
		String[] list = new String[aliasList.size()];
		aliasList.toArray(list);
		return list;		
	}
	/**
	 * Update the help message to reflect all the automatically gathered information about this command.
	 */
	private void setHelpMessage() {
		helpMessage = String.format("Command name: %s\nParameters: %s\nAliases: %s\n", getCommandName(), getArgs(), getAliases());
	}
	/**
	 * Takes in the number of arguments that were given with this command and makes sure that the number is within
	 * the minimum and maximum limit for the number of arguments that this command can take in.
	 * @param argsGiven - the number of args that were given when this command was triggered.
	 * @return <b>true</b> if the number of args is withing the limits<br><b>false</b> if the number is not within the limits
	 */
	public boolean compareArgs(int argsGiven) {
		if(argsGiven < minArgs) {
			say("Not enough args given");
			help();
			return false;
		}
		if(argsGiven > maxArgs) {
			say("Too many args given");
			help();
			return false;
		}
		return true;
	}
	/**
	 * Makes the bot show the Not a Number error message in the text chat.
	 */
	public void nanError() {
		say("Argument number: " + readingArg + " is supposed to be a number but was not entered as such");
		help();
		return;
	}
}
