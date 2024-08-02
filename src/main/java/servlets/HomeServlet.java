package servlets;

import java.io.IOException;
//import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * Servlet implementation class HomeServlet
 */
//@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	private static final Logger loggerObj = LogManager.getLogger(HomeServlet.class);

    public HomeServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		loggerObj.info("Entered get Method");
		String theId = request.getParameter("id");
		if(theId == null || !theId.matches("\\d+")) {
			response.sendRedirect("studentForm.html");
			loggerObj.error("Invalid ID");
			response.getWriter().print("<h1>Id Invalid</h1>");
			return;
		}		
		Integer id = Integer.parseInt(theId);
		try {
			String db_url = "jdbc:postgresql://host.docker.internal:5432/best_prog_db";
			String username = "postgres";
			String password = "Henry123";			
			Class.forName("org.postgresql.Driver");
			loggerObj.info("Postgresql driver loaded successfuly");
			Connection con = DriverManager.getConnection(db_url,username,password);
			
			PreparedStatement pst =con.prepareStatement("select * from students where id = ?");
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				String lname = rs.getString("lname");
				loggerObj.info("Name is "+lname+" and Id is "+id);
				response.getWriter().print("<h1>Name is "+lname+" and Id is "+id+"</h1>");
			}
			else {
				loggerObj.info("Id entered does not exist");
				response.getWriter().print("<h1>Id not found!!!</h1>");
			}
			
		} catch (SQLException e) {
			loggerObj.error("SQL exceptions caught : connection to database failed"+e);
			e.printStackTrace(response.getWriter());
		} catch (ClassNotFoundException e) {
			loggerObj.error("SQL exceptions caught : Some classes were not found"+e);
			response.getWriter().print(e);
			e.printStackTrace(response.getWriter());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		loggerObj.info("Entered post Method");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		if(!id.matches("\\d+")) {
			loggerObj.warn("An invalid ID was entered!");
			response.getWriter().print("<h1>Id must be an integer</h1>");
			response.sendRedirect("studentForm.html");
			return;
		}
		else {
			Integer theId = Integer.parseInt(id);
			try {
				String db_url = "jdbc:postgresql://localhost:5432/best_prog_db";
				String username = "postgres";
				String password = "Henry123";			
				Class.forName("org.postgresql.Driver");
				Connection con = DriverManager.getConnection(db_url,username,password);
				PreparedStatement pst =con.prepareStatement("insert into students(id,lname) values(?,?)");
				pst.setInt(1, theId);
				pst.setString(2, name);
				int rowsAffected = pst.executeUpdate();
				if(rowsAffected !=0) {	
					loggerObj.info("Data inserted successfully in the database");

					response.getWriter().print("<h1>Inserted success</h1>");
				}
				else {
					loggerObj.error("Insertion failed!");
					response.getWriter().print("<h1>Inserted Failed</h1>");
				}
				
				
			} catch (SQLException e) {
				loggerObj.error("SQL exceptions caught : connection to db failed"+e);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				loggerObj.error("SQL exceptions caught : Some classes were not found"+e);
				e.printStackTrace();
			}
		}
		
	}

}