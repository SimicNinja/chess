package client;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import serverfacade.ResponseException;
import serverfacade.ServerFacade;

import java.net.HttpURLConnection;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests
{
	private static Server server;
	private static ServerFacade facade;
	private static UserData newUser;
	private static UserData existingUser;
	private String existingAuth;

	@BeforeAll
	public static void init()
	{
		server = new Server();
		var port = server.run(8080);
		System.out.println("Started test HTTP server on " + port);
		facade = new ServerFacade("http://localhost:" + port);

		existingUser = new UserData("ExistingUser", "existingUserPassword", "eu@mail.com");
		newUser = new UserData("NewUser", "newUserPassword", "nu@mail.com");
	}

	@BeforeEach
	public void setup() throws ResponseException
	{
		facade.clear();
		AuthData registerResult =
				facade.register(existingUser.username(), existingUser.password(), existingUser.email());
		existingAuth = registerResult.authToken();
	}

	@AfterAll
	static void stopServer()
	{
		server.stop();
	}

	@Test
	public void successRegister() throws ResponseException
	{
		//submit register request
		AuthData registerResult = facade.register(newUser.username(), newUser.password(), newUser.email());

		Assertions.assertEquals(newUser.username(), registerResult.username(), "Response did not have the same username as was registered");
		Assertions.assertNotNull(registerResult.authToken(), "Response did not contain an authentication string");
	}

	@Test
	public void failedRegister() throws ResponseException
	{
		ResponseException e = Assertions.assertThrows(ResponseException.class, () ->
				facade.register(existingUser.username(), existingUser.password(), existingUser.email()));
	}
}
