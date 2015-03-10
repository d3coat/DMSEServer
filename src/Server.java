import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.sun.xml.internal.ws.handler.ClientSOAPHandlerTube;


public class Server {
	static int ServerPort = 22000;
	public static void Start() {
		StartListening();
		
	}

	@SuppressWarnings({ "resource", "finally" })
	private static void StartListening() {
		ServerSocket listenSocket = null;
		try {
			listenSocket = new ServerSocket(ServerPort);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			while(true){
				try {
					Logger.DoLog("Listening on "+ServerPort);
					Socket clientSocket = listenSocket.accept();
					new HandleRequestThread(clientSocket);
					} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
	}

}
