package service;

import chess.ChessGame;
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
	int gameID;

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

			GameManagement.NewGameResult result2 = gameManager.makeGame(new Server.NewGameRequest(authToken, "TestGame"));
			gameID = result2.gameID();
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
		Exception e = assertThrows(DataAccessException.class, () ->	gameManager.makeGame(new Server.NewGameRequest(authToken, "TestGame")));

		assertTrue(e.getMessage().contains("already exists"));
	}

	@Test
	@DisplayName("Invalid User Creates Game")
	public void unauthorizedToMakeGameTest()
	{
		Exception e = assertThrows(DataAccessException.class, () ->	gameManager.makeGame(new Server.NewGameRequest("FakeToken", "TestGame")));

		assertTrue(e.getMessage().contains("no authorization"));
	}

	@Test
	@DisplayName("LickyFrog Joins White")
	public void joinWhiteTest()
	{
		try
		{
			gameManager.joinGame(new Server.JoinGameRequest(authToken, ChessGame.TeamColor.WHITE, gameID));

			assertEquals("LickyFrog", daoManager.getGames().getGame(gameID).whiteUsername());
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("LickyFrog Joins Black")
	public void joinBlackTest()
	{
		try
		{
			gameManager.joinGame(new Server.JoinGameRequest(authToken, ChessGame.TeamColor.BLACK, gameID));

			assertEquals("LickyFrog", daoManager.getGames().getGame(gameID).blackUsername());
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("Invalid GameID to Join")
	public void badGameIDTest()
	{
		Exception e = assertThrows(DataAccessException.class, () ->
				gameManager.joinGame(new Server.JoinGameRequest(authToken, ChessGame.TeamColor.WHITE, -1)));

		assertTrue(e.getMessage().contains("A game with"));
	}

	@Test
	@DisplayName("Invalid User Joins Game")
	public void unauthorizedToJoinGameTest()
	{
		Exception e = assertThrows(DataAccessException.class, () ->
				gameManager.joinGame(new Server.JoinGameRequest("FakeToken", ChessGame.TeamColor.WHITE, gameID)));

		assertTrue(e.getMessage().contains("no authorization"));
	}

	@Test
	@DisplayName("Try to Join Occupied Team")
	public void occupiedTeamTest()
	{
		try
		{
			UserManagement.LoginResult result = userManager.register(new UserData("SecondFrog", "1234", "nu@gmail.com"));
			String secondAuthToken = result.authToken();

			gameManager.joinGame(new Server.JoinGameRequest(authToken, ChessGame.TeamColor.WHITE, gameID));

			Exception e = assertThrows(DataAccessException.class, () ->
					gameManager.joinGame(new Server.JoinGameRequest(secondAuthToken, ChessGame.TeamColor.WHITE, gameID)));

			assertTrue(e.getMessage().contains("Another user has already claimed"));
		}
		catch(DataAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

}