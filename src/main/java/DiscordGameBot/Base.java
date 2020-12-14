package DiscordGameBot;


import java.nio.file.Files;
import java.nio.file.Paths;

import DiscordGameBot.event_handlers.GameBot;
import DiscordGameBot.sql.SQL;

public class Base {
	public static void main(String[] args) throws Exception {
		new Base();
	}
	
	final Bot bot;
	
	public Base() {
		final String token;
		try {
			SQL.init();
			token = Files.readString(Paths.get("data/token.txt"));
		} catch (Throwable t) {
			this.bot = null;
			t.printStackTrace();
			return;
		}
		this.bot = new Bot(token);
		bot.attach(new GameBot(bot));
	}
}
