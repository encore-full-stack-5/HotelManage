package global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBInit {
	private static final String URL = "jdbc:mysql://localhost:3306/hotel";
	private static final String USER = "hotel";
	private static final String PW = "1234";
	
	private static Connection c;
	public static Connection getConnection() throws SQLException {
		if (c == null) c = DriverManager.getConnection(URL, USER, PW);
		return c;
	}
}