package com.dopple.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dopple.cron.ScreenshotJobScheduler;

/**
 * To implement the periodic mail service
 */
public class PeriodicReportService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PeriodicReportService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

	/**
	 * TO create a cron job for given time and send periodic mail to the user
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String requestpath;
		String url = request.getParameter("url");
		String dashboardName = request.getParameter("title");
		String fileType =  request.getParameter("type");
		String emailId = request.getParameter("id");
		String interval = request.getParameter("interval"); 
		String startTime = request.getParameter("time"); 
		String date = request.getParameter("date"); 

		if(date.isEmpty() || date == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(new Date()); 
			startTime	= dateStr+" "+startTime;

		}else{
			startTime = date+" "+startTime;
		}

		requestpath  = request.getServletContext().getRealPath("/");
		ScreenshotJobScheduler screenshotJobScheduler = new ScreenshotJobScheduler();
		screenshotJobScheduler.JobSchedule(url,fileType,requestpath,dashboardName,emailId,interval,startTime);
	}

}