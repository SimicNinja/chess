package dataaccess.MySQLDAOs;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.interfaces.UserDAO;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAO_MySQL implements UserDAO
{
	@Override
	public UserData getUser(String username)
	{
		return null;
	}

	@Override
	public void createUser(String username, String password, String email) throws DataAccessException
	{
		String sql = "INSERT INTO userData (username, password, email) VALUES(?, ?, ?)";

		try(Connection conn = DatabaseManager.getConnection())
		{
			try(var preparedStatement = conn.prepareStatement(sql))
			{
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, email);

				preparedStatement.executeUpdate();
			}
			catch(SQLException e)
			{
				if(e.getMessage().contains("null"))
				{
					throw new DataAccessException("You must provide a username, password, & email.");
				}
				else
				{
					throw new SQLException(e);
				}
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void clear()
	{

	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}
}
