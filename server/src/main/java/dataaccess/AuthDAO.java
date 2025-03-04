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

	public void authorizeToken(String authToken) throws DataAccessException
	{
		if(!authMap.containsKey(authToken))
		{
			throw new DataAccessException("There is no authorization token " + authToken + " stored in cache.");
		}
	}

	public void deleteAuthData(String authToken) throws DataAccessException
	{
		authorizeToken(authToken);
		authMap.remove(authToken);
	}

	public void clear()
	{
		authMap.clear();
	}

	public boolean isEmpty()
	{
		return authMap.isEmpty();
	}
}