package service;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.UserData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DAOManagementTests
{
//	@AfterAll
//	public static void tearDown() throws SQLException, DataAccessException
//	{
//		Connection conn = DatabaseManager.getConnection();
//
//		try(Statement statement = conn.createStatement())
//		{
//			statement.executeUpdate("TRUNCATE TABLE userData");
//			statement.executeUpdate("TRUNCATE TABLE gameData");
//			statement.executeUpdate("TRUNCATE TABLE authData");
//		}
//
//		conn.close();
//	}

	@Test
	@DisplayName("Successful Application Clear")
	public void clearTest()
	{
		DAOManagement daoManager = new DAOManagement();
		UserManagement userManager = new UserManagement(daoManager);

		try
		{
			userManager.register(new UserData("LickyFrog", "greenTreeFrog", "amazon@gmail.com"));
			userManager.register(new UserData("Stan", "1324", "stan.lee@hotmail.com"));
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}

		daoManager.clearApplication();

		assertTrue(daoManager.getUsers().isEmpty(), "Users isn't empty.");
		assertTrue(daoManager.getAuthorizations().isEmpty(), "Authorizations isn't empty.");
	}
}
