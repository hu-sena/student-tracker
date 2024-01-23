package com.databaseapp.web.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	
	// initialize the source (database)
	private DataSource dataSource;
	
	// dependency injection
	public StudentDBUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<>();
		
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResult = null;
		
		try {
			// Step 1: get JDBC connection
			myConnection = dataSource.getConnection();
			
			// Step 2: create SQL statement
			String SQL = "SELECT * FROM student ORDER BY last_name";
			myStatement = myConnection.createStatement();
			
			// Step 3: execute the SQL query
			myResult = myStatement.executeQuery(SQL);
			
			// Step 4: process result set - loop through the results
			while (myResult.next()) {
				
				// retrieve data from result set into a row
				int id = myResult.getInt("id");
				String firstName = myResult.getString("first_name");
				String lastName = myResult.getString("last_name");
				String email = myResult.getString("email");
				
				// create a new student object
				Student tempStudent = new Student(id, firstName, lastName, email);
				
				// add into the list of students
				students.add(tempStudent);
			}
			
			return students;
			
		} finally {
			// Step 4: close JDBC objects connection
			close(myConnection, myStatement, myResult);
		}
		
	}

	
}
