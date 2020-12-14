package DiscordGameBot.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DiscordGameBot.objects.delegate2;

public abstract class LowDatabase {
	private final String fileName, url;
	protected Connection connection;

	public LowDatabase(String fileName) {
		super();
		this.fileName = fileName;
		this.url = "jdbc:sqlite:data/sql/" + fileName + (fileName.endsWith(".db") ? "" : ".db");
		this.connection = connect();
	}

	protected Connection connect() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url);
			if (connection == null) {
				System.out.println("Failed creating database: " + fileName);
			} else {
				System.out.println("Loaded database: " + fileName);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	protected List<Result> fetch(String query, Object... values) {
		List<Result> list = new ArrayList<Result>();
		statement(query, (statement, set) -> {
			try {
				ResultSetMetaData meta = set.getMetaData();
				final int columnLength = meta.getColumnCount();
				while (set.next()) {
					Result result = new Result();
					for (int index = 1; index <= columnLength; index++) {
						result.put(meta.getColumnName(index), set.getObject(index));
					}
					list.add(result);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}, values);
		return list;
	}

	protected void batchInsert(String query, Result... results) {
		try {
			connection.setAutoCommit(false);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			int resultIteration = 1;
			for (Result result : results) {
				int iteration = 1;
				for (Object value : result.values()) {
					if (value instanceof Integer) {
						statement.setInt(iteration, (int) value);
					} else if (value instanceof String) {
						statement.setString(iteration, (String) value);
					} else if (value instanceof Boolean) {
						statement.setBoolean(iteration, (boolean) value);
					} else if (value instanceof Long) {
						statement.setLong(iteration, (long) value);
					} else if (value instanceof Float) {
						statement.setFloat(iteration, (float) value);
					} else if (value instanceof Double) {
						statement.setDouble(iteration, (double) value);
					} else if (value instanceof Byte) {
						statement.setByte(iteration, (byte) value);
					} else {
						throw new SQLException("Type not supported. Sorry :/");
					}
					iteration++;
				}
				statement.addBatch();
				if ((resultIteration++ % 1000) == 0) {
					System.out.println("insert Batch: " + resultIteration);
					statement.executeBatch();
					statement.clearBatch();
				}
			}
			statement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	protected void statement(String query, Object... values) {
		statement(query, null, values);
	}

	protected void statement(String query, delegate2<PreparedStatement, ResultSet> event, Object... values) {
		int marks = query.length() - query.replace("?", "").length();
		int size = values.length;
		if (marks != size) {
			System.out.println("Question marks out of sync with values!");
			System.out.println("Marks: " + marks);
			System.out.println("Size: " + size);
			System.out.println("Query: " + query);
		}
		System.out.println("statement: " + query);
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			int iteration = 1;
			for (Object value : values) {
				if (value instanceof Integer) {
					statement.setInt(iteration, (int) value);
				} else if (value instanceof String) {
					statement.setString(iteration, (String) value);
				} else if (value instanceof Boolean) {
					statement.setBoolean(iteration, (boolean) value);
				} else if (value instanceof Long) {
					statement.setLong(iteration, (long) value);
				} else if (value instanceof Float) {
					statement.setFloat(iteration, (float) value);
				} else if (value instanceof Double) {
					statement.setDouble(iteration, (double) value);
				} else if (value instanceof Byte) {
					statement.setByte(iteration, (byte) value);
				} else {
					throw new SQLException("Type not supported. Sorry :/");
				}
				iteration++;
			}
			try {
				boolean set = statement.execute();
				if (set) {
					event.execute(statement, statement.getResultSet());
				}
			} catch (Throwable t) {
				System.out.println(t.getMessage());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/*
	 * private boolean nullCheck() { boolean nullCheck = connection == null; if
	 * (nullCheck && (nullCheck = this.connect() == null)) {
	 * System.out.println("Failed to connect to database: " + fileName); } return
	 * nullCheck; }
	 */
}
