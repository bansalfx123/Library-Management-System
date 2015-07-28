
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.FineBean;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Demo created by SG0222490 on 6/22/2015.
 */
public class search_fines extends HttpServlet{

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/lib_test";
		String user = "root";
		String password = "root";
		String query;
		
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
		
        
        
	    String cardno=request.getParameter("cardno");
	    String fname=request.getParameter("fname");
	    String lname=request.getParameter("lname");
	    
	    cardno = cardno.trim();
	    fname = fname.trim();
	    lname = lname.trim();
	    
		if(cardno== null || cardno.equals("")){
			cardno="%";	
		}
		
		if(fname==null || fname.equals("")){
			fname = "\"%\"";	
		}
		else{
			fname = "\"%"+fname+"%\"";
		}
		
		if(lname==null || lname.equals("")){
			lname = "\"%\"";	
		}
		else{
			lname = "\"%"+lname+"%\"";
		}
        
        try {
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = (Connection) DriverManager.getConnection(url,user,password);
		Statement statement = (Statement) connection.createStatement();
		
		if("%".equals(cardno)){	
			query = "Select f.loan_id,b.card_no,b.fname,b.lname,SUM(f.Fine_Amount) as fine_amount from borrowers b,fines f "
					+ " where f.card_no=b.card_no and f.paid=0";
			
		}else{
			query = "Select f.loan_id,b.card_no,b.fname,b.lname,SUM(f.Fine_Amount) as fine_amount from borrowers b,fines f "
					+ "where f.card_no=b.card_no and f.paid=0 and f.card_no = "+cardno+" and b.Fname like "+fname+" and b.Lname like "+lname+";";	
		}
		
		ResultSet rs = statement.executeQuery(query);
		
	ArrayList<FineBean> fineArray = new ArrayList<FineBean>();
		while(rs.next())
		        {
			
				FineBean bean = new FineBean();
				bean.setAmount(rs.getDouble("fine_amount"));
				bean.setCardNo(rs.getString("card_no"));
				bean.setFname(rs.getString("fname"));
				bean.setLname(rs.getString("lname"));
				bean.setLoanId(rs.getString("loan_id")); 
		
				fineArray.add(bean);
		        }
		
		session.setAttribute("row",fineArray);
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
