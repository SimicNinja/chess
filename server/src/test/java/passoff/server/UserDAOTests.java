package passoff.server;

import dataaccess.*;
import dataaccess.MySQLDAOs.UserDAO_MySQL;
import dataaccess.interfaces.UserDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

		String[] usernames = {"LickyFrog", "SimicNinja", "JOA"};
		String[] passwords = {"kitchenTime", "codingTime", "animeTime"};
		String[] emails = {"1@gmail.com", "2@gmail.com", "3@gmail.com"};

		String insert = "INSERT INTO userData (username, password, email) VALUES(?, ?, ?)";

		try(PreparedStatement statement = conn.prepareStatement(insert))
		{
			for(int i = 0; i < 3; i++)
			{
				statement.setString(1, usernames[i]);
				statement.setString(2, passwords[i]);
				statement.setString(3, emails[i]);
				statement.executeUpdate();
			}
		}
	}

	@AfterAll
	public static void tearDown() throws SQLException
	{
		try(PreparedStatement statement = conn.prepareStatement("TRUNCATE TABLE userData"))
		{
			statement.executeUpdate();
		}

		conn.close();
		conn = null;
	}

	@Test
	public void testCreateUser() throws DataAccessException, SQLException
	{
		dao.createUser("Asuna", "artTime", "4@gmail.com");

		List<String> usernames = AuthDAOTests.getItems("userData", "username", conn);

		Assertions.assertTrue(usernames.contains("Asuna"));
	}

	@Test
	public void testCreateUserFail() throws DataAccessException, SQLException
	{
		dao.createUser("Asuna", "artTime", "4@gmail.com");

		Assertions.assertThrows(DataAccessException.class, () ->
			dao.createUser(null, "motorcycleTime", "5@gmail.com"));

		Assertions.assertThrows(RuntimeException.class, () ->
				dao.createUser("Asuna", "motorcycleTime", "5@gmail.com"));
	}
}
