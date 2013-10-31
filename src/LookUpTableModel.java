/**
 * Group: 479
 * Trinh,Qui Thu (quibie7@gmail.com)
 * Nguyen,Diem Ngoc (diiemnguyen@yahoo.com)
 * Le,Thinh Minh (tmle04@yahoo.com)
 */

/**** THIS CODE IS GIVEN FOR GROUP PROJECT - FALL 2013 ***/
//A TableModel that supplies ResultSet data to a JTable.

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

// ResultSet rows and columns are counted from 1 and JTable 
// rows and columns are counted from 0. When processing 
// ResultSet rows or columns for use in a JTable, it is 
// necessary to add 1 to the row or column number to manipulate
// the appropriate ResultSet column (i.e., JTable column 0 is 
// ResultSet column 1 and JTable row 0 is ResultSet row 1).
public class LookUpTableModel extends AbstractTableModel 
{
   private Connection connection;
   private Statement statement;
   private ResultSet rs;
   private ResultSetMetaData rsMetaData;
   private int numRow;

   // keep track of database connection status
   private boolean connectDB = false;
   
   // constructor initializes rs and obtains its meta data object;
   // determines number of rows
   public LookUpTableModel( String driver, String url, 
      String username, String password, String query ) 
      throws SQLException, ClassNotFoundException
   {         
      // load database driver class
      Class.forName( driver );

      // connect to database
      connection = DriverManager.getConnection( url, username, password );

      // create Statement to query database
      statement = connection.createStatement( 
         ResultSet.TYPE_SCROLL_INSENSITIVE,
         ResultSet.CONCUR_READ_ONLY );

      // update database connection status
      connectDB = true;

      // set query and execute it
      setQuery( query );
   } // end constructor LookUpTableModel

   // get class that represents column type
   public Class getColumnClass( int column ) throws IllegalStateException
   {
      // ensure database connection is available
      if ( !connectDB ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine Java class of column
      try 
      {
         String className = rsMetaData.getColumnClassName( column + 1 );
         
         // return Class object that represents className
         return Class.forName( className );
      } // end try
      catch ( Exception exception ) 
      {
         exception.printStackTrace();
      } // end catch
      
      return Object.class; // if problems occur above, assume type Object
   } // end method getColumnClass

   // get number of columns in ResultSet
   public int getColumnCount() throws IllegalStateException
   {   
      // ensure database connection is available
      if ( !connectDB ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine number of columns
      try 
      {
         return rsMetaData.getColumnCount(); 
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return 0; // if problems occur above, return 0 for number of columns
   } // end method getColumnCount

   // get name of a particular column in ResultSet
   public String getColumnName( int column ) throws IllegalStateException
   {    
      // ensure database connection is available
      if ( !connectDB ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine column name
      try 
      {
         return rsMetaData.getColumnName( column + 1 );  
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return ""; // if problems, return empty string for column name
   } // end method getColumnName

   // return number of rows in ResultSet
   public int getRowCount() throws IllegalStateException
   {      
      // ensure database connection is available
      if ( !connectDB ) 
         throw new IllegalStateException( "Not Connected to Database" );
 
      return numRow;
   } // end method getRowCount

   // obtain value in particular row and column
   public Object getValueAt( int row, int column ) 
      throws IllegalStateException
   {
      // ensure database connection is available
      if ( !connectDB ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // obtain a value at specified ResultSet row and column
      try 
      {
         rs.absolute( row + 1 );
         return rs.getObject( column + 1 );
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return ""; // if problems, return empty string object
   } // end method getValueAt
   
   // set new database query string
   public void setQuery( String query ) throws SQLException, IllegalStateException 
   {
      // ensure database connection is available
      if ( !connectDB ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // specify query and execute it
      rs = statement.executeQuery( query );

      // obtain meta data for ResultSet
      rsMetaData = rs.getMetaData();

      // determine number of rows in ResultSet
      rs.last();             	// move to last row
      numRow = rs.getRow();  	// get row number      
      
      // notify JTable that model has changed
      fireTableStructureChanged();
   } // end method setQuery

   // close Statement and Connection               
   public void disconnectFromDatabase()            
   {              
      if ( !connectDB )                  
         return;

      // close Statement and Connection            
      try                                          
      {                                            
         statement.close();                        
         connection.close();                       
      } // end try                                 
      catch ( SQLException sqlException )          
      {                                            
         sqlException.printStackTrace();           
      } // end catch                               
      finally  // update database connection status
      {                                            
         connectDB = false;              
      } // end finally                             
   } // end method disconnectFromDatabase     
   
}  // end class LookUpTableModel


