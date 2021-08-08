package erp.application.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import erp.application.login.model.LevelType;
import java.sql.ResultSet;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JDBCUpdate {

	private static final String[] STATIC_SETTINGS = { "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/login",
			"root", "Password123" };
	private static volatile JDBCUpdate JDBC_UPDATE;

	private JDBCUpdate() {
		if (JDBC_UPDATE != null) {
			throw new RuntimeException("Runtime exception thrown, for preventing reflection");
		}
	}

	public static synchronized JDBCUpdate singletonJDBC() {
		if (JDBC_UPDATE == null) {
			JDBC_UPDATE = new JDBCUpdate();
		}
		return JDBC_UPDATE;
	}

	protected JDBCUpdate protectFromSerialization() {
		return singletonJDBC();
	}

	public static void updateRole(int index) {
		final int rVal = index - 1;
		LevelType[] level = LevelType.values();
		final String sqlUpdate = "INSERT INTO role(role) VALUES ('" + level[rVal].name() + "')";
		Connection conn = null;
		Statement st = null;
		try {
			Class.forName(STATIC_SETTINGS[0]);
			conn = DriverManager.getConnection(STATIC_SETTINGS[1], STATIC_SETTINGS[2], STATIC_SETTINGS[3]);
			st = conn.createStatement();
			st.executeUpdate(sqlUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null || st != null)
					conn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		writingPrimaryKeys();
	}

	private static void writingPrimaryKeys() {
		Connection connUser = null;
		Statement stUser = null;
		ResultSet rsUser = null;
		final String selectSqlUser = "SELECT user_id FROM user";
		int counter = 0;
		String primaryKeyUser = null;
		String primaryKeyRole = null;
		try {
			Class.forName(STATIC_SETTINGS[0]);
			connUser = DriverManager.getConnection(STATIC_SETTINGS[1], STATIC_SETTINGS[2], STATIC_SETTINGS[3]);
			stUser = connUser.createStatement();
			rsUser = stUser.executeQuery(selectSqlUser);
			while (rsUser.next()) {
				counter++;
				String[] userId = new String[counter];
				String primaryKeys = rsUser.getString(1);
				userId[counter - 1] = primaryKeys;
				primaryKeyUser = userId[counter - 1];
			}
			System.out.println("User: " + primaryKeyUser);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connUser.close();
				stUser.close();
				rsUser.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Connection connRole = null;
		Statement stRole = null;
		ResultSet rsRole = null;
		final String selectSqlRole = "SELECT role_id FROM role";
		try {
			Class.forName(STATIC_SETTINGS[0]);
			connRole = DriverManager.getConnection(STATIC_SETTINGS[1], STATIC_SETTINGS[2], STATIC_SETTINGS[3]);
			stRole = connRole.createStatement();
			rsRole = stRole.executeQuery(selectSqlRole);
			while (rsRole.next()) {
				counter++;
				String[] roleId = new String[counter];
				String primaryKey = rsRole.getString(1);
				roleId[counter - 1] = primaryKey;
				primaryKeyRole = roleId[counter - 1];
			}
			System.out.println("Role " + primaryKeyRole);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connRole.close();
				stRole.close();
				rsRole.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		WriteReadJDBCFile.writePrimaryKeys(primaryKeyUser, primaryKeyRole);
		insertIntoUserRole(WriteReadJDBCFile.readPrimaryKeys());
	}

	private static void insertIntoUserRole(String[] listOfKeys) {
		System.out.println(listOfKeys[0] + " " + listOfKeys[1] + " " + listOfKeys[listOfKeys.length - 2] + " "
				+ listOfKeys[listOfKeys.length - 1]);
		final String sqlInsert = "INSERT INTO user_role (user_id, role_id) VALUES ('"
				+ listOfKeys[listOfKeys.length - 2] + "', '" + listOfKeys[listOfKeys.length - 1] + "')";
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(STATIC_SETTINGS[0]);
			conn = DriverManager.getConnection(STATIC_SETTINGS[1], STATIC_SETTINGS[2], STATIC_SETTINGS[3]);
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlInsert);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
