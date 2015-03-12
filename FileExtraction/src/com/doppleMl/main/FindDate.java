package com.doppleMl.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This method read input file and find the log start date 
 * Concat date to outputfile path
 * if date not found set current date
 * then return string
 */
public class FindDate {

	String outFilePath = null;

	public String file(File outFile){
		//Declare date 
		String date = null;
		Pattern patt = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");
		BufferedReader r;
		try {
			r = new BufferedReader(new FileReader(outFile));
			String line;
			while ((line = r.readLine()) != null) {
				Matcher m = patt.matcher(line);
				// Date string found in file then break
				if(date != null){
					break; 
				}
				while (m.find()) {
					//Copy date value to date variable 
					date = m.group(0); 
				}
			}

			String fileName = outFile.getName();
			String extension = null;
			// Splitting file name and extension

			int pos = fileName.lastIndexOf(".");
			int exten = fileName.lastIndexOf('.');
			if (exten > 0) {
				extension = fileName.substring(exten+1);
			}

			if (pos > 0) {
				fileName = fileName.substring(0, pos);
			}	

			//concatenate date string to the file name
			if(date != null){
				//Replace 19/4/2014 to 19-4-2014 replace string
				date = date.replace("/","-");
				outFilePath = FileWatch.DESTINATION_PATH + System.getProperty("file.separator") + fileName+":"+date+"."+extension;	
			}else{
				//  SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd"); 	      
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
				Date currentDate = new Date();
				outFilePath = FileWatch.DESTINATION_PATH + System.getProperty("file.separator") + fileName+":"+dateFormat.format(currentDate)+"."+extension;	
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outFilePath;
	}
}
