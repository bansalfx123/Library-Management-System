
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
public class check_out extends HttpServlet{
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        processRequest(req, res);

    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/lib_test";
		String user = "root";
		String password = "root";
		
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
        
        String bookid = request.getParameter("book_id");
	    String branchid = request.getParameter("branch_id");
	    String cardno = request.getParameter("card_no");
	    String dateout = request.getParameter("date_out");
	    String duedate = request.getParameter("due_date");
	    
	    bookid.trim();
	    branchid.trim();
	    cardno.trim();
	    duedate.trim();
	    dateout.trim();
	    
		
        try {
        			
		    Class.forName("com.mysql.jdbc.Driver");
			Connection connection = (Connection) DriverManager.getConnection(url,user,password);
			Statement statement = (Statement) connection.createStatement();
		    
		    if(bookid == null || branchid == null || cardno == null || bookid.equals("") || branchid.equals("") || cardno.equals("") )
            { 
		    	request.setAttribute("msg", "All Fields are Compulsary.");
	        	RequestDispatcher rd = getServletContext().getRequestDispatcher("/Check_out.jsp");
	            rd.forward(request,response);
            }
		    else{
		    	
		    	String check_if_fine = "select COUNT(*) as finecount from fines where card_no = '"+cardno+"' and paid = 0";
				ResultSet rs9 = connection.createStatement().executeQuery(check_if_fine);
				rs9.next();
				int count_fine =rs9.getInt("finecount");
				if(count_fine !=  0){
	            	request.setAttribute("msg", "Person has pending fine, please pay fine first.");
	            	RequestDispatcher rd = getServletContext().getRequestDispatcher("/Check_out.jsp");
	                rd.forward(request,response);
	            }
				
				
		    	
		    
		    	String query = "select COUNT(1) as rowcount from borrowers where Card_no='"+cardno+"'";
				ResultSet rs = connection.createStatement().executeQuery(query);
				rs.next();
				int count =rs.getInt("rowcount");
				if(count == 0){
	            	request.setAttribute("msg", "Person not registered. Please Add Member.");
	            	RequestDispatcher rd = getServletContext().getRequestDispatcher("/Check_out.jsp");
	                rd.forward(request,response);
	            }
				
				
				
				
		    }
		    
		    String query2 = "select COUNT(1) as rowcount from book_copies where Book_id='"+bookid+"' and Branch_id='"+branchid+"'";
			ResultSet rs2 = statement.executeQuery(query2);
			rs2.next();
			int count2 =rs2.getInt("rowcount");
			
			String query3 = "select COUNT(1) as rowcount from book_loans where Book_id='"+bookid+"' and Branch_id='"+branchid+"'";
			ResultSet rs3 = statement.executeQuery(query3);
			rs3.next();
			int count3 =rs3.getInt("rowcount");
			
			if((count2-count3) <= 0){
				request.setAttribute("msg", "Sorry no copies available, try another branch.");
            	RequestDispatcher rd = getServletContext().getRequestDispatcher("/Check_out.jsp");
                rd.forward(request,response);
                
                String query4 = "Select book_copies.Book_id, book_copies.branch_id, book_copies.no_of_copies, "
                		+ "book_author.title from book_copies,book_author where "
        				+ "book_copies.Book_id like '"+ bookid + "' and book_copies.Book_id = book_author.Book_id";
        		ResultSet rs4 = statement.executeQuery(query4);
        		
        		ArrayList book_ids_out = new ArrayList();
        		ArrayList titles_out = new ArrayList();
        		ArrayList branches_out = new ArrayList();
        		ArrayList<Integer> copy_out = new ArrayList<Integer>();
        		ArrayList<Integer> avail_copy_out = new ArrayList<Integer>();
        		ArrayList combined_data_out = new ArrayList();
        		
        		while(rs4.next())
        		        {
        		            String bookid_name = rs4.getString("Book_id");
        		            String title_name = rs4.getString("Title");
        		            String branchid_number = rs4.getString("Branch_id");
        		            int copies_number = rs4.getInt("No_of_copies");
        		            
        		
        		            book_ids_out.add(bookid_name);
        		            titles_out.add(title_name);
        		            branches_out.add(branchid_number);
        		            copy_out.add(copies_number);
        		
        		        }
        		
        		combined_data_out.add(book_ids_out);
        		combined_data_out.add(titles_out);
        		combined_data_out.add(branches_out);
        		combined_data_out.add(copy_out);
        		
        		for(int i=0;i<titles_out.size();i++)
        		{
        		String sql1="select COUNT(*) as count_out from book_loans where "
        				+ "book_loans.Book_id='"+book_ids_out.get(i)+"' and book_loans.Branch_id='"+branches_out.get(i)+"' "
        						+ "and book_loans.Date_in is NULL";
        		ResultSet rs1=statement.executeQuery(sql1);
        		rs1.next();
        		int temp=rs1.getInt("count_out");
        		avail_copy_out.add((int)copy_out.get(i) - temp);
        		}
        		
        		combined_data_out.add(avail_copy_out);
        		session.setAttribute("row_checkout",combined_data_out);
        		response.sendRedirect("Check_out.jsp");

        	    }
			
			
			else{
    	    	
        	    String query5 = "select max(loan_id) as maxloan from book_loans";
    			ResultSet rs5 = statement.executeQuery(query5);
    			rs5.next();
    			int count5 =rs5.getInt("maxloan");
    			count5 = count5 +1;
    			
    			String query_more3 = "select COUNT(*) as rowcount1 from book_loans where card_no='"+cardno+"'";
				ResultSet result_more3 = connection.createStatement().executeQuery(query_more3);
				result_more3.next();
				int count1= result_more3.getInt("rowcount1");
				System.out.print(count1);
				if(count1 < 3){
					String query6 = "insert into book_loans values('"+count5+"','"+bookid+"','"+branchid+"','"+cardno+"','"+dateout+"','"+duedate+"',NULL);";  
	    			PreparedStatement p=(PreparedStatement) connection.prepareStatement(query6);
	                p.executeUpdate();
	            	request.setAttribute("msg", "Person cannot borrow more books. Already borrowed three books.");
	            	RequestDispatcher rd = getServletContext().getRequestDispatcher("/Check_out.jsp");
	                rd.forward(request,response);
	            }
    			
    			
                
                request.setAttribute("msg", "Book Checked out.");
            	RequestDispatcher rd = getServletContext().getRequestDispatcher("/Check_out.jsp");
                rd.forward(request,response);
                
        	    }
			
	    } catch (SQLException ex) {
	    	 ex.printStackTrace();
	    } catch (ClassNotFoundException ex) {
	    	 ex.printStackTrace();
	    }
		}
	 
}
