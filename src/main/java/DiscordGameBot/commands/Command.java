package DiscordGameBot.commands;

import DiscordGameBot.guild.GuildManager;
import DiscordGameBot.guild.GuildSettings;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Command {
	
	public final MessageReceivedEvent event;
	public final GuildSettings settings;
	public final String prefix, command, content, contentRaw;
	public final String[] args;
	
	public Command(MessageReceivedEvent event) {
		this.event = event;
		this.content = event.getMessage().getContentDisplay();
		this.contentRaw = event.getMessage().getContentRaw();
		this.settings = GuildManager.get(event.getGuild().getId());
		this.prefix = content.substring(0, settings.prefixLength());
		
		if (content.indexOf("\n") > -1) {
			this.args = contentRaw.replaceAll(" ", "∰").split("\n");
		} else {
			this.args = contentRaw.split(" ");
		}
		
		this.command = args[0];
	}
}
