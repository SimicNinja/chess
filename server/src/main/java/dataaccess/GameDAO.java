package dataaccess;

import chess.ChessGame;
import model.GameData;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameDAO
{
	private Map<Integer, GameData> idMap = new HashMap<>();

	public GameData getGame(int gameID)
	{
		return idMap.get(gameID);
	}

	public boolean duplicateGame(String gameName)
	{
		final boolean[] flag = {false};

		idMap.forEach((gameID, GameData) ->
		{
			if(GameData.gameName().equals(gameName))
			{
				flag[0] = true;
			}
		});

		return flag[0];
	}

	public int newGame(String gameName) throws DataAccessException
	{
		int gameID = UUID.randomUUID().hashCode();

		if(gameName == null || gameName.isEmpty())
		{
			throw new DataAccessException("You must provide a game name.");
		}

		idMap.put(gameID, new GameData(gameID, "", "", gameName, new ChessGame()));
		return gameID;
	}

	public void clear()
	{
		idMap.clear();
	}

	public boolean isEmpty()
	{
		return idMap.isEmpty();
	}
}
