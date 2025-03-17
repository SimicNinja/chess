package dataaccess.mysqldaos;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOMySQL
{
	public void clear(String tableName)
	{
		String sql = "TRUNCATE TABLE " + tableName;

		try(Connection conn = DatabaseManager.getConnection())
		{
			try(var statement = conn.prepareStatement(sql))
			{
				statement.executeUpdate();
			}
		}
		catch(SQLException | DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	public boolean isEmpty(String tableName)
	{
		String sql = "SELECT 1 FROM ? LIMIT 1";

		try(Connection conn = DatabaseManager.getConnection())
		{
			try(var statement = conn.prepareStatement(sql))
			{
				statement.setString(1, tableName);
				ResultSet set = statement.executeQuery();
				return !set.next();
			}
		}
		catch(SQLException | DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
}
