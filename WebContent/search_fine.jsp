<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page import="com.library.FineBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link href="style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Fines </title>
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

<body>
<section>

<h2 align="center">  Search and Pay Fine  </h2>
<form action="HServlet7" method="post">
    <div class="col-xs-4">
  		<input type="submit" value="Update Fine">
	</div>
	<br><br>
</form>
<form action="HServlet5" method="post" role="form">
    <div class="col-xs-4">
      <label for="name">Card number:</label>
      <input type="text" class="form-control" name="cardno" placeholder="Enter password">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">First Name of Borrower:</label>
      <input type="text" class="form-control" name="fname" placeholder="Enter password">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">Last Name of Borrower:</label>
      <input type="text" class="form-control" name="lname" placeholder="Enter password">
    </div>
    <br><br><br>
    <div class="col-xs-4">
  		<button type="submit" class="btn btn-default">Submit</button>
	</div>
</form>
<br><br><br>


<form action="HServlet6" method="post" role="form">
<%ArrayList<FineBean> array = (ArrayList)session.getAttribute("row"); %>
	
	<%if(array != null && !("".equals(array))){ %>
	
	<table class="table table-striped table-hover" id="mytable">
            <tr>
            <th> </th>
            <th>Loan ID.</th>
            <th>Card Number</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Fine Amount</th>
            </tr>
       		
           <%
            for(int i=0; i < array.size();i++)
            {%> 
            <tr>
            <td> <input type="radio" id="tupels_id" name="tupels_name" value="<%= array.get(i).getLoanId()%>;<%= array.get(i).getCardNo()%>"></td>        
            <td><%=array.get(i).getLoanId()%></td>
            <td><%=array.get(i).getCardNo()%></td>
            <td><%=array.get(i).getFname()%></td>
            <td><%=array.get(i).getLname()%></td>
            <td><%=array.get(i).getAmount() %></td>
            </tr><%
            }
        %>
        </table>
        
	  <br><br>
	  <input name="Pay Fine" type="submit" value="Pay Fine">
	</form>
		
	<%} %>
	

</section>

</body>
</html>