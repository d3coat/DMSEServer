
public class User {
	private String Username = null;
	private String PubKey = null;
	
	public User(String username, String publicKey){
		setUsername(username);
		setPubKey(publicKey);
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPubKey() {
		return PubKey;
	}

	public void setPubKey(String pubKey) {
		PubKey = pubKey;
	}
}
