import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DataLayer {
	private static String database = "DMSEDB.db";
	
	public static Boolean SetMessage(String receiver, String message) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:"+database);
			c.setAutoCommit(false);

			String sql = "INSERT INTO messages(userID,message) VALUES (((SELECT userID FROM users WHERE username=?)),?);";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, receiver);
			pstmt.setString(2, message);
			pstmt.executeUpdate();

			pstmt.close();
			c.commit();
			c.close();

			return true;
		} catch (Exception e) {
			Logger.DoLog("Database error: ", e);
		}
		return false;
	}

	@SuppressWarnings("null")
	public static List<String> GetMessages(String receiver) {
		List<String> lst = new LinkedList<String>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:"+database);
			c.setAutoCommit(false);

			String sql = "SELECT message FROM messages JOIN users ON messages.userID=users.userID WHERE users.username = ? ORDER BY msgtime;";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, receiver);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				lst.add(rs.getString("message"));
			}
			
			pstmt.close();
			c.commit();
			c.close();


		} catch (Exception e) {
			Logger.DoLog("Database error: ", e);
		}
		return lst;
	}

	public static List<User> GetUsers() {
		List<User> lst = new LinkedList<User>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:"+database);
			c.setAutoCommit(false);

			String sql = "SELECT username, publickey FROM users ORDER BY username;";

			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				lst.add(new User(rs.getString("username"), rs.getString("publickey")));
			}
			
			pstmt.close();
			c.commit();
			c.close();


		} catch (Exception e) {
			Logger.DoLog("Database error: ", e);
		}
		return lst;
	}

	public static Boolean AddUser(String username, String pubkey) {
		if(CheckIfUserExists(username))
			return false;
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:"+database);
			c.setAutoCommit(false);

			String sql = "INSERT INTO users(username,publickey) VALUES (?,?);";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, pubkey);
			pstmt.executeUpdate();

			pstmt.close();
			c.commit();
			c.close();

			return true;
		} catch (Exception e) {
			Logger.DoLog("Database error: ", e);
		}
		return false;
	}

	private static boolean CheckIfUserExists(String username) {
		Boolean exists = false;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:"+database);
			c.setAutoCommit(false);

			String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				if(rs.getInt(1)==1){
					exists = true;
				}
			}
			
			pstmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			Logger.DoLog("Database error: ", e);
		}
		return exists;
	}

}
