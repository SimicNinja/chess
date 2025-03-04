package server;

import dataaccess.DataAccessException;
import model.UserData;
import service.UserManagement;
import spark.*;
import com.google.gson.Gson;

public class Server
{
	private UserManagement userService = new UserManagement();

	public int run(int desiredPort)
	{
		Spark.port(desiredPort);

		Spark.staticFiles.location("web");

		// Register your endpoints and handle exceptions here.
		Spark.post("/user", this::addUser);

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

	private Object addUser(Request request, Response response)
	{
		UserData registerRequest = new Gson().fromJson(request.body(), UserData.class);

		try
		{
			UserManagement.RegisterResult result = userService.register(registerRequest);
			response.status(200);
			return new Gson().toJson(result);
		}
		catch(DataAccessException e)
		{
			if(e.getMessage().contains("must provide"))
			{
				response.status(400);
				return new Gson().toJson(new ErrorResponse("Error: bad request"));
			}
			else if(e.getMessage().contains("already exists."))
			{
				response.status(403);
				return new Gson().toJson(new ErrorResponse("Error: already taken"));
			}
			else
			{
				response.status(500);
				return new Gson().toJson(new ErrorResponse("Error: " + e.getMessage()));			}
		}
	}

	public record ErrorResponse(String message) {}
}
