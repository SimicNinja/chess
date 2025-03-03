package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class AuthDAO
{
	private Map<String, AuthData> authMap = new HashMap<>();

	public String createAuth(String username)
	{
		String authToken = UUID.randomUUID().toString();
		authMap.put(authToken, new AuthData(authToken, username));
		return authToken;
	}
}