package service;

import model.UserData;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import dataaccess.DataAccessException;
import server.Server.LoginRequest;

public class UserManagement
{
	private final AuthDAO authorizations;
	private final UserDAO users;

	public UserManagement(DAOManagement daoManager)
	{
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
	public LoginResult login(LoginRequest request) throws DataAccessException
	{
		String username = request.username();
		String password = request.password();

		if(users.getUser(username) == null)
		{
			throw new DataAccessException("User " + username + " does not exist.");
		}
		else if(!users.getUser(username).password().equals(password))
		{
			throw new DataAccessException("Incorrect password for " + username);
		}
		else
		{
			return login(username);
		}
	}

	private LoginResult login(String username) throws DataAccessException
	{
		return new LoginResult(username, authorizations.createAuth(username));
	}

	public void logout(String authToken) throws DataAccessException
	{
		authorizations.deleteAuthData(authToken);
	}

	public record LoginResult(String username, String authToken){}
}
