package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class DAOManagement
{
	private final GameDAO games = new GameDAO();
	private final UserDAO users = new UserDAO();
	private final AuthDAO authorizations = new AuthDAO();

	protected UserDAO getUsers()
	{
		return users;
	}

	protected AuthDAO getAuthorizations()
	{
		return authorizations;
	}

	protected GameDAO getGames()
	{
		return games;
	}

	public void clearApplication()
	{
		games.clear();
		users.clear();
		authorizations.clear();
	}
}
