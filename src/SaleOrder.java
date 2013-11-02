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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class SaleOrder extends JFrame {
	// JDBC driver, database URL, username and password
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/companydb";
    static final String USER= "root";
    static final String PASS= "Passw0rd";
    
    private Connection conn;
    private PreparedStatement ps;
    private Statement statement;
    private ResultSet rs;

    /* local variables for frame, panel, button, labels, text fields */
    
    private static final int FRAME_WIDTH = 250;
    private static final int FRAME_HEIGHT = 400;
    
    private JFrame frame;
    private JPanel jpan;
    private JPanel jpanel_tf0, jpanel_tf1, jpanel_tf2, jpanel_tf3;
    private JTextField tf0, tf1, tf2, tf3;
    private JButton b_order;
    private String s_cust_name, s_item, s_ord_case;
    
    PublicUsers pu;
    private String email = "";
    private String db_email = new String("");
    
    
    /* Constructor to create an UI for users login or create a new account */
    public SaleOrder(String setEmail) throws InstantiationException, IllegalAccessException, 
    	ClassNotFoundException, SQLException {
    
    	frame = new JFrame("SALE ORDER");
        
    	jpanel_tf0 = new JPanel(new GridBagLayout());
        jpanel_tf1 = new JPanel(new GridBagLayout());
        jpanel_tf2 = new JPanel(new GridBagLayout());
        jpanel_tf3 = new JPanel(new GridBagLayout());
        
        jpanel_tf0.setBorder(new TitledBorder("LOGIN AS"));
        jpanel_tf1.setBorder(new TitledBorder("COMPANY NAME"));
        jpanel_tf2.setBorder(new TitledBorder("PRODUCT TITLE"));
        jpanel_tf3.setBorder(new TitledBorder("ORDER CASE"));
        
        pu = new PublicUsers();
    	pu.closeFrame();
    	email = pu.getLoginEmail(); 
    	/*
    	try {
  			//Add the data into the database
  			String sql = "select cust_Email from customer";
  			statement = (Statement) conn.createStatement();
  			statement.executeQuery (sql);
  			rs = statement.getResultSet();
  			
  			while ( rs.next() )
  			{
  				db_email = rs.getString("cust_Email");
  			}
  			rs.close ();
  			statement.close ();
  			
  		} catch(Exception e) {
  			System.out.println("Login as : Saleman " + e);
      	}
    	
    	if ( db_email.equals(pu.getLoginEmail()) ) 
    		tf0 = new JTextField("\n\t" + db_email, 10);
    	else
    		tf0 = new JTextField("\n\t" + "Saleman ", 10);*/
        
    	tf0 = new JTextField("\n" + setEmail, 15);
        tf1 = new JTextField("", 15);
        tf2 = new JTextField("", 15);
        tf3 = new JTextField("", 3);
        b_order = new JButton("PROCESS ORDER");
        
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 3, 3,
                0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        jpanel_tf0.add(tf0, constraints);
        jpanel_tf1.add(tf1, constraints);
        jpanel_tf2.add(tf2, constraints);
        jpanel_tf3.add(tf3, constraints);

        constraints.gridx = 4;
        jpanel_tf3.add(b_order, constraints);

        jpan = new JPanel(new GridLayout(4, 1, 10, 10));
        jpan.add(jpanel_tf0);
        jpan.add(jpanel_tf1);
        jpan.add(jpanel_tf2);
        jpan.add(jpanel_tf3);
        this.add(jpan);
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
        frame.getContentPane().add(jpan);
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // connect to database with database name, username and password
        conn = (Connection) DriverManager.getConnection( DB_URL, USER, PASS );
    }
    
    public void populateJList(JList list, String query, Connection connection) throws SQLException
    {
        DefaultListModel model = new DefaultListModel(); //create a new list model

        statement = (Statement) connection.createStatement();
        rs = statement.executeQuery(query); //run your query

        while (rs.next()) //go through each row that your query returns
        {
            s_item = rs.getString("prod_Title"); //get the element in column "item_code"
            model.addElement(s_item); //add each item to the model
        }
        list.setModel(model);

        rs.close();
        statement.close();

    }
    
    public void display() throws Exception {
       
    	/* action for process order button -- salesman can make an order for customers */
        b_order.addActionListener( 
                
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed( ActionEvent event )
               {
                  // perform a new query
                  try 
                  {
                	  /* prepared statement is for insert data into table orders of database companydb */
                	  ps = (PreparedStatement) conn.prepareStatement("insert into orders set cust_Name = ?, prod_Title = ?, ord_Case = ?");
                	  s_cust_name = tf1.getText();
                	  
                	  s_ord_case = tf3.getText();
      	              
      	              ps.setString(1, s_cust_name);
      	              
      	              ps.setString(3, s_ord_case);
      	              
      	              ps.executeUpdate();
      	              System.out.println("A new order has been added to companydb.orders!");
      	              JOptionPane.showMessageDialog( null, "An order has been placed!", "SUCCESS", 1 );
                	  
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                	  System.out.println("Please input enough information!");
                      JOptionPane.showMessageDialog( null, "Please Input Enough Inforamtion",
                    		  						"MAKE ORDER NOTICE", 1 );
                      frame.setVisible(true);
                      tf1.requestFocus();
                     
                  }             
                  //System.exit( 1 ); // terminate application
                                      
                  
               } // end actionPerformed
            } // end ActionListener inner class          
         ); // end call to addActionListener
        
        
    }
    
    /**
 		closeOrderFrame will close the current frame
     */
    public void closeOrderFrame()
    {
    	frame.setVisible(false);
    }
    
    // execute application
    /*public static void main( String args[] ) throws Exception 
    {
    	new SaleOrder(email).display();
    	
    }*/
    
}


