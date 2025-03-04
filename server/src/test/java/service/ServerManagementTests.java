package service;

import dataaccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServerManagementTests
{
	@Test
	@DisplayName("Successful Application Clear")
	public void clearTest()
	{
		UserManagement userService = new UserManagement();

		try
		{
			userService.register(new UserData("LickyFrog", "greenTreeFrog", "amazon@gmail.com"));
			userService.register(new UserData("Stan", "1324", "stan.lee@hotmail.com"));
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}

		userService.clearApplication();

		assertTrue(userService.users.isEmpty(), "Users isn't empty.");
		assertTrue(userService.authorizations.isEmpty(), "Authorizations isn't empty.");
	}
}
