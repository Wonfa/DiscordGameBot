package DiscordGameBot.event_handlers;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface EventHandler {
	default void onMessageReceived(GuildMessageReceivedEvent event) {}
}
