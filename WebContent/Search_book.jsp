<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link href="style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Book</title>
<style>
header {
	 
}
nav {
    line-height:30px;
    background-color:#FFFFFF;
    font: bold 140% 'Yanone Kaffeesatz', arial, sans-serif;
    color:#444;
    height:600px;
    width:350px;
    float:left;
    padding:5px;	      
}
section {
	height:600px;
    width:1200px;
    float:left;
    padding:10px;	
    font: bold 130% 'Yanone Kaffeesatz', arial, sans-serif; 	 
}
footer {
}
</style>
</head>

<header>
<h1 class='head'>
 <img src="header.png"/>
</h1>
</header>

<nav>
	<div class="profile-sidebar">
				<!-- END SIDEBAR BUTTONS -->
				<!-- SIDEBAR MENU -->
				<div class="profile-usermenu">
					<ul class="nav">
						<li class="active">
							<a href="index.jsp">
							<i class="glyphicon glyphicon-home"></i>
							Overview </a>
						</li>
						<li>
							<a href="Search_book.jsp">
							<i class="glyphicon glyphicon-shopping-cart"></i>
							Search Book </a>
						</li>
						<li>
							<a href="Check_in.jsp">
							<i class="glyphicon glyphicon-shopping-cart"></i>
							Check-In </a>
						</li>
						<li>
							<a href="Check_out.jsp">
							<i class="glyphicon glyphicon-shopping-cart"></i>
							Check-Out </a>
						</li>
						<li>
							<a href="search_fine.jsp">
							<i class="glyphicon glyphicon-piggy-bank"></i>
							Fines </a>
						</li>
						<li>
							<a href="Add_user.jsp">
							<i class="glyphicon glyphicon-user"></i>
							Add User </a>
						</li>
					</ul>
				</div>
				<!-- END MENU -->
			</div>
<br><br><br><br><br><br><br><br><br><br><br><br><br>
<a href="https://www.facebook.com/pages/UT-Dallas-McDermott-Library/42569215488">
<img src="fb.jpg">
</a>
<a href="https://twitter.com/#!/LibInstructUTD">
<img src="twitter.jpg">
</a>
<a href="http://www.utdallas.edu/library/mobile/index.html">
<img src="phone.jpg">
</a>
</nav>

<body  onload="SetDate();">
<section>

<h2 align="center">  Search Book  </h2>
<form action="HServlet1" method="post" role="form">
	<div class="col-xs-4">
      <label for="name">Book ID:</label>
      <input type="tel" pattern='\d{10}' class="form-control" id="stdid"  name="Book_id" placeholder="Enter Book ID">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">Book Title:</label>
      <input type="text" class="form-control" id="sdob" name="Title" placeholder="Enter password">
    </div>
    <br><br><br>
	<div class="col-xs-4">
      <label for="name">Author Name:</label>
      <input type="text" class="form-control" id="sname" name="Author" placeholder="Enter Author Name">
    </div>
    <br><br><br>
    <div class="col-xs-4">
  		<button type="submit" class="btn btn-default">Submit</button>
	</div>
	<br><br><br>
	
	<%ArrayList array = (ArrayList)session.getAttribute("row_search"); %>
	
	<%if(array != null && !("".equals(array))){ %>
	
	<table class="table table-striped table-hover">
            <tr>
            <th>Book ID.</th>
            <th>Book Title</th>
            <th>Author(s)</th>
            <th>Branch ID</th>
            <th>Total copies at branch</th>
            <th>Available Copies at branch</th>
            </tr>
        <%		
            ArrayList book_ids=(ArrayList) array.get(0);
            ArrayList titles=(ArrayList) array.get(1);
            ArrayList authors=(ArrayList) array.get(2);
            ArrayList branches=(ArrayList) array.get(3);
            ArrayList<Integer> copies=(ArrayList) array.get(4);
            ArrayList<Integer> avail_copies=(ArrayList) array.get(5);
            
            for(int i=0;i<titles.size();i++)
            {%> 
            <tr>
            <td><% out.print((String)book_ids.get(i)); %></td>
            <td><% out.print((String)titles.get(i)); %></td>
            <td><% out.print((String)authors.get(i)); %></td>
            <td><% out.print((String)branches.get(i)); %></td>
            <td><% out.print((int)copies.get(i)); %></td>
            <td><% out.print((int)avail_copies.get(i)); %></td>
            </tr><%
            }
        %>
        </table>
	
	<%} 
	
	session.setAttribute("row_search", null);
	%>
</form>

</section>

</body>
</html>