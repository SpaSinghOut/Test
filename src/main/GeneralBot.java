package main;

import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.login.LoginException;

import commands.*;
import lavamusic.MusicEventListener;
import main.Parser.CommandContainer;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.*;

public class GeneralBot {
	public static JDA jda;
	static HashMap<String, Command> commands = new HashMap<String, Command>();
	static BotListener listener = new BotListener();
	static MusicEventListener musicListener = new MusicEventListener();
	public static void main(String[] args) {
		initializeBotExistence();
		listCommands();
	}
	private static void initializeBotExistence() {
		String token = "MzUzNjYzMzgyMzE2MTIyMTI2.DIy-NA.sEl2JqPmDwu3jjTGZS7JxXh0Nfo";
		try {
			JDABuilder builder = new JDABuilder(AccountType.BOT).addEventListener(listener).addEventListener(musicListener).setToken(token);
			builder.setAutoReconnect(true);
			builder.setStatus(OnlineStatus.IDLE);
			jda = builder.buildBlocking();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RateLimitedException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		//*/
	}
	private static void listCommands() {
		ArrayList<Command> commands = new ArrayList<Command>();
		
		commands.add(new PingPongCommand());
		commands.add(new PromotionCommand());
		commands.add(new PruneCommand());
		commands.add(new CoinflipCommand());
		commands.add(new RollCommand());
		commands.add(new SpamCommand());
		
		for(Command command: commands)
			for(String alias : command.getAliasList())
				GeneralBot.commands.put(alias, command);
	}
	static void handleCommand(CommandContainer commandText, MessageReceivedEvent event) {
		String commandName = commandText.commandName;
		System.out.println("The Command name is : " + commandName);
		boolean isValid = commands.containsKey(commandName);
		if(isValid) {
			System.out.printf("%s is %sa valid command\n", commandName, isValid?"":"not ");
			Command command = commands.get(commandName);
			if(!command.compareArgs(commandText.args != null ? commandText.args.length : 0))
				return;
			command.setEvent(event);
			try {
				command.execute(commandText.args);
			}catch(Exception e) {
				command.nanError();
			}
		}
		else {
			event.getTextChannel().sendMessage("Command does not exist");
		}
	}
}
