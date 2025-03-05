package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class AuthDAO
{
	private final Map<String, AuthData> authMap = new HashMap<>();

	public String createAuth(String username)
	{
		String authToken = UUID.randomUUID().toString();
		authMap.put(authToken, new AuthData(authToken, username));
		return authToken;
	}

	public String authorizeToken(String authToken) throws DataAccessException
	{
		if(!authMap.containsKey(authToken))
		{
			throw new DataAccessException("There is no authorization token " + authToken + " stored in cache; please login.");
		}
		return authMap.get(authToken).username();
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