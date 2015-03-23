package com.dopple.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.dopple.model.User;
import com.doppleml.servicelayer.UserService;

public class SetDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetDashboard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

	/**
	 * To update the user default dashboard path
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String path = request.getParameter("newpath");
	    HttpSession session = request.getSession();  
		User userObj = (User) session.getAttribute("user"); 
		userObj.setDefaultDashboard(path);
		ServletContext context = getServletContext();
		UserService userService = new UserService(context);
		userService.setDefaultDashboard(userObj);
	}

}


