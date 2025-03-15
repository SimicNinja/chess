package dataaccess.MySQLDAOs;

import dataaccess.DataAccessException;
import dataaccess.interfaces.UserDAO;
import model.UserData;

public class UserDAO_MySQL implements UserDAO
{
	@Override
	public UserData getUser(String username)
	{
		return null;
	}

	@Override
	public void createUser(String username, String password, String email) throws DataAccessException
	{

	}

	@Override
	public void clear()
	{

	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}
}
