package serverfacade;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ServerFacade
{

	private final String serverUrl;

	public ServerFacade(String url)
	{
		serverUrl = url;
	}

	public void clear() throws ResponseException
	{
		this.makeRequest("DELETE", "/db", null, null, null);
	}

	public AuthData register(String username, String password, String email) throws ResponseException
	{
		UserData newUser = new UserData(username, password, email);
		return this.makeRequest("POST", "/user", newUser, null, AuthData.class);
	}

	public AuthData login(String username, String password) throws ResponseException
	{
		LoginRequest loginInfo = new LoginRequest(username, password);
		return this.makeRequest("POST", "/session", loginInfo, null, AuthData.class);
	}

	public void logout(String authToken) throws ResponseException
	{
		this.makeRequest("DELETE", "/session", null, makeAuth(authToken), null);
	}

	private <T> T makeRequest(String method, String path, Object request,
							  Map<String, String> headers, Class<T> responseClass)
			throws ResponseException
	{
		try
		{
			URL url = (new URI(serverUrl + path)).toURL();
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod(method);
			http.setDoOutput(true);

			if(headers != null)
			{
				for(Map.Entry<String, String> header : headers.entrySet())
				{
					http.setRequestProperty(header.getKey(), header.getValue());
				}
			}


			writeBody(request, http);
			http.connect();
			throwIfNotSuccessful(http);
			return readBody(http, responseClass);
		}
		catch(ResponseException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			throw new ResponseException(500, ex.getMessage());
		}
	}


	private static void writeBody(Object request, HttpURLConnection http) throws IOException
	{
		if(request != null)
		{
			http.addRequestProperty("Content-Type", "application/json");
			String reqData = new Gson().toJson(request);
			try(OutputStream reqBody = http.getOutputStream())
			{
				reqBody.write(reqData.getBytes());
			}
		}
	}

	private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException
	{
		var status = http.getResponseCode();
		String message = "Unknown Error; Please Try Again";
		if(!isSuccessful(status))
		{
			message = switch(status)
			{
				case 400 -> "Error: Bad Request";
				case 401 -> "Error: Unauthorized";
				case 403 -> "Error: Already Taken";
				default -> message;
			};
			throw new ResponseException(status, message);
		}
	}

	private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException
	{
		T response = null;
		if(http.getContentLength() < 0)
		{
			try(InputStream respBody = http.getInputStream())
			{
				InputStreamReader reader = new InputStreamReader(respBody);
				if(responseClass != null)
				{
					response = new Gson().fromJson(reader, responseClass);
				}
			}
		}
		return response;
	}

	private Map<String, String> makeAuth(String authToken)
	{
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", authToken);
		return header;
	}

	private boolean isSuccessful(int status)
	{
		return status / 100 == 2;
	}

	private record LoginRequest(String username, String password) {}
}