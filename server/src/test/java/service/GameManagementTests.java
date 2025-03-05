package service;

import dataaccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameManagementTests
{
	private static DAOManagement daoManager;
	private static UserManagement userManager;
	private static GameManagement gameManager;
	String authToken;

	@BeforeAll
	public static void init()
	{
		daoManager = new DAOManagement();
		userManager = new UserManagement(daoManager);
		gameManager = new GameManagement(daoManager);
	}

	@BeforeEach
	public void setup()
	{
		try
		{
			daoManager.clearApplication();

			UserManagement.LoginResult result = userManager.register(new UserData("LickyFrog", "greenTreeFrog", "amazon@gmail.com"));
			authToken = result.authToken();

			gameManager.makeGame(new Server.NewGameRequest(authToken, "TestGame"));
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("Make a Game")
	public void makeGameTest()
	{
		daoManager.clearApplication();

		try
		{
			daoManager.clearApplication();

			UserManagement.LoginResult result = userManager.register(new UserData("LickyFrog", "greenTreeFrog", "amazon@gmail.com"));
			String authToken = result.authToken();

			GameManagement.NewGameResult actual = gameManager.makeGame(new Server.NewGameRequest(authToken, "TestGame"));

			assertNotNull(actual, "Result should not be null.");
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("Missing Game Name")
	public void missingName()
	{
		Exception e = assertThrows(DataAccessException.class, () ->	gameManager.makeGame(new Server.NewGameRequest(authToken, "")));

		assertTrue(e.getMessage().contains("You must provide a game name"));
	}

	@Test
	@DisplayName("Duplicate Game")
	public void duplicateTest()
	{
		Exception exception = assertThrows(DataAccessException.class, () ->	gameManager.makeGame(new Server.NewGameRequest(authToken, "TestGame")));

		assertTrue(exception.getMessage().contains("already exists"));
	}
}