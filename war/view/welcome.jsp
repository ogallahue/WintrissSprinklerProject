<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.wintrisstech.erik.sprinkler.WelcomeServlet"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel=stylesheet href="/stylesheet/main.css" type="text/css">
<title>Welcome</title>
</head>
<body>
	<%
		/* pageContext.setAttribute("username", request
				.getAttribute(WelcomeServlet.USERNAME_ATTRIBUTE)); */
		if (request.getAttribute(WelcomeServlet.USERNAME_ATTRIBUTE) != null) {
	%>
	<div align="right">
		${username} (<a class="login-link" href="/logout">logout</a>)

	</div>
	<%
		} else {
	%>
	<div align="right">
		<a class="login-link" href="/login">login</a>, <a class="login-link"
			href="register">register</a>
	</div>
	<%
		}
	%>
	<div class="main-title">Welcome</div>
	<%
		if (request.getAttribute(WelcomeServlet.USERNAME_ATTRIBUTE) != null) {
			if (request
					.getAttribute(WelcomeServlet.SPRINKLER_NAME_ATTRIBUTE) != null) {
	%>
	<p>
		Your sprinkler is: <b>${sprinklername}</b>
	<ul>
		<li><a href="/edit">Edit schedule</a>
		<li><a href="/id.txt">Download ID file</a>
	</ul>
	<%
		} else {
	%>

	<p>
		<a href="/sprinkler">Register your sprinkler</a>
		<%
			}
			}
		%>
	
</body>
</html>