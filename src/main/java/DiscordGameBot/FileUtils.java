package DiscordGameBot;

import java.io.File;

public final class FileUtils {
	private FileUtils() {
	}

	public static final String DATA = "data/";

	public static String[] fileNames(String path) {
		File file = new File(DATA + path);
		if (!file.isDirectory()) {
			return new String[0];
		}
		return file.list();
	}
}
