package com.dopple.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.dopple.mail.EmailUtility;
import com.doppleml.servicelayer.ScreenShotGeneratorService;

/**
 * Servlet implementation class ReportService
 */
@WebServlet("/ReportService")
public class ReportService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("########GETinside");
		//System.out.println(request.getParameter("fname"));
		//System.out.println(request.getParameter("lname"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("########POSTinside");
		String url = request.getParameter("url");
		String fileType = request.getParameter("type");
		String[] mailId = request.getParameter("id").split(",");
		
		String dashboardName = request.getParameter("title");
		String path  = request.getServletContext().getRealPath("/");
		System.out.println("path =>"+path);
		ScreenShotGeneratorService screenShotService = new ScreenShotGeneratorService();
		String filename = null;
		filename = screenShotService.screenShot(url,fileType,path);
		System.out.println("attachmentPath:"+filename);
		EmailUtility emailUtility = new EmailUtility();
		String result = null;	
			
				try {
					result = emailUtility.mailDashBoardReport( mailId,dashboardName, filename);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  response.setContentType("text/html");  
			      response.setCharacterEncoding("UTF-8"); 
			      response.getWriter().write(result); 
		System.out.println("result:"+result);
	}
	
}