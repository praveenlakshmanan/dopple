package com.dopple.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.dopple.action.AppConstants;

/**
 * A utility class for sending e-mail
 * 
 *
 */
public class EmailUtility {

	//constant values for host, port username and password. change host name, password etc here

	//configuration for sending from gmail


	//configuration for sending mail from godady webmail
	public static final String HOST = "smtpout.secureserver.net";
	public static final String PORT = "25";
	public static final String USERNAME = "test@guidanz.com";
	public static final String PASSWORD = "guidanz123";
	public static  Logger log = Logger.getLogger(EmailUtility.class);
	

	/**
	 * Method to send mail. takes recipient subject and message as params
	 * 
	 * @param recipient
	 * @param subject
	 * @param message
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public  String mailDashBoardReport( String[] recipients,  String dashboardName , String filename) throws AddressException, MessagingException
	{   
		String result = "Mail successfully sent";
		try {
		
		//read this from Json
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host",HOST);
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", USERNAME);
		properties.put("mail.password", PASSWORD);
	
		System.out.println("dashboard name==>"+dashboardName);
		System.out.println("file attachment==> "+filename);
		
		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		};

		log.debug("get authenticater");
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		log.debug("creating mime message");
		Message message = new MimeMessage(session);


		
			log.debug("setting details");
			message.setFrom(new InternetAddress(USERNAME));
			//add recipeints as arrays
			
		//	ArrayList<InternetAddress> toAddresses = new ArrayList<InternetAddress>() ;
			InternetAddress[] toAddresses = new InternetAddress[recipients.length];
			
			for (int i = 0; i < recipients.length; i++) {
				System.out.println("email ids"+recipients[i]);
				toAddresses[i] = new InternetAddress(recipients[i]); 
			}
			
				
			String emailMessage = getMessage(dashboardName);
			message.setRecipients(Message.RecipientType.TO,  toAddresses);
			message.setSubject("Report for Dashboard "+dashboardName);
			message.setSentDate(new Date());
			//Create multipart
			Multipart multipart = new MimeMultipart();
			
			// create the messagepart for message body part  1
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			// fill message
			messageBodyPart.setContent(emailMessage,"text/html");

		
			// create messagepart for attachment Part 2
			MimeBodyPart attachPart = new MimeBodyPart();
			DataSource source = new FileDataSource(AppConstants.DASHBOARD_PATH+filename);
			attachPart.setDataHandler(new DataHandler(source));
			attachPart.setFileName(filename);
			
			//add message part to multipart
			multipart.addBodyPart(messageBodyPart);
			//add attachment part to multipart
			multipart.addBodyPart(attachPart);
			// Put multiparts in message
			message.setContent(multipart);
			// sends the e-mail
			log.debug("sending mail");
			Transport.send(message);
			
			log.debug("successfully sent");

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			result = "Mail sent failed";
			System.out.println("Address exception");
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			result = "Mail sent failed";
			System.out.println("Message exception");
			e.printStackTrace();
	
		}catch (Exception e) {
			result = "Mail sent failed";
			// TODO: handle exception
		}
		return result;
	}
	
	/**
	 * TO compose the message
	 * @param dashboardName
	 * @return
	 */
	private  String getMessage(String dashboardName){
		//get this message from templates later
		StringBuffer emailMessage = new StringBuffer("Hi,");
		emailMessage.append("<br/><br/>");
		emailMessage.append("Please find the reports for the dashboard "+dashboardName);
		emailMessage.append("<br/><br/>");
		return emailMessage.toString();
	}
	

}