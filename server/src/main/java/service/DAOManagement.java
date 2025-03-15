package service;

import dataaccess.*;
import dataaccess.MySQLDAOs.AuthDAO_MySQL;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import dataaccess.memoryDAOs.GameDAO_Memory;
import dataaccess.memoryDAOs.UserDAO_Memory;

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
