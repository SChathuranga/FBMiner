package com.genesislab.seleniumbase;

import java.sql.Connection;

public class FetchAtOnce implements Runnable 
{
	static Connection connection = null;
	private Thread t;
	private String facebookID, facebookUsername, facebookPassword;
	DBOperations dbops = new DBOperations();
		
	public FetchAtOnce(String facebookID,String facebookUsername,String facebookPassword)
	{
		this.facebookID = facebookID;
		this.facebookUsername = facebookUsername;
		this.facebookPassword = facebookPassword;
		System.out.println("Creating thread for " + this.facebookID);
		try
		{
			//connecting to database
			connection = DBConnector.dbConnector();
			if(connection.isValid(0))
				System.out.println("Database Connection Established");
			else
				System.out.println("Database Connection Failed");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void run()
	{
		CoreBase fetch = new CoreBase();
		try 
		{
			User facebookUser = fetch.Facebook_Login(facebookID, facebookUsername, facebookPassword);
			if(dbops.insertUserData(connection, facebookUser))
			{
				System.out.println("\n"+facebookID + ": Fetching and Saving Data Successfull");
				if(dbops.deleteCompleted(connection, facebookID))
				{
					System.out.println("\nDeleted FB ID: " + facebookID + " successfully!");
				}
				else
					System.out.println("\nDeleting FB ID: " + facebookID + " failed!");
			}
			else
			{
				System.out.println("\n"+facebookID + ": Fetching and Saving Data Failed");
			}
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void start() throws InterruptedException
	{
		if(t==null)
		{
			t = new Thread(this, facebookID);
			t.start();
		}		
	}
	
}
