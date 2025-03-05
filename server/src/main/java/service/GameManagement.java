package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import server.Server.NewGameRequest;

public class GameManagement
{
	private final AuthDAO authorizations;
	private final GameDAO games;

	public GameManagement(DAOManagement daoManager)
	{
		this.authorizations = daoManager.getAuthorizations();
		this.games = daoManager.getGames();
	}

	public NewGameResult makeGame(NewGameRequest request) throws DataAccessException
	{
		authorizations.authorizeToken(request.authToken());

		String gameName = request.gameName();
		if(!games.duplicateGame(gameName))
		{
			return new NewGameResult(games.newGame(gameName));
		}
		throw new DataAccessException("Game " + gameName + "already exists.");
	}

	public record NewGameResult(int gameID) {}
}
