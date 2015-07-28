
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Demo created by SG0222490 on 6/22/2015.
 */
public class check_in extends HttpServlet{

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/lib_test";
		String user = "root";
		String password = "root";
		
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
		
        String bookid=request.getParameter("bookid");
	    String cardno=request.getParameter("cardno");
	    String fname=request.getParameter("fname");
	    String lname=request.getParameter("lname");
	    
	    bookid = bookid.trim();
	    cardno = cardno.trim();
	    fname = fname.trim();
	    lname = lname.trim();
	    
		if(bookid== null || bookid.equals("")){
			bookid="%";	
		}
		
		if(cardno== null || cardno.equals("")){
			cardno="%";	
		}
		
		if(fname==null || fname.equals("")){
			fname="%";	
		}			
		
		if(lname==null || lname.equals("")){
			lname="%";	
		}		
        
        try {
		    	
		
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = (Connection) DriverManager.getConnection(url,user,password);
		Statement statement = (Statement) connection.createStatement();
		String query = "Select * from BORROWERS,BOOK_LOANS where BOOK_LOANS.Book_id like '"+bookid+"' "
				+ "and BOOK_LOANS.Card_no like '"+cardno+"' and BORROWERS.Fname like '"+fname+"' and "
						+ "BORROWERS.Lname like '"+lname+"' and BOOK_LOANS.Date_in is NULL and BORROWERS.card_no = BOOK_LOANS.card_no;";
		ResultSet rs = statement.executeQuery(query);
		
		ArrayList loanid_array = new ArrayList();
		ArrayList bookid_array = new ArrayList();
		ArrayList branch_ids = new ArrayList();
		ArrayList<Integer> cardno_array = new ArrayList<Integer>();
		ArrayList combined_data_in = new ArrayList();
		
		while(rs.next())
		        {
		            String loan_id = rs.getString("loan_id");
		            String book_id = rs.getString("book_id");
		            String branchid_number = rs.getString("Branch_id");
		            int card_number = rs.getInt("card_no");
		            
		
		            loanid_array.add(loan_id);
		            bookid_array.add(book_id);
		            branch_ids.add(branchid_number);
		            cardno_array.add(card_number);
		
		        }
		
		combined_data_in.add(loanid_array);
		combined_data_in.add(cardno_array);
		combined_data_in.add(bookid_array);
		combined_data_in.add(branch_ids);
		
		session.setAttribute("row_checkin",combined_data_in);
		response.sendRedirect("Check_in.jsp");
		
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
