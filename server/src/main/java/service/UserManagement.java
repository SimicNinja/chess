package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

public class UserManagement
{
	public UserDAO users = new UserDAO();
	public AuthDAO authorizations = new AuthDAO();

	public RegisterResult register(UserData registerRequest) throws DataAccessException
	{
		String username = registerRequest.username();
		if(users.getUser(username) == null)
		{
			users.createUser(username, registerRequest.password(), registerRequest.email());
			String authToken = authorizations.createAuth(username);
			return new RegisterResult(username, authToken);
		}
		throw new DataAccessException("User " + username + "already exists.");
	}
//	public LoginResult login(LoginRequest loginRequest) {}
//	public void logout(LogoutRequest logoutRequest) {}

	public record RegisterResult(String username, String authToken){}
}
