package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserDAO
{
	private Map<String, UserData> userMap = new HashMap<>();

	public UserData getUser(String username)
	{
		return userMap.get(username);
	}

	public void createUser(String username, String password, String email)
	{
		userMap.put(username, new UserData(username, password, email));
	}
}
