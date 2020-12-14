package DiscordGameBot.sql;

import java.util.HashMap;

public class SQL {
	private static HashMap<String, Database> databases;

	public static void init() {
		databases = new HashMap<String, Database>();
	}

	public static void register(String... names) {
		for (String name : names) {
			databases.put(name, new Database(name));
		}
	}

	public static Database from(String name) {
		return databases.get(name);
	}
}
