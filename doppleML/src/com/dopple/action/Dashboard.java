package com.dopple.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.dopple.model.User;
import com.doppleml.servicelayer.UserService;

public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();  
		//String route;
        User user = (User) session.getAttribute("user"); 
        JSONObject userjson = new JSONObject();
        userjson.put("name", user.getUsername());
        userjson.put("default_dashboard", user.getDefaultDashboard());
        userjson.put("loccode", user.getLoccode());
        userjson.put("role", user.getRole());
        userjson.put("privilege", user.getPrivilege());
       // route = user.getDefaultDashboard();
       // System.out.println(route);
        response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8"); 
	   // JSONObject userObj = (JSONObject) user;
	    PrintWriter out = response.getWriter();
	    out.println(userjson);
	    out.close();
    }

	/**
	 * TO create a cron job for given time and send periodic mail to the user
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("inside");
		String user = request.getParameter("user");
		ServletContext context = getServletContext();
		UserService userService = new UserService(context);
		User userObj = userService.authenticate(user);
		if(userObj != null){
		 JSONObject userjson = new JSONObject();
	        userjson.put("name", userObj.getUsername());
	        userjson.put("default_dashboard", userObj.getDefaultDashboard());
	        userjson.put("loccode", userObj.getLoccode());
	        userjson.put("role", userObj.getRole());
	        userjson.put("privilege", userObj.getPrivilege());
	       // route = user.getDefaultDashboard();
	       // System.out.println(route);
	        response.setContentType("application/json");  
		    response.setCharacterEncoding("UTF-8"); 
		   // JSONObject userObj = (JSONObject) user;
		    PrintWriter out = response.getWriter();
		    out.println(userjson);
		    out.close();
	}else{
		String result = "false";
		 response.setContentType("text/html");  
	      response.setCharacterEncoding("UTF-8");
	      response.getWriter().write(result); 
	}
	}

}


