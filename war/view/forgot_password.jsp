<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel=stylesheet href="/stylesheet/main.css" type="text/css">
<title>Login</title>
</head>
<body>
	<h1>Forgotten Username or Password</h1>
	<form action="/forgot_password" method="post">
		<table>
			<tr>
				<td align="right"><b>email</b></td>
				<td><input name="email" value="${fn:escapeXml(email)}"></td>
			</tr>

			<tr>
				<td colspan="2"><div class="error">${error}</div></td>
			</tr>
		</table>
		<input type="submit" value="Submit">
	</form>

</body>
</html>