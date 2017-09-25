package commands;

import net.dv8tion.jda.core.entities.Role;

public class PromotionCommand extends Command{
	public PromotionCommand() {
		super();
		setCommandName("hey");
	}
	@Override
	public boolean execute(String[] args) {
		Role role = jda.getRoles().get(0);
		for(Role r: jda.getRoles())
			if(r.getName().equals("Specialist"))
				role = r;
		System.out.println("Found role: " + role);
		event.getGuild().getController().addRolesToMember(event.getMember(), role).complete();
		say("Congratulations, " + event.getMember().getAsMention() + ", you've been promoted to " + role.getAsMention());
		return true;
	}
}
