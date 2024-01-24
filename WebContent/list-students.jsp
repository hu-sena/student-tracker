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
			<h2>&nbsp;Harvard University</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
		
		<!-- add search box: Search Student -->
		<form action="StudentControllerServlet" method="GET">
			<input type="hidden" name="command" value="SEARCH" />
			
            	Search student: <input type="text" name="theSearchName" />
            <input type="submit" value="Search" class="add-student-button" />
        </form>
		
		<!-- add new button: Add Student -->
		
		<form action="add-student-form.jsp">
			<input type="submit" value="Add Student"
				   class="add-student-button" />
		</form>
		
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
				
				<!-- set up a link for each student -->
				<c:url var="tempLink" value="StudentControllerServlet">
					<c:param name="command" value="LOAD" />
					<c:param name="studentId" value="${tempStudent.id}" />
				<!-- example: .../StudentControllerServlet?command=LOAD&studentId=1 -->
				
				</c:url>
				
				<!-- set up a link for each student -->
				<c:url var="deleteLink" value="StudentControllerServlet">
					<c:param name="command" value="DELETE" />
					<c:param name="studentId" value="${tempStudent.id}" />
				<!-- example: .../StudentControllerServlet?command=DELETE&studentId=1 -->
				
				</c:url>
				
					<tr>
						<td>${tempStudent.firstName}</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
						<td>
							<a href="${tempLink}">Update</a>
							| 
							<a href="${deleteLink}"
							   onClick="if (!(confirm('Are you sure you want to delete this student?'))) return false">
							   Delete</a>
						</td>
					</tr>
				
				</c:forEach>
			</table>
			
		</div>
	</div>

</body>


</html>