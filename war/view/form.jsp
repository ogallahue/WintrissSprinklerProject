<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Create Schedule</title>
</head>
<body>
	<h1>Create Your Schedule Here</h1>

	<form id="form" action="edit" method="post">
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
		<table border="1">
			<tr>
				<td><b>Schedule 1 On:</b></td>
				<td><input type="radio" name="00_on_off" value="on"></td>
			</tr>
			<tr>
				<td><b>Schedule 1 Off:</b></td>
				<td><input type="radio" name="00_on_off" value="off" checked></td>
			</tr>

		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Day of Week:</b>&nbsp;</td>
				<td>Sunday <input type="checkbox" name="00_dow0">
				</td>
				<td>Monday <input type="checkbox" name="00_dow1">
				</td>
				<td>Tuesday <input type="checkbox" name="00_dow2">
				</td>
				<td>Wednesday <input type="checkbox" name="00_dow3">
				</td>
				<td>Thursday <input type="checkbox" name="00_dow4">
				</td>
				<td>Friday <input type="checkbox" name="00_dow5">
				</td>
				<td>Saturday <input type="checkbox" name="00_dow6">
				</td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Zone:</b>&nbsp;</td>
				<td>1 <input type="checkbox" name="00_zone00"></td>
				<td>2 <input type="checkbox" name="00_zone01"></td>
				<td>3 <input type="checkbox" name="00_zone02"></td>
				<td>4 <input type="checkbox" name="00_zone03"></td>
				<td>5 <input type="checkbox" name="00_zone04"></td>
				<td>6 <input type="checkbox" name="00_zone05"></td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>

				<td><b>Time On:</b></td>
				<td><input type="time" name="00_time_on"></td>
			</tr>
			<tr>
				<td><b>Time Off:</b></td>
				<td><input type="time" name="00_time_off"></td>

			</tr>
		</table>
		<br />
		<hr />
		<br />
		<!-- Schedule 2 -->
		<table border="1">
			<tr>
				<td><b>Schedule 2</b></td>
				<td>ON <input type="radio" name="01_on_off" value="on"></td>
				<td>OFF <input type="radio" name="01_on_off" value="off" checked></td>
			</tr>
			<!-- <tr>
				<td><b>Schedule 2 Off:</b></td>
			</tr> -->

		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Day of Week:</b>&nbsp;</td>
				<td>Sunday <input type="checkbox" name="01_dow0">
				</td>
				<td>Monday <input type="checkbox" name="01_dow1">
				</td>
				<td>Tuesday <input type="checkbox" name="01_dow2">
				</td>
				<td>Wednesday <input type="checkbox" name="01_dow3">
				</td>
				<td>Thursday <input type="checkbox" name="01_dow4">
				</td>
				<td>Friday <input type="checkbox" name="01_dow5">
				</td>
				<td>Saturday <input type="checkbox" name="01_dow6">
				</td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Zone:</b>&nbsp;</td>
				<td>1 <input type="checkbox" name="01_zone00"></td>
				<td>2 <input type="checkbox" name="01_zone01"></td>
				<td>3 <input type="checkbox" name="01_zone02"></td>
				<td>4 <input type="checkbox" name="01_zone03"></td>
				<td>5 <input type="checkbox" name="01_zone04"></td>
				<td>6 <input type="checkbox" name="01_zone05"></td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>

				<td><b>Time On:</b></td>
				<td><input type="time" name="01_time_on"></td>
			</tr>
			<tr>
				<td><b>Time Off:</b></td>
				<td><input type="time" name="01_time_off"></td>

			</tr>
		</table>
		<br />
		<hr />
		<br />
		<!-- Schedule 3 -->
		<table border="1">
			<tr>
				<td><b>Schedule 3 On:</b></td>
				<td><input type="radio" name="02_on_off" value="on"></td>
			</tr>
			<tr>
				<td><b>Schedule 3 Off:</b></td>
				<td><input type="radio" name="02_on_off" value="off" checked></td>
			</tr>

		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Day of Week:</b>&nbsp;</td>
				<td>Sunday <input type="checkbox" name="02_dow0">
				</td>
				<td>Monday <input type="checkbox" name="02_dow1">
				</td>
				<td>Tuesday <input type="checkbox" name="02_dow2">
				</td>
				<td>Wednesday <input type="checkbox" name="02_dow3">
				</td>
				<td>Thursday <input type="checkbox" name="02_dow4">
				</td>
				<td>Friday <input type="checkbox" name="02_dow5">
				</td>
				<td>Saturday <input type="checkbox" name="02_dow6">
				</td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>
				<td><b>Zone:</b>&nbsp;</td>
				<td>1 <input type="checkbox" name="02_zone00"></td>
				<td>2 <input type="checkbox" name="02_zone01"></td>
				<td>3 <input type="checkbox" name="02_zone02"></td>
				<td>4 <input type="checkbox" name="02_zone03"></td>
				<td>5 <input type="checkbox" name="02_zone04"></td>
				<td>6 <input type="checkbox" name="02_zone05"></td>
			</tr>
		</table>
		<br />
		<table border="1">
			<tr>

				<td><b>Time On:</b></td>
				<td><input type="time" name="02_time_on"></td>
			</tr>
			<tr>
				<td><b>Time Off:</b></td>
				<td><input type="time" name="02_time_off"></td>

			</tr>
		</table>
		<br />
		<hr />
		Add a schedule: <input type="checkbox" name="add_schedule">
		<hr />
		<input type="submit" value="Submit">
	</form>

</body>
</html>