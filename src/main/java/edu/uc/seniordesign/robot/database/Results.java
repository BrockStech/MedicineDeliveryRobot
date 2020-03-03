package edu.uc.seniordesign.robot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Results
{
	ResultSet resultSet = null;
    Connection connection = null;
    PreparedStatement statement = null;
    
	public ArrayList<String> fetchResults()
    {
        ArrayList<String> results = new ArrayList<String>();
		String sqlStatement = "SELECT scheduled_time FROM schedule";
        try
        {
        	initalizeConnection(sqlStatement);
            
            while ((resultSet.next()))
            {
            	results.add(resultSet.getString("scheduled_time"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (connection != null)
            {
                try 
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }
	
	private void initalizeConnection(String query) throws SQLException
	{
		connection = JDBCMySQLConnection.getConnection();
	    statement = connection.prepareStatement(query);
	    resultSet = statement.executeQuery();
	}
	
	public void insertResults(ArrayList<String> results)
    {  
        String sqlStatement = "TRUNCATE TABLE schedule;";
        try
        {
        	connection = JDBCMySQLConnection.getConnection();
        	PreparedStatement truncateStatement = connection.prepareStatement(sqlStatement);
        	truncateStatement.executeUpdate();
        	truncateStatement.close();
        	executeInsertQuery(results, connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (connection != null)
            {
                try 
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return;
    }
	
	private void executeInsertQuery(ArrayList<String> results, Connection connection) throws SQLException
	{
		String sqlStatement = "INSERT INTO schedule (id, scheduled_time) VALUES (?,?)";
		int i = 1;
		for (String result : results)
		{
			PreparedStatement insertStatement = connection.prepareStatement(sqlStatement);
			insertStatement.setInt(1, i);
			insertStatement.setString(2, result);
        	insertStatement.executeUpdate();
        	insertStatement.close();
        	i++;
		}
	}
	
	
}    