package commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

public class PruneCommand extends Command {
	public PruneCommand() {
		super();
		setCommandName("prune");
		setArgs("numMessages");
	}
	@Override
	public boolean execute(String[] args){
		TextChannel channel = event.getTextChannel();
		int x = Integer.parseInt(args[0]);
		
		if(x > 100) {
			say("Argument exceeds the maximum of 98. Only deleting the past 98 messages.");
			x = 98;
		}
		else if(x < 2) {
			say("Argument is lower than the minimum of 2 messages. No messages will be deleted.");
			return false;
		}

		say("Deleting " + x + " messages in 5 seconds");
		MessageHistory history = channel.getHistory();
		history.retrievePast(x + 2).complete();
		System.out.println("retrived history size: " + history.getRetrievedHistory().size());
		
		List<Message> list = history.getRetrievedHistory();
		channel.deleteMessages(list.subList(1, list.size())).completeAfter(5, TimeUnit.SECONDS);

		channel.deleteMessageById(list.get(0).getId()).completeAfter(1, TimeUnit.SECONDS);
		return true;
	}
}
