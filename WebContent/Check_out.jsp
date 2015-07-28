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
<title>Check-Out</title>
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
    width:1100px;
    float:left;
    padding:10px;	
    font: bold 130% 'Yanone Kaffeesatz', arial, sans-serif; 	 
}
footer {
}
</style>
<script>
            function SetDate()
            {
                var date = new Date();              
                var day = date.getDate();
                var month = date.getMonth() + 1;
                var year = date.getFullYear();
                if (month < 10) month = "0" + month;
                if (day < 10) day = "0" + day;
                var today = year + "-" + month + "-" + day;
                document.getElementById('date_out').value = today;
                             
                date.setDate(date.getDate()+14);
                var day = date.getDate();
                var month = date.getMonth() + 1;
                var year = date.getFullYear();
                if (month < 10) month = "0" + month;
                if (day < 10) day = "0" + day;
                var twoWeek = year + "-" + month + "-" + day;
                document.getElementById('due_date').value = twoWeek;
            }
        </script>
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
<br><br><br><br><br><br><br><br><br>
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

<h2 align="center">  CHECK-OUT  </h2>
<form action="HServlet2" method="post">
	<div class="col-xs-4">
      <label for="name">Book ID:</label>
      <input type="text" class="form-control" name="book_id">
    </div>
    <br><br><br>
	<div class="col-xs-4">
      <label for="name">Branch ID:</label>
      <input type="text" class="form-control" name="branch_id">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">Card Number:</label>
      <input type="text" class="form-control" name="card_no">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">Date Out:</label>
      <input type="text" class="form-control" name="date_out" id="date_out">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">Due Date:</label>
      <input type="text" class="form-control" name="due_date" id="due_date">
    </div>
    <br><br><br>
    <div class="col-xs-4">
  		<button type="submit" class="btn btn-default">Submit</button>
	</div>
	
	<%String msg = (String)request.getAttribute("msg"); %>
	
	<%if(msg!=null && !("".equals(msg))){ %>
	<%=msg %>
	<%} %>

</form>
<br><br><br><br>

<%ArrayList array = (ArrayList)session.getAttribute("row_checkout"); %>
	
	<%if(array != null && !("".equals(array))){ %>
	
	<a> Refer below to guide member to other branch with available copies.</a><br>
	<table style="border: 1px solid; " class="table table-striped table-hover">
            <tr>
            <th>Book ID.</th>
            <th>Book Title</th>
            <th>Branch ID</th>
            <th>Total copies at branch</th>
            <th>Available Copies at branch</th>
            </tr>
        <%		
            ArrayList book_ids=(ArrayList) array.get(0);
            ArrayList titles=(ArrayList) array.get(1);
            ArrayList branches=(ArrayList) array.get(2);
            ArrayList<Integer> copies=(ArrayList) array.get(3);
            ArrayList<Integer> avail_copies=(ArrayList) array.get(4);
            
            for(int i=0;i<titles.size();i++)
            {%> 
            <tr>
            <td><% out.print((String)book_ids.get(i)); %></td>
            <td><% out.print((String)titles.get(i)); %></td>
            <td><% out.print((String)branches.get(i)); %></td>
            <td><% out.print((int)copies.get(i)); %></td>
            <td><% out.print((int)avail_copies.get(i)); %></td>
            </tr><%
            }
        %>
        </table>
	
	<%} 
	session.setAttribute("row_checkout", null);
	%>

</section>

</body>
</html>