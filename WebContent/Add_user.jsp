<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link href="style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Member</title>
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
							<a href="#">
							<i class="glyphicon glyphicon-user"></i>
							Add Member </a>
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

<h2 align="center">  Add Member  </h2>
<form action="HServlet" method="post" >
	<div class="col-xs-4">
      <label for="name">First Name:</label>
      <input type="text" class="form-control" name="fname" placeholder="Enter First Name">
    </div>
    <br><br><br>
	<div class="col-xs-4">
      <label for="name">Last Name:</label>
      <input type="text" class="form-control" name="lname" placeholder="Enter Last Name">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">Street Name:</label>
      <input type="text" class="form-control" name="street_name" placeholder="Enter Street Name">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">City:</label>
      <input type="text" class="form-control" name="city" placeholder="Enter City Name">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">State:</label>
      <input type="text" class="form-control" name="state" placeholder="Enter State Name">
    </div>
    <br><br><br>
    <div class="col-xs-4">
      <label for="name">Phone:</label>
      <input type="text" class="form-control" id="return_date" name="phone" pattern='\(\d{3}\)\s\d{3}\-\d{4}'> Format: (XXX) XXX-XXXX
    </div>
    <br><br><br><br><br>
    <div class="col-xs-4">
  		<button type="submit" class="btn btn-default">Submit</button>
	</div>
	
	<%String msg = (String)request.getAttribute("msg"); %>
	
	<%if(msg!=null && !("".equals(msg))){ %>
	<%=msg %>
	<%} %>
	
</form>

</section>

<footer>
 The Library Managment System | 100 E. Mccallum Road, Richardson, Texas 75080-3021 | 972 888-9999 
</footer>

</body>
</html>