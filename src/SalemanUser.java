/**
 * Group: 479
 * Trinh,Qui Thu (quibie7@gmail.com)
 * Nguyen,Diem Ngoc (diiemnguyen@yahoo.com)
 * Le,Thinh Minh (tmle04@yahoo.com)
 */

import javax.servlet.ServletConfig;
import javax.swing.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.awt.*;
import java.awt.event.*;
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
    private int rowNum;
    
    /* local variables for frame, panel, button, labels, text fields */
    private static final int FRAME_WIDTH = 280;
    private static final int FRAME_HEIGHT = 160;
    
    private JFrame frame;
    private Panel p;
    private JButton b_login;
    private JTextField f_email;
    private JLabel l_company, l_email;
    private String s1, s_email;
    
    private String userName = new String("");
    private ServletConfig config;
    
    
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
	
	
	/**
		closeSalemanLoginFrame will close the current frame
	 */
	public void closeSalemanLoginFrame()
	{
		frame.setVisible(false);
	}
	
	// Saleman user login frame
	public static void main(String[] args) throws InstantiationException, 
			IllegalAccessException, ClassNotFoundException, SQLException
	{
		new SalemanUser();
	}

} // end SalemanUser class