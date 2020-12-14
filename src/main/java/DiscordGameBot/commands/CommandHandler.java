package DiscordGameBot.commands;

import java.util.HashMap;
import java.util.Map;

import DiscordGameBot.Logger;
import DiscordGameBot.objects.delegate1;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CommandHandler {
	final Map<String, delegate1<Command>> commands;
	
	public CommandHandler() {
		this.commands = new HashMap<String, delegate1<Command>>();
	}
	
	public void register(String command, delegate1<Command> event) {
		commands.put(command, event);
	}
	
	public void handle(GuildMessageReceivedEvent messageEvent) {
		Command command = new Command(messageEvent);
		delegate1<Command> event = commands.get(command.command);
		
		if (event == null) {
			Logger.print(command.toString());
			Logger.print("No event for command: " + command.command);
			return;
		}
		
		event.execute(command);
	}
}
