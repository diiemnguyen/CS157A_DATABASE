/**
 * Group: 479
 * Trinh,Qui Thu (quibie7@gmail.com)
 * Nguyen,Diem Ngoc (diiemnguyen@yahoo.com)
 * Le,Thinh Minh (tmle04@yahoo.com)
 */

/**** HOW JDBC CONNECT TO MYSQL - FALL 2013 ***/
import java.sql.*;

public class JDBCConnectCompanyDB {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/companydb";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "Passw0rd"; 	// depends on installing MySQL
   
   public static void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   
	   try {
	      //Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");
	
	      //Open a connection
	      System.out.println("Connecting to database companydb...");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
	      
	      
	      
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");
	}//end main
}//end JDBCExample
