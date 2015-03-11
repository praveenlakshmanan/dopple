package com.dopple.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dopple.cron.ScreenshotJobScheduler;

/**
 * To check a job already exists or not
 * it return true or false
 */

public class CheckPeriodicService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckPeriodicService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * To check job exists or not
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String dashboardName = request.getParameter("title");
		ScreenshotJobScheduler screenshotJobScheduler = new ScreenshotJobScheduler();
		String result= screenshotJobScheduler.serviceIsExists(dashboardName);
		response.setContentType("text/html");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(result); 
	}

}
