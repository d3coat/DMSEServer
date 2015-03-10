import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class HandleRequestThread extends Thread{
	Socket ClientSocket = null;
	DataInputStream DataIn;
	DataOutputStream DataOut;
	
	public HandleRequestThread(Socket clientSocket) {
		ClientSocket = clientSocket;
		try {
			DataIn = new DataInputStream(ClientSocket.getInputStream());
			DataOut = new DataOutputStream(ClientSocket.getOutputStream());
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
	    try {
	    	String input = DataIn.readUTF();
	    	String output = ParseInput(input);
	    	DataOut.writeUTF(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String ParseInput(String input) {
		String requestType = ParseHelper.ReadXMLValue("RequestType", input);
		RequestType re = RequestType.valueOf(requestType.toUpperCase());
		if(re.equals(RequestType.ADDMESSAGE))
		{
			String receiver = ParseHelper.ReadXMLValue("Receiver", input);
			String message = ParseHelper.ReadXMLValue("Message", input);
			if(receiver!=null && message!=null){
				Boolean result = DataLayer.SetMessage(receiver, message);
				return ResponseBuilder.SetMessageResponse(result);
			} else {
				return ResponseBuilder.ErrorResponse("Receiver og message tag not accepted");
			}
		} 
		else if(re.equals(RequestType.GETMESSAGES))
		{
			String receiver = ParseHelper.ReadXMLValue("Receiver", input);
			List<String> messages = DataLayer.GetMessages(receiver);
			return ResponseBuilder.GetMessagesResponse(messages);
		}
		else if(re.equals(RequestType.ADDUSER))
		{
			String username = ParseHelper.ReadXMLValue("Username", input);
			String pubkey = ParseHelper.ReadXMLValue("PublicKey", input);
			Boolean result = DataLayer.AddUser(username, pubkey);
			return ResponseBuilder.AddUserResponse(result);
		}
		else if(re.equals(RequestType.GETUSERS))
		{
			List<User> users = DataLayer.GetUsers();
			return ResponseBuilder.GetUsersResponse(users);
		}
		else
		{
			return ResponseBuilder.ErrorResponse("RequestType not recoqnised");
		}
	}
	
	public enum RequestType {
	    GETMESSAGES,
	    GETUSERS,
	    ADDUSER,
	    ADDMESSAGE
	}
}