
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
public class search_book extends HttpServlet{

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/lib_test";
		String user = "root";
		String password = "root";
		
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
		 
        String bookid=request.getParameter("Book_id");
	    String title=request.getParameter("Title");
	    String author=request.getParameter("Author");
	
	    bookid = bookid.trim();
	    title = title.trim();
	    author = author.trim();
	    
		if(bookid== null || bookid.equals("")){
			bookid="%";	
		}
		
		
		if(title==null || title.equals("")){
			title="\"%\"";	
		}
		else{
			title="\"%"+title+"%\"";
		}			
		
		if(author==null || author.equals("")){
			author="\"%\"";	
		}
		else{
			author="\"%"+author+"%\"";
		}			
        
        try {
		    
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = (Connection) DriverManager.getConnection(url,user,password);
		Statement statement = (Statement) connection.createStatement();
		String query = "Select book_copies.Book_id, book_copies.branch_id, book_copies.no_of_copies, book_author.title, book_author.author from book_copies,book_author where "
				+ "book_copies.Book_id like '"+ bookid + "' and book_author.Title like " +title+" and book_author.Author like "+author+" and book_copies.Book_id = book_author.Book_id";
		ResultSet rs = statement.executeQuery(query);
		
		ArrayList book_ids = new ArrayList();
		ArrayList titles = new ArrayList();
		ArrayList authors = new ArrayList();
		ArrayList branches = new ArrayList();
		ArrayList<Integer> copy = new ArrayList<Integer>();
		ArrayList<Integer> avail_copy = new ArrayList<Integer>();
		ArrayList combined_data = new ArrayList();
		
		while(rs.next())
		        {
		            String bookid_name = rs.getString("Book_id");
		            String title_name = rs.getString("Title");
		            String author_name = rs.getString("Author");
		            String branchid_number = rs.getString("Branch_id");
		            int copies_number = rs.getInt("No_of_copies");
		            
		
		            book_ids.add(bookid_name);
		            titles.add(title_name);
		            authors.add(author_name);
		            branches.add(branchid_number);
		            copy.add(copies_number);
		
		        }
		
		combined_data.add(book_ids);
		combined_data.add(titles);
		combined_data.add(authors);
		combined_data.add(branches);
		combined_data.add(copy);
		
		for(int i=0;i<titles.size();i++)
		{
		String query1="select COUNT(*) as count_out from book_loans as bl where bl.Book_id='"+book_ids.get(i)+"' and bl.Branch_id='"+branches.get(i)+"' and bl.Date_in is NULL";
		ResultSet rs1=statement.executeQuery(query1);
		rs1.next();
		int temp = rs1.getInt("count_out");
		avail_copy.add((int)copy.get(i) - temp);
		}
		
		combined_data.add(avail_copy);
		
		session.setAttribute("row_search",combined_data);
		response.sendRedirect("Search_book.jsp");
		
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
