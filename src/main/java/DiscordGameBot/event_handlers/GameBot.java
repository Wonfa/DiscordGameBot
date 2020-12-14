package DiscordGameBot.event_handlers;

import DiscordGameBot.Bot;
import DiscordGameBot.Logger;
import DiscordGameBot.commands.CommandHandler;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class GameBot implements EventHandler {
	
	Bot bot;
	CommandHandler commandHandler;
	
	public GameBot(Bot parent) {
		this.bot = parent;
		this.commandHandler = new CommandHandler();
		commandHandler.register("game", command -> Logger.print(command.args));
		commandHandler.register("dc", command -> bot.disconnect());
	}
	
	@Override
	public void onMessageReceived(GuildMessageReceivedEvent event) {
		commandHandler.handle(event);
	}
}
