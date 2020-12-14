package DiscordGameBot;

import java.util.Arrays;

public class Logger {
	public static final void print(Object obj) {
		if (obj == null) {
			obj = "null";
		}
		if (obj instanceof Iterable) {
			@SuppressWarnings("rawtypes")
			Iterable iter = (Iterable) obj;
			System.out.print("{");
			for (Object value : iter) {
				System.out.print(String.valueOf(value.toString()) + ",");
			}
			System.out.println("}");
		} else if (obj.getClass().isArray()) {
			System.out.println(Arrays.deepToString(new Object[] { obj }));
		} else {
			System.out.println(String.valueOf(obj.toString()));
		}
	}
}
