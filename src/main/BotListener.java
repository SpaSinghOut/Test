package main;

import java.util.ArrayList;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class BotListener extends ListenerAdapter {
	/**
	 * The list of all the possible command triggers
	 */
	ArrayList<String> triggers = new ArrayList<String>();

	static JDA jda = GeneralBot.jda;
	
	public BotListener() {
		System.out.println("listener created");
		triggers.add("T.");
	}
	
	/**
	 * What happens when a User types in any message
	 * 
	 */
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		System.out.println("A message has been received");
		// The raw message
		String message = event.getMessage().getContent();
		System.out.println("the message is: " + message);
		// does the message start with a trigger
		boolean startsWithTrigger = true;
		// the index of the end element in the list of triggers
		int end = triggers.size();
		// Go through every trigger to see if the message starts with one of them
		while( !message.startsWith(triggers.get(--end)))
			// If we are out of triggers
			if(end == 0) {
				// If we get to the end then no trigger word matched and the message does not start with a trigger
				startsWithTrigger = false;
				break;
			}
		System.out.printf("The message does %sstart with a trigger\n", startsWithTrigger?"":"not ");
		if(startsWithTrigger) {
			GeneralBot.handleCommand(Parser.parse(message.toLowerCase(), triggers.get(end)), event);
			System.out.println(message.toLowerCase() + " has been executed");
		}
	}
	@Override
	public void onUserOnlineStatusUpdate(UserOnlineStatusUpdateEvent event) {
		
	}
	
	@Override
	public void onReady(ReadyEvent e) {
		System.out.println("Logged in");
	}
}
