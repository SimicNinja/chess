package dataaccess.interfaces;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.GameData;
import service.GameManagement;
import java.util.List;

public interface GameDAO
{
	public GameData getGame(int gameID) throws DataAccessException;
	public List<GameManagement.ListedGame> getGames();
	public boolean duplicateGame(String gameName);
	public int newGame(String gameName) throws DataAccessException;
	public void joinGame(int gameID, ChessGame.TeamColor color, String username) throws DataAccessException;
	public void clear();
	public boolean isEmpty();
}
