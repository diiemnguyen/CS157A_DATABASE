/**
 * Group: 479
 * Trinh,Qui Thu (quibie7@gmail.com)
 * Nguyen,Diem Ngoc (diiemnguyen@yahoo.com)
 * Le,Thinh Minh (tmle04@yahoo.com)
 */


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class SaleOrder extends JFrame {
	// JDBC driver, database URL, username and password
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/companydb";
    static final String USER= "root";
    static final String PASS= "Passw0rd";
    
    private Connection conn;
    private PreparedStatement ps;

    /* local variables for frame, panel, button, labels, text fields */
    private JFrame frame;
    private JPanel jpan;
    private JPanel jpanel_tf1, jpanel_tf2, jpanel_tf3;
    private JTextField tf1, tf2, tf3;
    private JButton b_order;
    private String s_cust_name, s_ord_case;
    
    
    /* Constructor to create an UI for users login or create a new account */
    public SaleOrder() throws InstantiationException, IllegalAccessException, 
    	ClassNotFoundException, SQLException {
    	
    	frame = new JFrame("SALE ORDER");
        
        jpanel_tf1 = new JPanel(new GridBagLayout());
        jpanel_tf2 = new JPanel(new GridBagLayout());
        jpanel_tf3 = new JPanel(new GridBagLayout());
        
        jpanel_tf1.setBorder(new TitledBorder("COMPANY NAME"));
        jpanel_tf2.setBorder(new TitledBorder("PRODUCT TITLE"));
        jpanel_tf3.setBorder(new TitledBorder("ORDER CASE"));
        
        tf1 = new JTextField("", 15);
        tf2 = new JTextField("", 15);
        tf3 = new JTextField("", 3);
        b_order = new JButton("PROCESS ORDER");
        
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 3, 3,
                0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        jpanel_tf1.add(tf1, constraints);
        jpanel_tf2.add(tf2, constraints);
        jpanel_tf3.add(tf3, constraints);

        constraints.gridx = 4;
        jpanel_tf3.add(b_order, constraints);

        jpan = new JPanel(new GridLayout(3, 1, 10, 10));
        jpan.add(jpanel_tf1);
        jpan.add(jpanel_tf2);
        jpan.add(jpanel_tf3);
        this.add(jpan);
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 400);
        frame.setVisible(true);
        frame.getContentPane().add(jpan);
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // connect to database with database name, username and password
        conn = (Connection) DriverManager.getConnection( DB_URL, USER, PASS );
    }
    
    public void display() throws Exception {
       
    	/* action for create account button -- user creates a new account with company name and email */
        b_order.addActionListener( 
                
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed( ActionEvent event )
               {
                  // perform a new query
                  try 
                  {
                	  /* prepared statement is for insert data into table customer of database companydb */
                	  ps = (PreparedStatement) conn.prepareStatement("insert into orders set cust_Name = ?, prod_Title = ?, ord_Case = ?");
                	  s_cust_name = tf1.getText();
                	  
                	  s_ord_case = tf3.getText();
      	              
      	              ps.setString(1, s_cust_name);
      	              
      	              ps.setString(3, s_ord_case);
      	              
      	              ps.executeUpdate();
      	              System.out.println("A new order has been added to companydb.orders!");
      	              JOptionPane.showMessageDialog( b_order, "An order has been placed!", "SUCCESS", 0 );
                	  
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
    	new SaleOrder().display();  
    	
    }
    
}


