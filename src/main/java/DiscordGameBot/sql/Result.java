package DiscordGameBot.sql;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class Result {
	private LinkedHashMap<String, Object> values;

	public Result() {
		this.values = new LinkedHashMap<String, Object>();
	}

	void put(String key, Object value) {
		values.put(key, value);
	}

	public Object[] values() {
		return values.values().toArray(new Object[0]);
	}

	/**
	 * Use {@link #get(String)} if you can
	 * 
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Entry<String, Object> get(int index) {
		return (Entry<String, Object>) values.entrySet().toArray()[index];
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) values.get(key);
	}

	/**
	 * Use {@link #set(String, Object)} if you can
	 * 
	 * @param index
	 * @param value
	 */
	public void set(int index, Object value) {
		Entry<String, Object> entry = get(index);
		entry.setValue(value);
	}

	public void set(String key, Object value) {
		values.replace(key, value);
	}

	public void defaults(List<String> columns) {
		for (String column : columns) {
			put(column, "-6969");
		}
	}

	/**
	 * Use {@link #defaults(List)} if you are iterating
	 * 
	 * @param database
	 * @param tableName
	 */
	public void defaults(Database database, String tableName) {
		if (database == null || tableName == null) {
			return;
		}
		List<String> columns = database.getColumnNames(tableName);
		for (String column : columns) {
			put(column, "-6969");
		}
	}

	public String[] toStrings() {
		return values.values().toArray(new String[0]);
	}

	@Override
	public String toString() {
		return get(0).getValue().toString();
	}
}
