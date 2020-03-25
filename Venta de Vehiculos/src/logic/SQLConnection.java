package logic;

import java.sql.*;

import javax.swing.JOptionPane;

public class SQLConnection {

	public static Connection dbConnection() {

		Connection dbSQL = null;

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=VentaVehiculos;user=gab;password=123";

			dbSQL = DriverManager.getConnection(dbURL);

			System.out.println("Connection Succeeded");

			return dbSQL;

		} catch (Exception e) {
			System.out.println("Failed Connection");
			return null;
		}
	}
}

