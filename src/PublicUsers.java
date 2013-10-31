/**
 * Group: 479
 * Trinh,Qui Thu (quibie7@gmail.com)
 * Nguyen,Diem Ngoc (diiemnguyen@yahoo.com)
 * Le,Thinh Minh (tmle04@yahoo.com)
 */

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PublicUsers extends HttpServlet {
	// JDBC driver, database URL, username and password
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/companydb";
    static final String USER= "root";
    static final String PASS= "Passw0rd";
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement statement;
    private int rowNum;
    
    /* local variables for frame, panel, button, labels, text fields */
    private static final int FRAME_WIDTH = 370;
    private static final int FRAME_HEIGHT = 350;
    
    private JFrame frame;
    private Panel p;
    private JButton b_create, b_login;
    private JTextField f1, f2, f_email;
    private JLabel l1, l2, l_email;
    private String s1, s2, s_email;
    
    private String userName = new String("");
    private ServletConfig config;
    
    
    

    /* Constructor to create an UI for users login or create a new account */
    public PublicUsers() throws InstantiationException, IllegalAccessException, 
    	ClassNotFoundException, SQLException {
    	
    	frame = new JFrame("PUBLIC USERS");
    	p = new Panel();
    	b_create = new JButton("CREATE AN ACCOUNT");
    	b_login = new JButton("LOG IN");
    	
    	f1 = new JTextField(20);
    	f2 = new JTextField(20);
    	f_email = new JTextField(20);
    	
    	l1 = new JLabel("COMPANY NAME");
    	l2 = new JLabel("COMPANY EMAIL");
    	l_email = new JLabel("LOG IN EMAIL: ");
    	
    	JLabel l_newCust = new JLabel("-------------------------------- NEW CUSTOMER ---------------------------");
        p.add(l_newCust);
    	
    	/* new user create an account*/
    	p.add(l1);
    	p.add(f1);
        p.add(l2);
        p.add(f2);	
        p.add(b_create);
        
        JLabel empty = new JLabel("                                                          ");
        p.add(empty);
        
        JLabel empty1 = new JLabel("                                                  ");
        p.add(empty1);
        
        JLabel empty2 = new JLabel("***********************************************************************");
        p.add(empty2);
        
        JLabel empty3 = new JLabel("                                                          ");
        p.add(empty3);
        
        JLabel l_existUser = new JLabel("-------------------------------- EXISTING USER ---------------------------");
        p.add(l_existUser);
       
       
        
        /* existing user login */
        p.add(l_email);
    	p.add(f_email);
        p.add(b_login);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.getContentPane().add(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // connect to database with database name, username and password
        conn = (Connection) DriverManager.getConnection( DB_URL, USER, PASS );
        
    }
    
    public String getName()
    {
    	s1 = f1.getText();
    	return s1; 
    }
    
    public String getEmail()
    {
    	s2 = f2.getText();
    	return s2; 
    }
    
    public void init(ServletConfig config)  throws ServletException
    {
    	 this.config=config;
    }
    
    public void display(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException, Exception {
       
    	/* action for create account button -- user creates a new account with company name and email */
        b_create.addActionListener( 
                
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed( ActionEvent event )
               {
                  // perform a new query
                  try 
                  {
                	  /* prepared statement is for insert data into table customer of database companydb */
                	  ps = (PreparedStatement) conn.prepareStatement("insert into customer set cust_Name = ?,  cust_Email = ?");
      	              s1 = f1.getText();
      	              s2 = f2.getText();
      	              ps.setString(1, s1);
      	              ps.setString(2, s2);
      	              ps.executeUpdate();
      	              System.out.println("Your new account has been created successfully!");
      	              JOptionPane.showMessageDialog( null, "A new account has been created! \n " +
      	              		"You can login to make an order", "SUCCESS", 1 );
      	              f1.setText("");
      	              f2.setText("");
      	              frame.setVisible(true);
                	  
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                	  System.out.println("Please Enter Name and/or Email to create a new account");
                      JOptionPane.showMessageDialog( null, 
                    		  sqlException.getMessage(), "Database error", 
                    		  JOptionPane.ERROR_MESSAGE );
                      f1.setText("");
                      frame.setVisible(true);
                     
                  }             
                  //System.exit( 1 ); // terminate application
                                      
                  
               } // end actionPerformed
            } // end ActionListener inner class          
         ); // end call to addActionListener
        
        
        
        /* action for login button -- user login with email address */
        b_login.addActionListener( 
                
                new ActionListener() 
                {
                   // pass query to table model
                   public void actionPerformed( ActionEvent event )
                   {
                	   try {
	              			//Add the data into the database
	              			String sql = "select cust_Email from customer";
	              			statement = (Statement) conn.createStatement();
	              			statement.executeQuery (sql);
	              			rs = statement.getResultSet();
	              			while (rs.next ()){
	              				userName = rs.getString("cust_Email");
	              			}
	              			rs.close ();
	              			statement.close ();
	              		}catch(Exception e){
	              			System.out.println("Exception is ;"+e);
		              	}
		              		
	            	    if(userName.equals(f_email.getText())) 
	              		{
	              			System.out.println("WELCOME " + userName );
	              			closeFrame();
							
								try {
									new SaleOrder().display();
									//so.display();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
	              			
	              		}
	              		else
	              		{
	              			System.out.println("Please Enter Existing Email");
	              			f_email.setText("");
	              			JOptionPane.showMessageDialog( null, "Please Enter Existing Email", "NOTICE", 1 );
	              		}
                                          
                      
                   } // end actionPerformed
                } // end ActionListener inner class          
             ); // end call to addActionListener
    }
    
    
    
    public void closeFrame()
    {
    	frame.setVisible(false);
    }
    
    
    // execute application
    public static void main( String args[] ) throws Exception 
    {
    	new PublicUsers().display(null, null);  
    	
    }
    
}


