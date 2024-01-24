<!DOCTYPE html>

<html>

<head>
	<title>Add Student</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
	
	<script type="text/javascript" src="js/student-validation.js"></script>
	
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>&nbsp;Harvard University</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Add Student</h3>
		
		<form action="StudentControllerServlet" method="POST"
			  name="studentForm" onSubmit="return validateForm()">
		
			<!-- servlet to handle types of command to execute -->
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>First Name:</label></td>
						<td><input type="text" name="firstName" /></td>
					</tr>
					<tr>
						<td><label>Last Name:</label></td>
						<td><input type="text" name="lastName" /></td>
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" /></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
				</tbody>
			</table>
			
		</form>
		<div style="clear: both;">
		<p>
			<a href="StudentControllerServlet">Back to List</a>
		</p>
		</div>
		
	</div>
</body>
</html>