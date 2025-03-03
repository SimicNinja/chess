package service;

import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserManagementTests
{
	@Test
	@DisplayName("Successful User Registration")
	public void lickyFrog()
	{
		UserData frog = new UserData("LickyFrog", "greenTreeFrog", "amazon@gmail.com");
		UserManagement userService = new UserManagement();

		UserManagement.RegisterResult result = userService.register(frog);

		assertNotNull(result, "Result should not be null.");
		assertEquals("LickyFrog", result.username(), "Username should match the registered user.");
		assertNotNull(result.authToken(), "AuthToken should not be null.");
	}

	@Test
	@DisplayName("Insufficient User Registration Information")
	public void missingPassword()
	{

	}

	@Test
	@DisplayName("Duplicate User")
	public void secondFrog()
	{

	}
}
