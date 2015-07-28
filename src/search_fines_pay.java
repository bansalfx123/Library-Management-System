
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
public class search_fines_pay extends HttpServlet{

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/lib_test";
		String user = "root";
		String password = "root";
		
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();	
        
        try {
        	
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = (Connection) DriverManager.getConnection(url,user,password);
		Statement statement = (Statement) connection.createStatement();
		
		String master = request.getParameter("tupels_name");
		String[] parts = master.split(";");
		String loan_id = parts[0];
		String card_no = parts[1];
		
		String sql="update fines set paid = 1 where fines.Card_no="+card_no+" or fines.loan_id="+loan_id+";";
		PreparedStatement p = (PreparedStatement) connection.prepareStatement(sql);
        p.executeUpdate();
		
        
        response.sendRedirect("search_fine.jsp");
		
	    } catch (SQLException ex) {
	    	 ex.printStackTrace();
	    } catch (ClassNotFoundException ex) {
	    	 ex.printStackTrace();
	    }
		}

	
	 public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
     {
         processRequest(req, res);

     }
}
