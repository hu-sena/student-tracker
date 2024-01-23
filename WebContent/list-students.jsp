<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>


<!DOCTYPE html>
<html>

<head>
	<title>Student Tracker App</title>
	<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2> Harvard University</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
		
		<!-- add new button: Add Student -->
		<!--  
		<input type="button" value="Add Student"
			   onclick="window.location.href='add-student-form.jsp'; return=false;"
			   class="add-student-button" />
		-->
		<form action="add-student-form.jsp">
			<input type="submit" value="Add Student"
				   class="add-student-button" />
		</form>
		
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
				</tr>
				
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
					<tr>
						<td>${tempStudent.firstName}</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
					</tr>
				
				</c:forEach>
			</table>
			
		</div>
	</div>

</body>


</html>