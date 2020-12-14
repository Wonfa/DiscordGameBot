package DiscordGameBot.sql;

import java.util.HashMap;

import DiscordGameBot.FileUtils;

public class SQL {
	private static HashMap<String, Database> databases;

	public static void init() {
		databases = new HashMap<String, Database>();
		for (String name : FileUtils.fileNames("sql/")) {
			if (!name.contains(".db")) {
				continue;
			}
			register(name.replace(".db", ""));
		}
	}

	public static void register(String... names) {
		for (String name : names) {
			databases.put(name, new Database(name));
		}
	}

	public static Database from(String name) {
		if (databases.get(name) == null) {
			register(name);
		}
		return databases.get(name);
	}
}
