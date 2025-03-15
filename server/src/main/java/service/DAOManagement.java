package service;

import dataaccess.*;

public class DAOManagement
{
	private final GameDAO_Memory games = new GameDAO_Memory();
	private final UserDAO users = new UserDAO_Memory();
	private final AuthDAO authorizations = new AuthDAO_MySQL();

	public DAOManagement()
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
