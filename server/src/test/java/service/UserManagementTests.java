package service;

import dataaccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagementTests
{
	private static UserManagement userService;

	@BeforeAll
	public static void init()
	{
		userService = new UserManagement();
	}

	@BeforeEach
	public void setup()
	{
		try
		{
			userService.clearApplication();
			userService.register(new UserData("LickyFrog", "greenTreeFrog", "amazon@gmail.com"));
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("Successful User Registration")
	public void lickyFrog()
	{
		userService.clearApplication();

		try
		{
			UserManagement.LoginResult result = userService.register(new UserData("LickyFrog", "greenTreeFrog", "amazon@gmail.com"));

			assertNotNull(result, "Result should not be null.");
			assertEquals("LickyFrog", result.username(), "Username should match the registered user.");
			assertNotNull(result.authToken(), "AuthToken should not be null.");
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("Insufficient User Registration Information")
	public void missingPassword()
	{
		UserData noPasswordStan = new UserData("Stan", "", "stan.lee@hotmail.com");

		Exception e= assertThrows(DataAccessException.class, () ->	userService.register(noPasswordStan));

		assertTrue(e.getMessage().contains("You must provide a username, password, & email."));
	}

	@Test
	@DisplayName("Duplicate User")
	public void secondFrog()
	{
		UserData secondFrog = new UserData("LickyFrog", "brownTreeFrog", "florida@gmail.com");

		Exception exception = assertThrows(DataAccessException.class, () ->	userService.register(secondFrog));

		assertTrue(exception.getMessage().contains("already exists"));
	}

	@Test
	@DisplayName("Successful Login")
	public void loginTest()
	{
		try
		{
			UserManagement.LoginResult result = userService.login(new Server.LoginRequest("LickyFrog", "greenTreeFrog"));

			assertNotNull(result);
			assertEquals("LickyFrog", result.username());
			assertNotNull(result.authToken());
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("Wrong Password")
	public void badPasswordTest()
	{
		Server.LoginRequest wrongPassword = new Server.LoginRequest("LickyFrog", "brownTreeFrog");

		Exception e = assertThrows(DataAccessException.class, () ->	userService.login(wrongPassword));
		assertTrue(e.getMessage().contains("You must provide the correct password for LickyFrog"));
	}

	@Test
	@DisplayName("Non-existent User")
	public void noUserTest()
	{
		Server.LoginRequest unregisteredUser = new Server.LoginRequest("JohnDoe", "brownTreeFrog");

		Exception e = assertThrows(DataAccessException.class, () ->	userService.login(unregisteredUser));
		assertTrue(e.getMessage().contains("User JohnDoe does not exist."));
	}

	@Test
	@DisplayName("Successful Logout")
	public void logoutTest()
	{
		userService.clearApplication();

		try
		{
			userService.users.createUser("Stan", "1324", "stan.lee@hotmail.com");
			String authToken = userService.authorizations.createAuth("Stan");

			userService.logout(authToken);

			assertTrue(userService.authorizations.isEmpty(), "Authorizations isn't empty.");
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("Double Logout")
	public void doubleLogoutTest()
	{
		userService.clearApplication();

		try
		{
			userService.users.createUser("Stan", "1324", "stan.lee@hotmail.com");
			String authToken = userService.authorizations.createAuth("Stan");

			userService.logout(authToken);

			Exception e = assertThrows(DataAccessException.class, () ->	userService.logout(authToken));
			assertTrue(e.getMessage().contains("no authorization token"));
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
}
