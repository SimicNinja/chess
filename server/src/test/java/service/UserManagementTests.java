package service;

import dataaccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagementTests
{
	@Test
	@DisplayName("Successful User Registration")
	public void lickyFrog()
	{
		UserData frog = new UserData("LickyFrog", "greenTreeFrog", "amazon@gmail.com");
		UserManagement userService = new UserManagement();

		UserManagement.RegisterResult result = null;
		try
		{
			result = userService.register(frog);
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}

		assertNotNull(result, "Result should not be null.");
		assertEquals("LickyFrog", result.username(), "Username should match the registered user.");
		assertNotNull(result.authToken(), "AuthToken should not be null.");
	}

	@Test
	@DisplayName("Insufficient User Registration Information")
	public void missingPassword()
	{
		UserData noPasswordStan = new UserData("Stan", "", "stan.lee@hotmail.com");
		UserManagement userService = new UserManagement();

		Exception exception = assertThrows(DataAccessException.class, () ->	userService.register(noPasswordStan));

		assertTrue(exception.getMessage().contains("You must provide a username, password, & email."));
	}

	@Test
	@DisplayName("Duplicate User")
	public void secondFrog()
	{
		UserData frog = new UserData("LickyFrog", "greenTreeFrog", "amazon@gmail.com");
		UserData secondFrog = new UserData("LickyFrog", "brownTreeFrog", "florida@gmail.com");
		UserManagement userService = new UserManagement();

		try
		{
			userService.register(frog);
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
		Exception exception = assertThrows(DataAccessException.class, () ->	userService.register(secondFrog));

		assertTrue(exception.getMessage().contains("already exists"));
	}
}
