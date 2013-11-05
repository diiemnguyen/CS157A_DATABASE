/**
 * Group: 479
 * Trinh,Qui Thu (quibie7@gmail.com)
 * Nguyen,Diem Ngoc (diiemnguyen@yahoo.com)
 * Le,Thinh Minh (tmle04@yahoo.com)
 */

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SalemanUser class will allow sale person
 *   to login, and then he/she can make an
 *   order for the customer.
 */

class SalemanUser extends JFrame {
	
	// JDBC driver, database URL, username and password
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/companydb";
    static final String USER= "root";
    static final String PASS= "Passw0rd";
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement statement;
    
    /* local variables for frame, panel, button, labels, text fields */
    private static final int FRAME_WIDTH = 280;
    private static final int FRAME_HEIGHT = 160;
    
    private JFrame frame;
    private Panel p;
    private JButton b_login;
    private JTextField f_email;
    private JLabel l_company, l_email;
    
    private String userName = new String("");
    private ServletConfig config;
    private Boolean loop = false;
    
    
	SalemanUser() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		frame = new JFrame("SALEMAN USER");
    	p = new Panel();
 
    	l_company = new JLabel("WHOLESALE FOOD COMPANY");
    	l_email = new JLabel("SALEMAN EMAIL");
    	f_email = new JTextField(12);
    	b_login = new JButton("LOG IN");
    	
  
        /* saleman user login with GUI*/
    	p.add(l_company);
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
	
	
	public void init(ServletConfig config)  throws ServletException
    {
    	 this.config=config;
    }
	
	public void display(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException, Exception {
	       
        /* action for login button -- user login with email address */
        b_login.addActionListener( 
                
                new ActionListener() 
                {
                	
                   public void actionPerformed( ActionEvent event )
                   {
                	   // check if the input text is empty -- show message
                	   if (f_email.getText().isEmpty())
             		   {
             				
                		    System.out.println("Salesman cannot login without email");
             				JOptionPane.showMessageDialog( null, "Salesman cannot login without email", "LOGIN NOTICE", 1 );
							f_email.setText("");
							f_email.requestFocus();
							
             		   } else {	// otherwise, connect table employee for empl_Email info.
                		   
             			   // check only 2 salesmen can login to make an order for customer, 
             			   // others employees cannot login
             			   if ( f_email.getText().equals("lucie@food.com") || f_email.getText().equals( "ken@food.com") )
             			   {
             			   
		                	   try {
		                		   
		                		   do{
		                               loop = false;
		                               userName = new String(f_email.getText());
		                               ps=(PreparedStatement) conn.prepareStatement("select empl_Email  from  employee  where empl_Email=?");
		                               ps.setString(1,userName);
		                               rs=ps.executeQuery();
		                               
		                               if(!rs.next() && rs.getRow() == 0) {
		                            	   System.out.println("Please Enter Salesman Email");
		                                   JOptionPane.showMessageDialog(null,"Login Failed. Please Enter Salesman email!", "LOGIN NOTICE", 1);
		                                   f_email.setText("");
		                                   f_email.requestFocus();
		                                   loop = true;
		                                   frame.setVisible(true);
		                                   break;
		                               } else {
		                                   userName = rs.getString("empl_Email");
		                                   System.out.println("WELCOME " + userName);
		                                   closeSalemanLoginFrame();
										   new SaleOrder(f_email.getText()).display();
		                               }
		                               
		                               //rs.close ();
		  		              		   //statement.close ();
		                           }while (loop);
		                		   
			              		} catch(Exception e) {
			              			
			              			System.out.println("Exception is :"+e);
			              			
				              	} // end try catch
		                	   
             			   } else {
	             				
             				   System.out.println("Only Salesman can log in to make an order!");
	                           JOptionPane.showMessageDialog(null,"Only Salesman can log in to make an order!", "LOGIN NOTICE", 1);
	                           f_email.setText("");
	                           f_email.requestFocus();
	                           loop = true;
	                           frame.setVisible(true);
             			   }
		                	   
		              	
                	   }// end if check empty
                	  

                   } // end actionPerformed
                } // end ActionListener inner class          
             ); // end call to addActionListener
    }
	
	
	/**
		closeSalemanLoginFrame will close the current frame
	 */
	public void closeSalemanLoginFrame()
	{
		frame.setVisible(false);
	}
	
	// Saleman user login frame
	public static void main(String[] args) throws ServletException, IOException, Exception
	{
		new SalemanUser().display(null, null);
	}

} // end SalemanUser class
