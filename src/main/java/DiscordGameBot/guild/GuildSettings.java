package DiscordGameBot.guild;

import DiscordGameBot.sql.Database;
import DiscordGameBot.sql.Result;
import DiscordGameBot.sql.SQL;

public class GuildSettings {
	
	public static final String TABLE = "guild_settings";
	
	/*** Default Values ***/
	private static final String PREFIX = "!";
	/*** --- ***/
	
	final String id;
	
	private String prefix;
	
	public GuildSettings(String id) {
		this.id = id;
		load();
	}
	
	private void load() {
		Database db = SQL.from(TABLE);
		Result result;
		
		if (db == null || (result = db.select(TABLE, "id", id)) == null) {
			insertDefaultValues();
			return;
		}
		
		this.prefix = result.get("prefix");
	}
	
	private void insertDefaultValues() {
		this.prefix = PREFIX;
	}
	
	public int prefixLength() {
		return prefix.length();
	}
	
	public String prefix() {
		return prefix;
	}
}	
