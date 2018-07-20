package com.genesislab.seleniumbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnector 
{
	static Connection con = null;
	static Statement stmt = null;
	
	public static Connection dbConnector()
	{
		try
		{
			String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(Driver);
			String url = "jdbc:sqlserver://45.35.15.92; databaseName=fb_test; user=java;password=Java@64;";
			con = DriverManager.getConnection(url);
			return con;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
