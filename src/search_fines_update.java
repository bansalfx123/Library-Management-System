
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * Demo created by SG0222490 on 6/22/2015.
 */
public class search_fines_update extends HttpServlet{

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/lib_test";
		String user = "root";
		String password = "root";
		
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
        ResultSet rs=null;
        
        try {
        	
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = (Connection) DriverManager.getConnection(url,user,password);
		Statement statement = (Statement) connection.createStatement();
		
		String sql="select loan_id,datediff(curdate(),due_date) as diff,card_no from book_loans where date_in is null;";
		 rs = statement.executeQuery(sql);
		
        while(rs.next())
                {
                    int loan_id = rs.getInt("loan_id");
                    int difference = rs.getInt("diff");
                    int card_no = rs.getInt("card_no");
                    
                    
                    String sql1="select count(*) as count from fines where loan_id="+loan_id+";";
            		ResultSet rs1 = connection.createStatement().executeQuery(sql1);
                    rs1.next();
                    
                    int present = rs1.getInt("count");
                    if(difference>0)
                    {
                        double fine_amount = difference*0.25;
                        if(present != 0)
                        {
                        	String sql3 = "UPDATE fines SET fine_amt="+fine_amount+" WHERE loan_id="+loan_id+";";
                    		PreparedStatement p = (PreparedStatement) connection.prepareStatement(sql3);
                            p.executeUpdate();
                        }
                        else
                        {
                        	String sql4 = "INSERT INTO fines VALUES ("+loan_id+","+card_no+","+fine_amount+",0);";
                    		PreparedStatement p = (PreparedStatement) connection.prepareStatement(sql4);
                            p.executeUpdate();
                        }                             
                    }
                }
        
        response.sendRedirect("search_fine.jsp");
        
	    } catch (SQLException ex) {
	    	 ex.printStackTrace();
	    } catch (ClassNotFoundException ex) {
	    	 ex.printStackTrace();
	    }
        finally{
        	try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		}

	
	 public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
     {
         processRequest(req, res);

     }
}
