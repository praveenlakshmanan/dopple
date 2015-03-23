package com.dopple.action;

import java.io.*;  

import javax.servlet.*;  
import javax.servlet.http.*;  

import org.json.simple.JSONObject;

import com.dopple.DAO.UserDAO;
import com.dopple.model.User;
import com.doppleml.servicelayer.UserService;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

	/**
	 * This action methos is to find the user aunthentication
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String user = request.getParameter("user");
		String password = request.getParameter("psw");
		
		ServletContext context = getServletContext();
		UserService userService = new UserService(context);
		User userObj = userService.authenticate(user, password);
		response.setContentType("text/html");  
	      response.setCharacterEncoding("UTF-8"); 
		if(userObj != null){
			HttpSession session = request.getSession();  
	        session.setAttribute("user",userObj); 
			JSONObject userjson = new JSONObject();
	        userjson.put("loccode", userObj.getLoccode());
	        userjson.put("defaultDashboard", userObj.getDefaultDashboard());
	       // route = user.getDefaultDashboard();
	       // System.out.println(route);
	        response.setContentType("application/json");  
		    response.setCharacterEncoding("UTF-8"); 
		   // JSONObject userObj = (JSONObject) user;
		    PrintWriter out = response.getWriter();
		    out.println(userjson);
		    out.close();
		    
		}else{
			response.getWriter().write("false");
		}
		 /* create userservice object
		
		*/
		
		//authenticate user
		
       // response.sendRedirect("index.html");
	}

}
