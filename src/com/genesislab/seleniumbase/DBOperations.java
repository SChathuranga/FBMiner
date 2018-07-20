package com.genesislab.seleniumbase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBOperations 
{
	//code here
	public String getFBIDFromMainTable(Connection con)
	{
		try
		{
			Statement stmt = con.createStatement();
			ResultSet rs;
			
			String query = "select top 1 fbid from fbids";
			rs = stmt.executeQuery(query);
			while(rs.next())
			{
				String fbID = rs.getString(1);
				System.out.println("FB ID: ");
				return fbID;
			}
		}
		catch(Exception ex)
		{
			System.out.println("Getting FB ID Failed");
			return "null";
		}
		return "null";
	}
	public int validateNumberOfRecords(Connection con)
	{
		try
		{
			Statement stmt = con.createStatement();
			ResultSet rs;
			
			String query = "select fbid from fbids";
			rs = stmt.executeQuery(query);
			int count = 0;
			while(rs.next())
				count++;
			stmt.close();
			System.out.println("There are " + count + " FB IDs");
			return count;
		}
		catch(Exception ex)
		{
			System.out.println("Counting Failed");
			return 0;
		}
	}
	
	public boolean insertIDsToMainTable(Connection con, String fbid)
	{
		try
		{
			Statement insstmt = con.createStatement();			
			String query = "insert into fbids(fbid) values(" + fbid + ")";
			return insstmt.executeUpdate(query)>0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean insertUserData(Connection con, User user)
	{
		//write code to insert into userdata table
		try
		{
			String sqlQuery = "insert into userdata(fbid, name, workplace, education, currentCity, homeTown, contactInfo, gender, relationships, family, about, otherNames, favoriteQuotes, lifeEvents, basicInfo, dob, friendlist) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sqlQuery);		
			pstmt.setString(1, user.getFbid());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getWorkplaces());
			pstmt.setString(4, user.getEducation());
			pstmt.setString(5, user.getCurrentCity());
			pstmt.setString(6, user.getHomeTown());
			pstmt.setString(7, user.getContactInfo());
			pstmt.setString(8, user.getGender());
			pstmt.setString(9, user.getRelationships());
			pstmt.setString(10, user.getFamily());
			pstmt.setString(11, user.getAbout());
			pstmt.setString(12, user.getOtherNames());
			pstmt.setString(13, user.getFavoriteQuotes());
			pstmt.setString(14, user.getLifeEvents());
			pstmt.setString(15, user.getBasicInfo());
			pstmt.setString(16, user.getDateOfBirth());
			pstmt.setString(17, user.getFriends());
			
			
			return pstmt.executeUpdate()>0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean checkAvailability(Connection con, String fbid)
	{
		try
		{
			String query = "select * from userdata where fbid =?";
			String query1 = "select * from fbids where fbid =?";
			
			PreparedStatement stmt = con.prepareStatement(query);
			PreparedStatement stmt1 = con.prepareStatement(query1);
			stmt.setString(1, fbid);
			stmt1.setString(1, fbid);
			
			ResultSet rs, rs1;
			rs = stmt.executeQuery();
			rs1 = stmt1.executeQuery();
			
			if(rs.next() || rs1.next())
			{
				stmt.close();
				stmt1.close();
				return true;
			}
			else
			{
				stmt.close();
				stmt1.close();
				return false;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Checking availability failed!");
			return false;
		}
	}
	
	public boolean checkAvailabilityOnUserData(Connection con, String fbid)
	{
		try
		{
			String query = "select * from userdata where fbid =?";
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, fbid);
			
			ResultSet rs;
			rs = stmt.executeQuery();
			
			if(rs.next())
			{
				stmt.close();
				return true;
			}
			else
			{
				stmt.close();
				return false;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Checking availability failed!");
			return false;
		}
	}
	
	
	public boolean deleteCompleted(Connection con, String fbid) 
	{
		try
		{
			String query2 = "delete from fbids where fbid =?";
			PreparedStatement delps = con.prepareStatement(query2);
			delps.setString(1, fbid);
			return (delps.executeUpdate()>0);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Deleting failed!");
			return false;
		}
	}

	public boolean importCSVData(Connection con, String fbid) throws IOException, SQLException
	{
		String query = "insert into fbids(fbid, datataken) values(?,?)";
		PreparedStatement ps = con.prepareStatement(query);
    	ps.setString(1, fbid);
    	ps.setString(2, "0");
    	return ps.executeUpdate()>0;
	}
	
	public boolean exportCSVData(Connection con, ArrayList<String> fbids) throws IOException, SQLException
	{
		String query = "insert into fbids(fbid) values";
		for(int i=1; i<fbids.size();i++)
		{
			query = query + "(?), ";
		}
		query = query + "(?)";
		
		PreparedStatement ps = con.prepareStatement(query);
		
		for(int j=0; j<fbids.size();j++)
		{
			ps.setString(j+1, fbids.get(j));
		}
		
    	return ps.executeUpdate()>0;
	}
	
	public ArrayList<String> getFBIDListfromDB(Connection con)
	{
		ArrayList<String> DBList = new ArrayList<>();
		
		String squery = "select fbid from fbids";
		
		try 
		{
			Statement sstmt = con.createStatement();
			ResultSet srs;
			
			srs = sstmt.executeQuery(squery);
			while(srs.next())
			{
				DBList.add(srs.getString(1));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return DBList;
	}
	
	public String getAccountUserName(Connection con)
	{
		try
		{
			Statement stmt = con.createStatement();
			ResultSet rs;
			
			String query = "select top 1 username from accountdetails";
			rs = stmt.executeQuery(query);
			while(rs.next())
			{
				String username = rs.getString(1);
				System.out.println("FB ID: "+username);
				return username;
			}
		}
		catch(Exception ex)
		{
			System.out.println("Getting Account Username Failed");
			return "null";
		}
		return "null";
	}
	
	public String getAccountPassword(Connection con)
	{
		try
		{
			Statement stmt = con.createStatement();
			ResultSet rs;
			
			String query = "select top 1 password from accountdetails";
			rs = stmt.executeQuery(query);
			while(rs.next())
			{
				String password = rs.getString(1);
				System.out.println("FB Password: "+password);
				return password;
			}
		}
		catch(Exception ex)
		{
			System.out.println("Getting Account Password Failed");
			return "null";
		}
		return "null";
	}
	
	public boolean updateAccountDetails(Connection con, String currentUsername, String username, String password)
	{
		try
		{
			String sqlQuery = "update accountdetails set username=?, password=? where username = ?";
			PreparedStatement updatestmt = con.prepareStatement(sqlQuery);		
			updatestmt.setString(1, username);
			updatestmt.setString(2, password);
			updatestmt.setString(3, currentUsername);
						
			return updatestmt.executeUpdate()>0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public ResultSet fetchIDs(Connection con, String amount) throws SQLException
	{
		ResultSet IDs;
		
		String query = "select top " + amount + " fbid from fbids";
		Statement fetching = con.createStatement();
		//fetching.setInt(1, amount);
		IDs = fetching.executeQuery(query);
		return IDs;
	}
	
	public ResultSet fetchIDstest(Connection con) throws SQLException
	{
		ResultSet IDs;
		
		String query = "select top 2 fbid from fbids";
		Statement ff = con.createStatement();
		IDs = ff.executeQuery(query);
		return IDs;
	}
	
	public boolean checkIDAvailability(Connection con, String id) throws SQLException
	{
		ResultSet rs;
		
		String query = "select fbid from fbids where fbid = ?";
		PreparedStatement ff = con.prepareStatement(query);
		ff.setString(1, id);
		rs = ff.executeQuery();
		if(rs.next())
			return true;
		else
			return false;
	}
}
