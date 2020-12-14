package DiscordGameBot.guild;

import java.util.HashMap;
import java.util.Map;

public class GuildManager {
	
	private static final Map<String, GuildSettings> guildInfo;
	
	static {
		guildInfo = new HashMap<String, GuildSettings>();
	}
	
	public static GuildSettings get(String id) {
		GuildSettings settings = guildInfo.get(id);
		
		if (settings == null) {
			settings = new GuildSettings(id);
			guildInfo.put(id, settings);
		}
		
		return settings;
	}
}
