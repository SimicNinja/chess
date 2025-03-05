package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.UserData;

import dataaccess.DataAccessException;
import server.Server.LoginRequest;

public class UserManagement
{
	private DAOManagement daoManager;
	private UserDAO users;
	private AuthDAO authorizations;

	public UserManagement(DAOManagement daoManager)
	{
		this.daoManager = daoManager;
		this.users = daoManager.getUsers();
		this.authorizations = daoManager.getAuthorizations();
	}

	public LoginResult register(UserData registerRequest) throws DataAccessException
	{
		String username = registerRequest.username();
		if(users.getUser(username) == null)
		{
			users.createUser(username, registerRequest.password(), registerRequest.email());
			return login(username);
		}
		throw new DataAccessException("User " + username + "already exists.");
	}
	public LoginResult login(LoginRequest loginRequest) throws DataAccessException
	{
		String username = loginRequest.username();
		String password = loginRequest.password();

		if(users.getUser(username) == null)
		{
			throw new DataAccessException("User " + username + " does not exist.");
		}
		else if(!users.getUser(username).password().equals(password))
		{
			throw new DataAccessException("You must provide the correct password for " + username);
		}
		else
		{
			return login(username);
		}
	}

	private LoginResult login(String username)
	{
		return new LoginResult(username, authorizations.createAuth(username));
	}

	public void logout(String authToken) throws DataAccessException
	{
		authorizations.deleteAuthData(authToken);
	}

	public void clearApplication()
	{
		users.clear();
		authorizations.clear();
	}

	public record LoginResult(String username, String authToken){}
}
