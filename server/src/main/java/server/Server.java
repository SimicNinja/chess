package server;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.UserData;
import service.DAOManagement;
import service.GameManagement;
import service.UserManagement;
import spark.*;
import com.google.gson.Gson;

public class Server
{
	private final DAOManagement daoManager = new DAOManagement();
	private final UserManagement userManager = new UserManagement(daoManager);
	private final GameManagement gameManager = new GameManagement(daoManager);

	public int run(int desiredPort)
	{
		Spark.port(desiredPort);

		Spark.staticFiles.location("web");

		// Register your endpoints and handle exceptions here.
		Spark.delete("/db", this::clear);
		Spark.post("/user", this::addUser);
		Spark.post("/session", this::login);
		Spark.delete("/session", this::logout);
		Spark.post("/game", this::newGame);

		//This line initializes the server and can be removed once you have a functioning endpoint
		Spark.init();

		Spark.awaitInitialization();
		return Spark.port();
	}

	public void stop()
	{
		Spark.stop();
		Spark.awaitStop();
	}

	private Object clear(Request request, Response response)
	{
		daoManager.clearApplication();
		return http200(response);
	}

	private Object addUser(Request request, Response response)
	{
		UserData registerRequest = new Gson().fromJson(request.body(), UserData.class);

		try
		{
			UserManagement.LoginResult result = userManager.register(registerRequest);
			response.status(200);
			return new Gson().toJson(result);
		}
		catch(DataAccessException e)
		{
			if(e.getMessage().contains("must provide"))
			{
				response.status(400);
				return new Gson().toJson(new JSONResponse("Error: bad request"));
			}
			else if(e.getMessage().contains("already exists."))
			{
				response.status(403);
				return new Gson().toJson(new JSONResponse("Error: already taken"));
			}
			else
			{
				return http500(e, response);
			}
		}
	}

	private Object login(Request request, Response response)
	{
		LoginRequest loginRequest = new Gson().fromJson(request.body(), LoginRequest.class);

		try
		{
			UserManagement.LoginResult result = userManager.login(loginRequest);
			response.status(200);
			return new Gson().toJson(result);
		}
		catch(DataAccessException e)
		{
			if(e.getMessage().contains("correct password") || e.getMessage().contains("does not exist"))
			{
				response.status(401);
				return new Gson().toJson(new JSONResponse("Error: unauthorized"));
			}
			else
			{
				return http500(e, response);
			}
		}
	}

	private Object logout(Request request, Response response)
	{
		String authToken = request.headers("authorization");

		try
		{
			userManager.logout(authToken);
			return http200(response);
		}
		catch(DataAccessException e)
		{
			if(e.getMessage().contains("no authorization token"))
			{
				response.status(401);
				return new Gson().toJson(new JSONResponse("Error: unauthorized"));
			}
			else
			{
				return http500(e, response);
			}
		}
	}

	private Object newGame(Request request, Response response)
	{
		String gameName = request.body();
		String authToken = request.headers("authorization");
		NewGameRequest newGameRequest = new NewGameRequest(authToken, gameName);

		try
		{
			GameManagement.NewGameResult result = gameManager.makeGame(newGameRequest);
			response.status(200);
			return new Gson().toJson(result);
		}
		catch(DataAccessException e)
		{
			if(e.getMessage().contains("must provide"))
			{
				response.status(400);
				return new Gson().toJson(new JSONResponse("Error: bad request"));
			}
			else if(e.getMessage().contains("no authorization token"))
			{
				response.status(401);
				return new Gson().toJson(new JSONResponse("Error: unauthorized"));
			}
			else
			{
				return http500(e, response);
			}
		}
	}

	private Object http200(Response response)
	{
		response.status(200);
		return new Gson().toJson(new JSONResponse(""));
	}

	private Object http500(DataAccessException e, Response response)
	{
		response.status(500);
		return new Gson().toJson(new JSONResponse("Error: " + e.getMessage()));
	}

	public record JSONResponse(String message) {}
	public record LoginRequest(String username, String password) {}
	public record NewGameRequest(String authToken, String gameName) {}
	public record JoinGameRequest(String authToken, ChessGame.TeamColor color, int gameID) {}
}
