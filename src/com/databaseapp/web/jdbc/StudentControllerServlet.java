package com.databaseapp.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


// Servlet implementation class StudentControllerServlet
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDBUtil studentDBUtil;
	
	// assign the JDBC connection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	// (life-cycle - execute when first loaded): one-time setup tasks such as initialize resource and connection
	@Override
	public void init() throws ServletException {
		// create studentDBUtil then, pass into datasource (connection pool)
		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}


	// @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			listStudents(request, response);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}


	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Step 1: get students from studentDBUtil
		List<Student> students = studentDBUtil.getStudents();
		
		// Step 2: add students to the request
		request.setAttribute("STUDENT_LIST", students);
		
		// Step 3: send to JSP 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
