package DiscordGameBot;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

import DiscordGameBot.event_handlers.EventHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Bot {
	JDA api;
	EventHandler manager;
	
	public Bot(String token) {
		try {
			this.api = JDABuilder.createDefault(token).build();
		} catch (LoginException e) {
			e.printStackTrace();
			return;
		}
		
		api.addEventListener(new Listener());
		Logger.print("Successfully Launched Bot.");
	}
	
	public void disconnect() {
		this.api.shutdown();
		Logger.print("Disconnecting Bot...");
	}
	
	public void attach(EventHandler manager) {
		this.manager = manager;
	}
	
	private class Listener extends ListenerAdapter {
		@Override
		public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
			attempt(() -> manager.onMessageReceived(event));
		}

		@Override
		public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event) {
			
		}

		public void attempt(delegate event) {
			try {
				event.execute();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
	@FunctionalInterface
	private interface delegate {
		void execute();
	}
}
