package client;

import serverfacade.ResponseException;
import serverfacade.ServerFacade;

import java.util.Arrays;

public class Client
{
	private String username = null;
	private final ServerFacade server;
	private final String serverUrl;
	private boolean signedIn = false;

	public Client(String serverUrl)
	{
		server = new ServerFacade(serverUrl);
		this.serverUrl = serverUrl;
	}

	public String eval(String input)
	{
		try
		{
			var tokens = input.toLowerCase().split(" ");
			var cmd = (tokens.length > 0) ? tokens[0] : "help";
			var params = Arrays.copyOfRange(tokens, 1, tokens.length);
			return switch(cmd)
			{
				case "register" -> register(params);
				case "login" -> login(params);
				case "quit" -> "quit";
				default -> help();
			};
		}
		catch(ResponseException ex)
		{
			return ex.getMessage();
		}
	}

	public String register(String... params) throws ResponseException
	{
		if(params.length != 3)
		{
			username = params[0];
			String authToken = server.register(username, params[1], params[2]).authToken();
			signedIn = true;
			return String.format("You have made a new account!" +
					"\nUsername: " + username +
					"\nPassword: " + params[1] +
					"\nEmail: " + params[2]);
		}
		throw new ResponseException(400, "Expected: <username> <password> <email>");
	}

	public String login(String... params) throws ResponseException
	{
		if(params.length >= 1)
		{
			signedIn = true;
			username = String.join("-", params);
			return String.format("You signed in as %s.", username);
		}
		throw new ResponseException(400, "Expected: <username>");
	}

//	public String rescuePet(String... params) throws ResponseException
	{
//		assertSignedIn();
//		if(params.length >= 2)
//		{
//			var name = params[0];
//			var type = PetType.valueOf(params[1].toUpperCase());
//			var pet = new Pet(0, name, type);
//			pet = server.addPet(pet);
//			return String.format("You rescued %s. Assigned ID: %d", pet.name(), pet.id());
//		}
//		throw new ResponseException(400, "Expected: <name> <CAT|DOG|FROG>");
	}

//	public String listGames() throws ResponseException
	{
//		assertSignedIn();
//		var pets = server.listPets();
//		var result = new StringBuilder();
//		var gson = new Gson();
//		for(var pet : pets)
//		{
//			result.append(gson.toJson(pet)).append('\n');
//		}
//		return result.toString();
	}

//	public String adoptPet(String... params) throws ResponseException
	{
//		assertSignedIn();
//		if(params.length == 1)
//		{
//			try
//			{
//				var id = Integer.parseInt(params[0]);
//				var pet = getPet(id);
//				if(pet != null)
//				{
//					server.deletePet(id);
//					return String.format("%s says %s", pet.name(), pet.sound());
//				}
//			}
//			catch(NumberFormatException ignored)
//			{
//			}
//		}
//		throw new ResponseException(400, "Expected: <pet id>");
	}

//	public String adoptAllPets() throws ResponseException
	{
//		assertSignedIn();
//		var buffer = new StringBuilder();
//		for(var pet : server.listPets())
//		{
//			buffer.append(String.format("%s says %s%n", pet.name(), pet.sound()));
//		}
//
//		server.deleteAllPets();
//		return buffer.toString();
	}

	public String logout() throws ResponseException
	{
		assertSignedIn();
		signedIn = false;
		return String.format("%s left the shop", username);
	}

//	private Pet getPet(int id) throws ResponseException
	{
//		for(var pet : server.listPets())
//		{
//			if(pet.id() == id)
//			{
//				return pet;
//			}
//		}
//		return null;
	}

	public String help()
	{
		if(!signedIn)
		{
			return """
				- login <username> <password>
				- register <username> <password> <email>
				- quit
				- help
				""";
		}
		return """
			- create <name> - Creates a new game with the given name.
			- list - Lists all games.
			- join <ID> [White or Black] - Adds you to the specified team color and game.
			- observe <ID> - Allows you to spectate the specified game.
			- logout
			- quit
			- help
			""";
	}

	private void assertSignedIn() throws ResponseException
	{
		if(!signedIn)
		{
			throw new ResponseException(400, "You must sign in");
		}
	}
}
