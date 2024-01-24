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
		super.init();
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
			
			// read the "command" parameter from form
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing student
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch (theCommand) {
				case "LIST":
					listStudents(request, response);
					break;
					
				case "LOAD":
					loadStudent(request, response);
					break;
					
				case "UPDATE":
					updateStudent(request, response);
					break;
					
				case "DELETE":
					deleteStudent(request, response);
					break;
				
				default: 
					listStudents(request, response);
			}
			
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // read the "command" parameter
            String theCommand = request.getParameter("command");
                    
            // route to the appropriate method
            switch (theCommand) {
                            
            case "ADD":
                addStudent(request, response);
                break;
                                
            default:
                listStudents(request, response);
            }
                
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
        
    }


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Step 1: read student ID from form
		String theStudentId = request.getParameter("studentId");
		
		// Step 3: delete the student from the database
		studentDBUtil.deleteStudent(theStudentId);
		
		// Step 4: send to JSP 
		listStudents(request, response);
	}


	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Step 1: read student info from form
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// Step 2: create a new student object
		Student theStudent = new Student(id, firstName, lastName, email);
		
		// Step 3: update the student to the database
		studentDBUtil.updateStudent(theStudent);
		
		// Step 4: send to JSP 
		listStudents(request, response);
		
	}


	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Step 1: read student ID from form
		String theStudentId = request.getParameter("studentId");
		
		// Step 2: get student from StudentDBUtil
		Student theStudent = studentDBUtil.getStudent(theStudentId);
		
		// Step 3: add student to the request
		request.setAttribute("THE_STUDENT", theStudent);
		
		// Step 4: send to JSP (update-student-form)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Step 1: read student info from form
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// Step 2: create a new student object
		Student theStudent = new Student(firstName, lastName, email);
		
		// Step 3: add the student to the database
		studentDBUtil.addStudent(theStudent);
		
		// Step 4: send back to main list-students JSP- use sendRedirect to avoid multiple reload issue (submissions)
		response.sendRedirect(request.getContextPath() + "/StudentControllerServlet?command=LIST");
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
