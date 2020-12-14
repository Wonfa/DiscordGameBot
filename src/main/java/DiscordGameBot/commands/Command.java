package DiscordGameBot.commands;

import java.util.Arrays;

import DiscordGameBot.guild.GuildManager;
import DiscordGameBot.guild.GuildSettings;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Command {
	
	private static final String STRING_SPLIT = "∰";
	
	public final GuildMessageReceivedEvent event;
	public final GuildSettings settings;
	public final String prefix, command, content, contentRaw;
	public final String[] args;
	
	public Command(GuildMessageReceivedEvent event) {
		this.event = event;
		this.content = event.getMessage().getContentDisplay();
		this.settings = GuildManager.get(event.getGuild().getId());
		this.contentRaw = event.getMessage().getContentRaw().substring(settings.prefixLength());
		this.prefix = content.substring(0, settings.prefixLength());
		
		if (content.indexOf("\n") > -1) {
			this.args = contentRaw.replaceAll(" ", STRING_SPLIT).split("\n");
		} else {
			this.args = contentRaw.split(" ");
		}
		
		for (int index = 0; index < args.length; index++) {
			args[index] = args[index].replaceAll(STRING_SPLIT, " ");
		}
		
		this.command = args[0];
	}

	@Override
	public String toString() {
		return "Command [event=" + event + ", settings=" + settings + ", prefix=" + prefix + ", command=" + command + ", content=" + content
				+ ", contentRaw=" + contentRaw + ", args=" + Arrays.toString(args) + "]";
	}
	
	
}
