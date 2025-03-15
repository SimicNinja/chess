package dataaccess;

import java.util.UUID;
import java.sql.*;

public class AuthDAO_MySQL implements AuthDAO
{
	@Override
	public String createAuth(String username) throws DataAccessException
	{
		String authToken = UUID.randomUUID().toString();

		try(Connection conn = DatabaseManager.getConnection())
		{
			try(var preparedStatement = conn.prepareStatement("INSERT INTO authData (authToken, username) VALUES(?, ?)"))
			{
				preparedStatement.setString(1, authToken);
				preparedStatement.setString(2, username);

				preparedStatement.executeUpdate();

				return authToken;
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public String authorizeToken(String authToken) throws DataAccessException
	{
		String sql = "SELECT username FROM authData WHERE authToken = ?";

		try(Connection conn = DatabaseManager.getConnection())
		{
			try(var statement = conn.prepareStatement(sql))
			{
				statement.setString(1, authToken);

				try(var rs = statement.executeQuery())
				{
					if(rs.next())
					{
						return rs.getString("username");
					}
					else
					{
						throw new DataAccessException("There is no authorization token " + authToken +
								" stored in the database; please login.");
					}
				}
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteAuthData(String authToken) throws DataAccessException
	{
		String sql = "DELETE FROM authData WHERE authToken = ?";

		try(Connection conn = DatabaseManager.getConnection())
		{
			try(var statement = conn.prepareStatement(sql))
			{
				statement.setString(1, authToken);

				int rowsAffected = statement.executeUpdate();

				if (rowsAffected == 0)
				{
					throw new DataAccessException("There is no authorization token "
							+ authToken + " stored in cache; please login.");
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
		String sql = "TRUNCATE TABLE authData";

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

	public boolean isEmpty()
	{
		String sql = "SELECT 1 FROM authData LIMIT 1";

		try(Connection conn = DatabaseManager.getConnection())
		{
			try(var statement = conn.prepareStatement(sql))
			{
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
