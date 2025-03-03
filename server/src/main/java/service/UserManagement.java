package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.UserData;

public class UserManagement
{
	public UserDAO users = new UserDAO();
	public AuthDAO authorizations = new AuthDAO();

	public RegisterResult register(UserData registerRequest)
	{
		String username = registerRequest.username();
		if(users.getUser(username) == null)
		{

			users.createUser(username, registerRequest.password(), registerRequest.email());
			String authToken = authorizations.createAuth(username);
			return new RegisterResult(username, authToken);
		}
		//Error Handling
		return null;
	}
//	public LoginResult login(LoginRequest loginRequest) {}
//	public void logout(LogoutRequest logoutRequest) {}

	public record RegisterResult(String username, String authToken){}
}
