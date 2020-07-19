package util;
import java.sql.*;

public class ConnectionUtil {

	public static Connection getConnection() throws SQLException {
		
		try {
			//Lets Tomcat see where the driver is
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:postgresql://localhost:5432/bank";
		String username = "postgres";
		String password = ""; 
		
		return DriverManager.getConnection(url, username, password);
		
	}
	
	//test if the connection works
	
//	public static void main(String[] args) {
//		
//		//Try with resources will automatically close the resource at the end of the try or catch block
//		try(Connection conn = ConnectionUtil.getConnection()){
//			System.out.println("connection successful");
//		}catch(SQLException e) {
//			System.out.println(e);
//		}
//	}
	
}
