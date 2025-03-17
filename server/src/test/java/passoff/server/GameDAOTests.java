package passoff.server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.mysqldaos.GameDAOMySQL;
import dataaccess.interfaces.GameDAO;
import model.GameData;
import org.junit.jupiter.api.*;
import service.GameManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDAOTests
{
	private static Connection conn;
	private final GameDAO dao = new GameDAOMySQL();

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

		int[] ids = {1, 2, 3};
		String[] white = {"LickyFrog", "SimicNinja", "JOA"};
		String[] black = {"SimicNinja", "JOA", "LickyFrog"};
		String[] names = {"Frog's first game", "Water fight", "Chest"};

		String insert = "INSERT INTO gameData () VALUES(?, ?, ?, ?, ?)";

		try(PreparedStatement statement = conn.prepareStatement(insert))
		{
			for(int i = 0; i < 3; i++)
			{
				ChessGame game = new ChessGame();
				Gson gson = new Gson();
				String jsonGame = gson.toJson(game);

				statement.setInt(1, ids[i]);
				statement.setString(2, white[i]);
				statement.setString(3, black[i]);
				statement.setString(4, names[i]);
				statement.setString(5, jsonGame);
				statement.executeUpdate();
			}
		}
	}

	@AfterEach
	public void tearDown() throws SQLException
	{
		try(PreparedStatement statement = conn.prepareStatement("TRUNCATE TABLE gameData"))
		{
			statement.executeUpdate();
		}

		conn.close();
		conn = null;
	}

	@Test
	public void testNewGame() throws DataAccessException, SQLException
	{
		dao.newGame("Lava Field");

		List<String> gameNames = AuthDAOTests.getItems("gameData", "gameName", conn);

		Assertions.assertTrue(gameNames.contains("Lava Field"));
	}

	@Test
	public void testNewGameFail()
	{
		Assertions.assertThrows(DataAccessException.class, () -> dao.newGame(null));
	}

	@Test
	public void testGetGame() throws DataAccessException
	{
		Assertions.assertEquals(new GameData(1, "LickyFrog",
				"SimicNinja", "Frog's first game", new ChessGame()), dao.getGame(1));
		Assertions.assertEquals(new GameData(2, "SimicNinja",
				"JOA", "Water fight", new ChessGame()), dao.getGame(2));
		Assertions.assertEquals(new GameData(3, "JOA",
				"LickyFrog", "Chest", new ChessGame()), dao.getGame(3));
	}

	@Test
	public void testGetGameFail()
	{
		Assertions.assertThrows(DataAccessException.class, () -> dao.getGame(4));
	}

	@Test
	public void testJoinGame() throws DataAccessException
	{
		int gameID = dao.newGame("EmptyGame");

		dao.joinGame(gameID, ChessGame.TeamColor.WHITE, "SimicNinja");

		Assertions.assertEquals(new GameData(gameID, "SimicNinja",
				null, "EmptyGame", new ChessGame()), dao.getGame(gameID));
	}

	@Test
	public void testJoinGameFail()
	{
		Assertions.assertThrows(DataAccessException.class, () ->
				dao.joinGame(4, ChessGame.TeamColor.WHITE, "SimicNinja"));
	}

	@Test
	public void testListGames()
	{
		List<GameManagement.ListedGame> expected = new ArrayList<>();
		expected.add(new GameManagement.ListedGame(
		1, "LickyFrog", "SimicNinja", "Frog's first game"));
		expected.add(new GameManagement.ListedGame(
		2, "SimicNinja", "JOA", "Water fight"));
		expected.add(new GameManagement.ListedGame(
		3, "JOA", "LickyFrog", "Chest"));

		Assertions.assertEquals(expected, dao.listGames());

		//Note: No fail test written as, similar to clear,
		// there are no parameter or conditions that will change based on method implementation.
	}

	@Test
	public void testClear()
	{
		dao.clear();
		Assertions.assertTrue(dao.listGames().isEmpty());
	}
}
