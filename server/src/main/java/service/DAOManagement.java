package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;

public class DAOManagement
{
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
}
