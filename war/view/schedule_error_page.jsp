<%@page import="org.wintrisstech.erik.sprinkler.ScheduleErrorChecker"%>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Schedule Error</title>
</head>
<body>
	<h1>Schedule Error!</h1>
	<%
		
		if (request.getAttribute(ScheduleErrorChecker.ERROR_MESSAGES_ATTRIBUTE) != null){
			List<String> errorMessageList = (List<String>) request.getAttribute(ScheduleErrorChecker.ERROR_MESSAGES_ATTRIBUTE);
		for(String errorMessage : errorMessageList ){
					%>
					<%= errorMessage %>
					<%	
	} }%>


</body>
</html>