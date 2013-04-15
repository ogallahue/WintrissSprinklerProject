<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../stylesheet/main.css">
<title>Create Schedule</title>
</head>
<body bgcolor="#FFFFFF">
	<h1>Create Your Schedule Here</h1>

	<form action="edit" method="post">
		<table>
			<tr>
				<td>Master On:</td>
				<td><input type="radio" name="master_on_off" value="on" /></td>
			</tr>
			<tr>
				<td>Master Off:</td>
				<td><input type="radio" name="master_on_off" value="off"
					checked /></td>
			</tr>

		</table>
		<hr />
		<br />
		<!-- Schedule 1 -->
		<table border="1" bgcolor="#FFFFFF">
			<tr>
				<td><b>Schedule 1 On:</b></td>
				<td><input type="radio" name="on_off00" value="on"></td>
			</tr>
			<tr>
				<td><b>Schedule 1 Off:</b></td>
				<td><input type="radio" name="on_off00" value="off" checked></td>
			</tr>

		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Day of Week:</b>&nbsp;</td>
				<td>Sunday <input type="checkbox" name="sun00">
				</td>
				<td>Monday <input type="checkbox" name="mon00">
				</td>
				<td>Tuesday <input type="checkbox" name="tue00">
				</td>
				<td>Wednesday <input type="checkbox" name="wed00">
				</td>
				<td>Thursday <input type="checkbox" name="thu00">
				</td>
				<td>Friday <input type="checkbox" name="fri00">
				</td>
				<td>Saturday <input type="checkbox" name="sat00">
				</td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Zone:</b>&nbsp;</td>
				<td>1 <input type="checkbox" name="zone0000"></td>
				<td>2 <input type="checkbox" name="zone0001"></td>
				<td>3 <input type="checkbox" name="zone0002"></td>
				<td>4 <input type="checkbox" name="zone0003"></td>
				<td>5 <input type="checkbox" name="zone0004"></td>
				<td>6 <input type="checkbox" name="zone0005"></td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>

				<td><b>Time On:</b></td>
				<td><input type="time" name="time0000"></td>
			</tr>
			<tr>
				<td><b>Time Off:</b></td>
				<td><input type="time" name="time0001"></td>

			</tr>
		</table>
		<br />
		<hr />
		<br />
		<!-- Schedule 2 -->
		<table border="1">
			<tr>
				<td><b>Schedule 2 On:</b></td>
				<td><input type="radio" name="on_off01" value="on"></td>
			</tr>
			<tr>
				<td><b>Schedule 2 Off:</b></td>
				<td><input type="radio" name="on_off01" value="off" checked></td>
			</tr>

		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Day of Week:</b>&nbsp;</td>
				<td>Sunday <input type="checkbox" name="sun01">
				</td>
				<td>Monday <input type="checkbox" name="mon01">
				</td>
				<td>Tuesday <input type="checkbox" name="tue01">
				</td>
				<td>Wednesday <input type="checkbox" name="wed01">
				</td>
				<td>Thursday <input type="checkbox" name="thu01">
				</td>
				<td>Friday <input type="checkbox" name="fri01">
				</td>
				<td>Saturday <input type="checkbox" name="sat01">
				</td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Zone:</b>&nbsp;</td>
				<td>1 <input type="checkbox" name="zone0100"></td>
				<td>2 <input type="checkbox" name="zone0101"></td>
				<td>3 <input type="checkbox" name="zone0102"></td>
				<td>4 <input type="checkbox" name="zone0103"></td>
				<td>5 <input type="checkbox" name="zone0104"></td>
				<td>6 <input type="checkbox" name="zone0105"></td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>

				<td><b>Time On:</b></td>
				<td><input type="time" name="time0100"></td>
			</tr>
			<tr>
				<td><b>Time Off:</b></td>
				<td><input type="time" name="time0101"></td>

			</tr>
		</table>
		<br />
		<hr />
		<br />
		<!-- Schedule 3 -->
		<table border="1">
			<tr>
				<td><b>Schedule 3 On:</b></td>
				<td><input type="radio" name="on_off02" value="on"></td>
			</tr>
			<tr>
				<td><b>Schedule 3 Off:</b></td>
				<td><input type="radio" name="on_off02" value="off" checked></td>
			</tr>

		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Day of Week:</b>&nbsp;</td>
				<td>Sunday <input type="checkbox" name="sun02">
				</td>
				<td>Monday <input type="checkbox" name="mon02">
				</td>
				<td>Tuesday <input type="checkbox" name="tue02">
				</td>
				<td>Wednesday <input type="checkbox" name="wed02">
				</td>
				<td>Thursday <input type="checkbox" name="thu02">
				</td>
				<td>Friday <input type="checkbox" name="fri02">
				</td>
				<td>Saturday <input type="checkbox" name="sat02">
				</td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Zone:</b>&nbsp;</td>
				<td>1 <input type="checkbox" name="zone0200"></td>
				<td>2 <input type="checkbox" name="zone0201"></td>
				<td>3 <input type="checkbox" name="zone0202"></td>
				<td>4 <input type="checkbox" name="zone0203"></td>
				<td>5 <input type="checkbox" name="zone0204"></td>
				<td>6 <input type="checkbox" name="zone0205"></td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>

				<td><b>Time On:</b></td>
				<td><input type="time" name="time0200"></td>
			</tr>
			<tr>
				<td><b>Time Off:</b></td>
				<td><input type="time" name="time0201"></td>

			</tr>
		</table>
		<br />
		<hr />

		<input type="submit" value="Submit">
		<hr />
	</form>
</body>
</html>