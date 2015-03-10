import java.util.List;

public class ResponseBuilder {
	static String XMLHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static String SetMessageResponse(Boolean setMessageResult) {
		return XMLHead + "<setmessageresponse><result>"+(setMessageResult == true ? 1 : 0)+"</result></setmessageresponse>";
	}

	public static String GetMessagesResponse(List<String> messages) {
		String tMessage = XMLHead + "<getmessagesresponse>";
		for(int i = 0; i< messages.size(); i++){
			tMessage += "<message>"+messages.get(i)+"</message>";
		}
		tMessage += "</getmessagesresponse>";
		return tMessage;
	}

	public static String ErrorResponse(String error) {
		return XMLHead+"<error>"+error+"</error>";
	}
	
	public static String AddUserResponse(Boolean result) {
		return XMLHead + "<adduserresponse><result>"+(result == true ? 1 : 0)+"</result></adduserresponse>";
	}

	public static String GetUsersResponse(List<User> users) {
		String tMessage = XMLHead + "<getusersresponse>";
		for(int i = 0; i< users.size(); i++){
			tMessage += "<user>"+
							"<username>"+
								users.get(i).getUsername()+
							"</username>"+
							"<publickey>"+
								users.get(i).getPubKey()
								+"</publickey>"+
						"</user>";
		}
		tMessage += "</getusersresponse>";
		return tMessage;
	}
}
