package DiscordGameBot;

import DiscordGameBot.event_handlers.GameBot;

public class Base {
	public static void main(String[] args) throws Exception {
		new Base();
	}
	
	final Bot bot;
	
	public Base() {
		this.bot = new Bot("test", new GameBot());
	}
}
