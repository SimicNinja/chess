import client.REPL;
//import server.Server;
import serverfacade.ResponseException;
import serverfacade.ServerFacade;

public class Main
{
	public static void main(String[] args) throws ResponseException
	{
		String serverURL = "http://localhost:8080";
//		startServer();

		new REPL(serverURL).run();

//		clearServer(serverURL);
	}

//	private static void startServer()
//	{
//		Server server = new Server();
//		var port = server.run(8080);
//		System.out.println("Started test HTTP server on " + port);
//	}
//
//	private static void clearServer(String serverURL) throws ResponseException
//	{
//		ServerFacade facade = new ServerFacade(serverURL);
//		facade.clear();
//	}
}