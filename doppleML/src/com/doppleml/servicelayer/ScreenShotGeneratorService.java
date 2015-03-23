package com.doppleml.servicelayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dopple.action.AppConstants;

public class ScreenShotGeneratorService {
	public String screenShot(String url,String fileType,String path){
		//JobKey key = context.getJobDetail().getKey();
		//JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		//String url= dataMap.getString("url");
		//String fileType = dataMap.getString("outputFileType");
		//String requestpath = dataMap.getString("requestpath");
		String s = null;
		String todaydate;

		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
		Date date = new Date();	

		todaydate = dateFormat.format(date);
		String filename = "Dashboard_"+todaydate+"."+fileType;

		try {//System.out.println("phantomjs "+path+"WebContent/WEB-INF/files/screenshot.js"+" "+url+" "+AppConstants.DASHBOARD_PATH+filename);
			//TO execute unix command
			//Process pdf = Runtime.getRuntime().exec("phantomjs "+path+"WebContent/WEB-INF/files/screenshot.js"+" "+url+" "+path+"Dashboardreports/Dashboard_"+todaydate+"."+fileType);
			Process pdf = Runtime.getRuntime().exec("sudo phantomjs "+path+"WebContent/WEB-INF/files/screenshot.js"+" "+url+" "+AppConstants.DASHBOARD_PATH+filename);
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

			//System.exit(0);
		}
		catch (IOException e) {
			System.out.println("exception happened - here's what I know: ");
			e.printStackTrace();
			//System.exit(-1);
		}
		return filename;
	}
}
