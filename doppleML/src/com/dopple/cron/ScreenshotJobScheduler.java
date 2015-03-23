package com.dopple.cron;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
/**
 * This method for creating a cron job
 * @author hduser
 *
 */
public class ScreenshotJobScheduler {

	/**
	 * This method will create a cron job for creating a screen shot 
	 * in periodic time interval
	 * @param url
	 * @param outputFileType
	 */
	public void JobSchedule(String url , String outputFileType, String requestpath, String dashboardName, String email, String interval, String startTime){
		//System.out.println("in job schedule");

		JobKey jobKey = new JobKey(dashboardName, "jobgroup");
		JobDetail job = JobBuilder.newJob(ScreenshotJob.class).withIdentity(jobKey)
				.usingJobData("url", url)
				.usingJobData("outputFileType", outputFileType)
				.usingJobData("requestpath", requestpath)
				.usingJobData("dashboardName", dashboardName)
				.usingJobData("email", email)
				.build();

		CronTrigger trigger = null;		
		Date startDate = null;
		try {
			//format 2014-12-04 00:00
			startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		if(interval.equals("Test")){
			System.out.println("trigger each two mins");
			trigger = newTrigger()
					.withIdentity("trigger_"+dashboardName, "group")
					.startAt(startDate)
					.withSchedule(cronSchedule("0 0/2 * * * ?"))
					.build();

		}else if (interval.equals("30m")){
			trigger = newTrigger()
					.withIdentity("trigger_"+dashboardName, "group")
					.withSchedule(cronSchedule("0 0/30 * * * ?"))
					.build();

		}else if(interval.equals("1h")){
			trigger = newTrigger()
					.withIdentity("trigger_"+dashboardName, "group")
					.withSchedule(cronSchedule("0 0 * * * ?"))
					.build();

		}else if(interval.equals("1d")){
			trigger = newTrigger()
					.withIdentity("trigger_"+dashboardName, "group")
					.withSchedule(cronSchedule("0 0 0 * * ?"))
					.build();

		}

		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();		 
			scheduler.start();
			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * To kill particular job in quartz scheduler
	 * @param jobName
	 * @param groupName
	 */
	public void stopService(String jobName,String groupName){

		Scheduler scheduler;
		try {
			System.out.println("stop service calling");
			scheduler = new StdSchedulerFactory().getScheduler();
			JobKey jobKey = new JobKey(jobName, groupName);
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName,groupName);

			// Schedule the job with the trigger
			if(scheduler.checkExists(triggerKey)){
				scheduler.unscheduleJob(triggerKey);
				System.out.println("job killed");
			}else{
				System.out.println("job not exists");
			}

			if(scheduler.checkExists(jobKey)){
				//scheduler.deleteJob(jobKey);
			}

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	 * To check service is exists or not
	 * @param dashboardName
	 * @return
	 */
	public String serviceIsExists(String dashboardName){
		String result = "false";
		try {
			JobKey jobKey = new JobKey(dashboardName, "jobgroup");
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			if(scheduler.checkExists(jobKey)){
				result = "true";
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
	}

}
