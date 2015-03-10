import java.util.Date;


public class Logger {

	public static void DoLog(String logstr) {
		System.out.println(new Date().toString() + " : "+ logstr);
	}

	public static void DoLog(String logstr, Exception e) {
		DoLog(logstr +" "+ e.getMessage() +" "+ e.getStackTrace());
	}

}
