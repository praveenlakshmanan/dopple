
package com.dopple.cron;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.dopple.action.AppConstants;
import com.dopple.mail.EmailUtility;
/**
 * To take a screen shot and store in to some location
 * @author hduser
 *
 */
public class ScreenshotJob implements Job{

	@Override
	/**
	 * This method will create a screen shot for given url and file format
	 * and stored in location
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey key = context.getJobDetail().getKey();
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		
		String url= dataMap.getString("url");
		String fileType = dataMap.getString("outputFileType");
		String requestpath = dataMap.getString("requestpath");
		String dashboardName = dataMap.getString("dashboardName");
		String email = dataMap.getString("email");
		
		String s = null;
		String todaydate;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
		Date date = new Date();	
		EmailUtility emailUtility = new EmailUtility();
		
		todaydate = dateFormat.format(date);
		try {
			String filename = "Dashboard_"+todaydate+"."+fileType;
			String outputPath = AppConstants.DASHBOARD_PATH+filename;
			Process pdf = Runtime.getRuntime().exec("phantomjs "+requestpath+"WebContent/WEB-INF/files/screenshot.js"+" "+url+" "+outputPath);
			BufferedReader stdInput = new BufferedReader(new
					InputStreamReader(pdf.getInputStream()));

			BufferedReader stdError = new BufferedReader(new
					InputStreamReader(pdf.getErrorStream()));

			// read the output from the command;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
			// Send mail
			emailUtility.mailDashBoardReport(email.split(","), dashboardName, filename);
		}
		catch (IOException | MessagingException e) {
			System.out.println("exception happened - here's what I know: ");
			e.printStackTrace();
			//System.exit(-1);
		}
	}

}
