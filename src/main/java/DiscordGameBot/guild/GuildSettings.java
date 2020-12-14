package DiscordGameBot.guild;

public class GuildSettings {
	
	final String id;
	
	private String prefix;
	
	public GuildSettings(String id) {
		this.id = id;
	}
	
	private void load() {
		
	}
	
	public int prefixLength() {
		return prefix.length();
	}
	
	public String prefix() {
		return prefix;
	}
}	
