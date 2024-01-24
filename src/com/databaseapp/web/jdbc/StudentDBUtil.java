package com.databaseapp.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
			
			// Step 3: execute the SQL statement
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

	private void close(Connection myConnection, Statement myStatement, ResultSet myResult) {
		
		try {
			if (myResult != null) {
				myResult.close();
			}
			if (myStatement != null) {
				myStatement.close();
			}
			// put back into connection pool
			if (myConnection != null) {
				myConnection.close();
			}
		} catch (Exception exc) {
			// provides info about the sequence of method calls till the exception occurred
			exc.printStackTrace();
		}
		
	}


	public void addStudent(Student theStudent) throws Exception {
		
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			// Step 1: get DBC connection
			myConnection = dataSource.getConnection();
			
			// Step 2: create SQL statement: insert
			String SQL = "INSERT INTO student "
					   + "(first_name, last_name, email) "
					   + "values (?, ?, ?)";
			myStatement = myConnection.prepareStatement(SQL);
			
			// Step 3: set the param values 
			myStatement.setString(1,  theStudent.getFirstName());
			myStatement.setString(2,  theStudent.getLastName());
			myStatement.setString(3,  theStudent.getEmail());
			
			// Step 4: execute the SQL statement
			myStatement.execute();
			
		} finally {
			// Step 4: close JDBC objects connection
			close(myConnection, myStatement, null);
		}
			
		
	}

	public Student getStudent(String theStudentId) throws Exception {
		// when student does not exist
		Student theStudent = null;
		
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		ResultSet myResult = null;
		int studentId;
		
		try {
			// Step 0: convert student ID into int
			studentId = Integer.parseInt(theStudentId);
			
			// Step 1: get DBC connection
			myConnection = dataSource.getConnection();
			
			// Step 2: create SQL statement: select based on ID
			String SQL = "SLECT * FROM student "
					   + "WHERE id=?";
			myStatement = myConnection.prepareStatement(SQL);
			
			// Step 3: set the param values 
			myStatement.setInt(1,  studentId);
			
			// Step 4: execute the SQL statement
			myResult = myStatement.executeQuery();
			
			// Step 5: retrieve data from result set
			if (myResult.next()) {
							
				String firstName = myResult.getString("first_name");
				String lastName = myResult.getString("last_name");
				String email = myResult.getString("email");
				
				// create the student object using student ID
				theStudent = new Student(studentId, firstName, lastName, email);	
			} else {
				throw new Exception("Could not find student ID: " + studentId);
			}
			return theStudent;
			
		} finally {
			// Step 4: close JDBC objects connection
			close(myConnection, myStatement, myResult);
		}
			
		
		
	}
}
