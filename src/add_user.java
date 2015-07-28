
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * Demo created by SG0222490 on 6/22/2015.
 */
public class add_user extends HttpServlet{
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	 {
	     processRequest(req, res);
	
	 }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
		String url = "jdbc:mysql://localhost:3306/lib_test";
		String user = "root";
		String password = "root";
		int new_cardno;
		
        try {
            String fname=request.getParameter("fname");
            String lname=request.getParameter("lname");
            String street_name =request.getParameter("street_name");
            String city=request.getParameter("city");
            String state =request.getParameter("state");
            String phone_no =request.getParameter("phone");
            
            fname = fname.trim();
            lname = lname.trim();
            street_name = street_name.trim();
            city = city.trim();
            state = state.trim();
            phone_no = phone_no.trim();
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= (Connection) DriverManager.getConnection(url,user,password);
            Statement statement =(Statement) connection.createStatement();
             
            if(fname==null||lname==null||fname==null||street_name==null||city==null||state==null ||phone_no==null||fname.equals("") || lname.equals("") || 
            		street_name.equals("") || city.equals("") || state.equals("") || phone_no.equals("")){
                
            	request.setAttribute("msg", "All fields are compulsory!");     
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Add_user.jsp");
                rd.forward(request,response);
            }
            else{
                String query="select Count(*) as rowcount from borrowers where fname='"+fname+"' and lname='"+lname+"' and address='"+street_name+"' "
                		+ "and city='"+city+"' and state='"+state+"' and phone='"+phone_no+"';";
                ResultSet rs=statement.executeQuery(query);
                rs.next();
                int count =rs.getInt("rowcount");
                if(count >=1){
                	request.setAttribute("msg", "The borrower already exist!");
                	RequestDispatcher rd = getServletContext().getRequestDispatcher("/Add_user.jsp");
                    rd.forward(request,response);
                }
                else{
	                
	                String third_query = "insert into borrowers (fname,lname,address,city,state,phone) values"
	                		+ "('"+fname+"','"+lname+"','"+street_name+"','"+city+"','"+state+"','"+phone_no+"');";
	                PreparedStatement pre_stat = (PreparedStatement) connection.prepareStatement(third_query);
		            pre_stat.executeUpdate();
		            
		            request.setAttribute("msg", "The borrower has been added!");
		            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Add_user.jsp");
		            rd.forward(request,response);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
        	ex.printStackTrace();
        }
    }
}
