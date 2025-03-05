package service;

import model.GameData;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;

public class GameManagment
{
	private final AuthDAO authorizations;
	private final GameDAO games;

	public GameManagment(DAOManagement daoManager)
	{
		this.authorizations = daoManager.getAuthorizations();
		this.games = daoManager.getGames();
	}

//	public NewGameResult makeGame(NewGameRequest request)
//	{
//
//	}
}
