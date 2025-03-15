package passoff.server;

import dataaccess.*;
import dataaccess.MySQLDAOs.UserDAO_MySQL;
import dataaccess.interfaces.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAOTests
{
	private static Connection conn;
	private final UserDAO dao = new UserDAO_MySQL();

	@BeforeAll
	public static void createDatabase()
	{
		try
		{
			DatabaseManager.createDatabase();
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@BeforeEach
	public void setup() throws DataAccessException, SQLException
	{
		conn = DatabaseManager.getConnection();

		String[] auths = {"qwerty", "12345", "asdf;"};
		String[] usernames = {"LickyFrog", "SimicNinja", "JOA"};

		String insert = "INSERT INTO authData (authToken, username) VALUES (?, ?)";

		try(PreparedStatement statement = conn.prepareStatement(insert))
		{
			for(int i = 0; i < 3; i++)
			{
				statement.setString(1, auths[i]);
				statement.setString(2, usernames[i]);
				statement.executeUpdate();
			}
		}
	}

	@AfterAll
	public static void tearDown() throws SQLException
	{
		conn.close();
		conn = null;
	}
}
