package DiscordGameBot.sql;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DiscordGameBot.objects.delegate2;

public class Database extends LowDatabase {
	public Database(String fileName) {
		super(fileName);
	}

	public <T extends Iterable<W>, W> void generate(String tableName, delegate2<Result, W> event, T values) {
		List<Result> results = new ArrayList<Result>();
		List<String> columns = getColumnNames(tableName);
		for (W value : values) {
			Result result = new Result();
			result.defaults(columns);
			event.execute(result, value);
			results.add(result);
		}
		insert(tableName, results.toArray(new Result[0]));
	}

	public void insert(String tableName, Result... results) {
		if (results.length <= 0) {
			System.out.println("No values given for insertion");
			return;
		}
		StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " VALUES(");
		boolean flag = false;
		for (@SuppressWarnings("unused")
		Object value : results[0].values()) {
			if (flag) {
				query.append(", ");
			}
			query.append("?");
			flag = true;
		}
		query.append(");");
		batchInsert(query.toString(), results);
	}

	public void insert(String tableName, Result result) {
		insert(tableName, result.values());
	}

	public void insert(String tableName, Object... values) {
		if (values.length <= 0) {
			System.out.println("No values given for insertion");
			return;
		}
		StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " VALUES(");
		boolean flag = false;
		for (@SuppressWarnings("unused")
		Object value : values) {
			if (flag) {
				query.append(", ");
			}
			query.append("?");
			flag = true;
		}
		query.append(");");
		statement(query.toString(), values);
	}

	public List<Result> collect(String tableName) {
		return fetch("SELECT * from " + tableName);
	}
	
	public List<Result> collectInOrder(String tableName, String columnName, boolean descending) {
		return fetch("SELECT * from " + tableName + " ORDER BY " + columnName + " " + (descending ? "DESC" : "ASC"));
	}
	
	public List<Result> collectInOrder(String tableName, String columnName) {
		return collectInOrder(tableName, columnName, false);
	}

	@SuppressWarnings("all")
	public List<Result> select(String tableName, String... values) {
		StringBuilder query = new StringBuilder("SELECT ");
		boolean flag = false;
		for (String value : values) {
			if (flag) {
				query.append(", ");
			}
			query.append("%s");
			flag = true;
		}
		query.append(" FROM " + tableName);
		return fetch(String.format(query.toString(), values));
	}

	public void createTable(String tableName, String query) {
		query = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n " + query + "\n);";
		statement(query);
	}

	public void deleteTable(String tableName) {
		String query = "DROP TABLE IF EXISTS " + tableName;
		statement(query);
	}

	public void deleteRow(String tableName, Object value) {
		String query = "DELETE FROM " + tableName + " WHERE " + getColumnNames(tableName).get(0) + " = ?";
		statement(query, value);
	}

	public List<String> getTableNames() {
		List<String> list = new ArrayList<String>();
		try (ResultSet set = connection.getMetaData().getTables(null, null, "%", null)) {
			while (set.next()) {
				list.add(set.getString(3));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public void renameColumn(String tableName, String oldName, String newName) throws SQLException {
		/*** collect old data before deleting ***/
		List<Result> oldValues = collect(tableName);
		List<String> names = getColumnNames(tableName);
		/*** delete old table ***/
		deleteTable(tableName);
		/*** create new one with replaced column name and insert old values back ***/
		StringBuilder query = new StringBuilder();
		Object[] aValues = oldValues.get(0).values();
		for (int index = 0; index < aValues.length; index++) {
			String name = names.get(index);
			if (name.equals(oldName)) {
				name = newName;
			}
			query.append(index != 0 ? " " : "").append(name);
			query.append(" STRING");
			query.append(index == 0 ? " PRIMARY KEY," : ",");
		}
		query = new StringBuilder(query.substring(0, query.length() - 1));
		createTable(tableName, query.toString());
		for (Result value : oldValues) {
			insert(tableName, value);
		}
	}

	public int columnCount(String tableName) {
		return getColumnNames(tableName).size();
	}

	public List<String> getColumnNames(String tableName) {
		List<String> list = new ArrayList<String>();
		DatabaseMetaData dmd = null;
		try {
			dmd = connection.getMetaData();
		} catch (SQLException e) {
			System.out.println("getColumnNames[1]: " + e.getMessage());
			return list;
		}
		try (ResultSet rs = dmd.getColumns(null, null, tableName, null)) {
			while (rs.next()) {
				list.add(rs.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("getColumnNames[2]: " + e.getMessage());
		}
		return list;
	}
}
