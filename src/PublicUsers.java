/**
 * Group: 479
 * Trinh,Qui Thu (quibie7@gmail.com)
 * Nguyen,Diem Ngoc (diiemnguyen@yahoo.com)
 * Le,Thinh Minh (tmle04@yahoo.com)
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class PublicUsers {
	// JDBC driver, database URL, username and password
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/companydb";
    static final String USER= "root";
    static final String PASS= "Passw0rd";
	private static final int GAP = 0;
    
    private Connection conn;
    private PreparedStatement ps;

    /* local variables for frame, panel, button, labels, text fields */
    private JFrame frame;
    private Panel p;
    private JPanel panel;
    private JButton b_create, b_login;
    private JTextField f1, f2, f_email;
    private JLabel l1, l2;
    private JLabel l_email;
    private String s1,s2;
    
    
    

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

        frame.setSize(370,350);
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(new JSeparator(JSeparator.HORIZONTAL));
        
        frame.getContentPane().add(p);
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // connect to database with database name, username and password
        conn = (Connection) DriverManager.getConnection( DB_URL, USER, PASS );
    }
    
    public void display() throws Exception {
       
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
      	              int rs = ps.executeUpdate();
      	              System.out.println("Your new account has been created successfully!");
      	              JOptionPane.showMessageDialog( b_create, "A new account has been created!", "SUCCESS", 0 );
                	  
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                      JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                  }             
                  System.exit( 1 ); // terminate application
                                      
                  
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
                      // perform a new query
                      try 
                      {
                    	  /* prepared statement is for select database table */
                    	  ps = (PreparedStatement) conn.prepareStatement("select cust_Email from customer where cust_Email = ?");
          	              s1 = f1.getText();
          	              ps.setString(1, s1);
          	              boolean rs = ps.execute();
          	              System.out.println("Login successfully!");
          	              JOptionPane.showMessageDialog( b_login, "Your are login!", "SUCCESS", 0 );
                    	  
                      } // end try
                      catch ( SQLException sqlException ) 
                      {
                    	  JOptionPane.showMessageDialog( null, 
                            sqlException.getMessage(), "Database error", 
                            JOptionPane.ERROR_MESSAGE );
                         
                      }             
                      System.exit( 1 ); // terminate application
                                          
                      
                   } // end actionPerformed
                } // end ActionListener inner class          
             ); // end call to addActionListener
    }
    
    
    // execute application
    public static void main( String args[] ) throws Exception 
    {
    	new PublicUsers().display();  
    	
    }
    
}


