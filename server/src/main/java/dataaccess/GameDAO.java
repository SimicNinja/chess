package dataaccess;

import model.GameData;
import java.util.HashMap;
import java.util.Map;

public class GameDAO
{
	private Map<Integer, GameData> gameMap = new HashMap<>();

	public void clear()
	{
		gameMap.clear();
	}

	public boolean isEmpty()
	{
		return gameMap.isEmpty();
	}
}
