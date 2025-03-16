package passoff.server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.mysqldaos.GameDAO_MySQL;
import dataaccess.interfaces.GameDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GameDAOTests
{
	private static Connection conn;
	private final GameDAO dao = new GameDAO_MySQL();

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
	public void testNewGameFail() throws DataAccessException
	{
		Assertions.assertThrows(DataAccessException.class, () -> dao.newGame(null));
	}


}
