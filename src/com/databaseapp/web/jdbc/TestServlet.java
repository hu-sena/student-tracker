package com.databaseapp.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;




// Servlet implementation class TestServlet
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// define datasource (connection pool) for Resource Injection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	
	// @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Step 1: set up the printWriter
		PrintWriter out = response.getWriter();
		
		// Step 2: get a connection to the database
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResult = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			// Step 3: create SQL statement
			String querySQL = "select * from student";
			myStatement = myConnection.createStatement();
			
			// Step 4: execute SQL query
			myResult = myStatement.executeQuery(querySQL);
			
			// Step 5: process the result set
			while (myResult.next()) {
				String email = myResult.getString("email");
				out.println(email);
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}

}
