package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import model.GameData;
import server.Server.JoinGameRequest;
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

	public void joinGame(JoinGameRequest request) throws DataAccessException
	{
		String username = authorizations.authorizeToken(request.authToken());

		GameData game = games.getGame(request.gameID());

		games.joinGame(game.gameID(), teamJoin(game, request.color()), username);
	}

	private ChessGame.TeamColor teamJoin(GameData game, ChessGame.TeamColor color) throws DataAccessException
	{
		if(color == ChessGame.TeamColor.BLACK && game.blackUsername().isEmpty())
		{
			return ChessGame.TeamColor.BLACK;
		}
		else if(color == ChessGame.TeamColor.WHITE && game.whiteUsername().isEmpty())
		{
			return ChessGame.TeamColor.WHITE;
		}
		throw new DataAccessException("Another user has already claimed the " + color + " team in this game.");
	}

	public record NewGameResult(int gameID) {}
}
