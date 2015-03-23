package com.doppleml.servicelayer;

import java.io.IOException;

import javax.servlet.ServletContext;

import com.dopple.DAO.UserDAO;
import com.dopple.model.User;

public class UserService {
	UserDAO userDAO;
	
	public UserService(ServletContext context){
		userDAO = new UserDAO(context);
	}

	public User authenticate(String userName, String password){

		User user = userDAO.getUser(userName, password);
		
		return user;
	}
	public User authenticate(String userName){

		User user = userDAO.getUser(userName);
		
		return user;
	}
	/**
	 * This method is to set the default dashboard 
	 * @param userobj
	 * @throws IOException
	 */
	public void setDefaultDashboard(User userobj) throws IOException{
		
		userDAO.updateUser(userobj);
		
	}
	
}
