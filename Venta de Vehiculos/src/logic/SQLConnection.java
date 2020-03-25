package logic;

import java.sql.*;

import javax.swing.JOptionPane;

public class SQLConnection {

	public static Connection connect() {

		Connection dbSQL = null;

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String dbURL = "jdbc:sqlserver://10.0.0.15;databaseName=VentaVehiculos;user=gab;password=123";




			dbSQL = DriverManager.getConnection(dbURL);

			System.out.println("Connection Succeeded");

			return dbSQL;

		} catch (Exception e) {
			System.out.println("Failed Connection");
			return null;
		} finally 
		{
			try 
			{
				if (dbSQL != null && !dbSQL.isClosed()) 
				{
					dbSQL.close();
				}
			} catch (SQLException ex) 
			{
				ex.printStackTrace();
			}
		}
	}

}