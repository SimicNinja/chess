package service;

import dataaccess.UserDAO;
import model.UserData;

public class UserManagement
{
	private UserDAO users = new UserDAO();

	public RegisterResult register(UserData registerRequest)
	{
		if(users.getUser(registerRequest.username()) == null)
		{
			return null;
		}
		return null;
	}
//	public LoginResult login(LoginRequest loginRequest) {}
//	public void logout(LogoutRequest logoutRequest) {}

	record RegisterResult(String username, String authToken){}
}
