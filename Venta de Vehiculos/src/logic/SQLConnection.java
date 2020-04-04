package logic;

import java.sql.*;



public class SQLConnection {

	public static Connection connect() {

		Connection dbSQL;

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String dbURL = "jdbc:sqlserver://10.0.0.6;databaseName=VentaVehiculos;user=project;password=123";

			dbSQL = DriverManager.getConnection(dbURL);

			//System.out.println("Connection Succeeded");

			return dbSQL;

		} catch (Exception e) {
			System.out.println("Failed Connection");
			return null;
		} //finally 
//		{
//			try 
//			{
//				if (dbSQL != null && !dbSQL.isClosed()) 
//				{
//					dbSQL.close();
//				}
//			} catch (SQLException ex) 
//			{
//				ex.printStackTrace();
//			}
//		}
	}

}