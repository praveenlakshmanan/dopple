package com.dopple.DAO;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.dopple.model.User;

public class UserDAO {
	//ServletContext application;
	JSONObject usersdb ;	
	String file;
	public UserDAO(ServletContext application){
		
		 JSONParser parser = new JSONParser();
		 
	        try {
	        	file = application.getRealPath("src/user.json");
	 
	            Object obj = parser.parse(new FileReader(file));
	            
	            usersdb = (JSONObject) obj;
	          
	            
		
	}catch (Exception e) {
        e.printStackTrace();
    }
	}
	/**
	 * This method is to update the user detail
	 * @param userobj
	 * @throws IOException
	 */
	public void updateUser(User userobj) throws IOException{
		
		JSONArray users = (JSONArray) usersdb.get("users"); 
        
        
        for (int i = 0; i <  users.size(); i++) {
           JSONObject user = (JSONObject) users.get(i);
          
           if(user.containsKey(userobj.getUsername())){
          	 
        	   JSONObject userjson = (JSONObject) user.get(userobj.getUsername());
        	   userjson.put("default_dashboard", userobj.getDefaultDashboard());
        	   user.put(userobj.getUsername(), userjson);
        	  
        	break;   
           }
        }
        usersdb.put("users", users);
        
        File folder=new File(file);
        folder.delete();
        //File temp=new File("../playlist/temp.txt");
        File fnew=new File(file);
        FileWriter outputfile = null;
        try {
        	
        	outputfile = new FileWriter(fnew);
            outputfile.write(usersdb.toJSONString());
            
 
        } catch (IOException e) {
            e.printStackTrace();
 
        }catch (Exception e){
        	e.printStackTrace();
        }finally {
      
            outputfile.flush();
            outputfile.close();
        }
	}
	
	
	public User getUser(String userName, String password){
		User userobj = null;
		JSONArray users = (JSONArray) usersdb.get("users"); 
          for (int i = 0; i <  users.size(); i++) {
             JSONObject user = (JSONObject) users.get(i);
             System.out.println("user "+user.toString());
             if(user.containsKey(userName)){
            	System.out.println("user "+user.containsKey(userName)); 
          	   JSONObject userjson = (JSONObject) user.get(userName);
          	   if(userjson!=null){
          	   if(userjson.get("password").equals(password)){
          		  userobj = new User();
    
          		  userobj.setFirstName(userjson.get("name").toString());
          		  userobj.setUsername(userjson.get("username").toString());
          		  userobj.setDefaultDashboard(userjson.get("default_dashboard").toString());
          		  userobj.setRole(userjson.get("role").toString());
          		  userobj.setPrivilege(userjson.get("privilege").toString());
          		  userobj.setLoccode(userjson.get("loccode").toString());
          		  System.out.println("user "+userobj.getFirstName());
          		  break;
          	   }
             }else{
            	 System.out.println("user is null in json");
             }
             }
          }
		return userobj;
		
	}
	public User getUser(String userName){
		User userobj = null;
		JSONArray users = (JSONArray) usersdb.get("users"); 
          for (int i = 0; i <  users.size(); i++) {
             JSONObject user = (JSONObject) users.get(i);
             System.out.println("user "+user.toString());
             if(user.containsKey(userName)){
            	System.out.println("user "+user.containsKey(userName)); 
          	   JSONObject userjson = (JSONObject) user.get(userName);
          	   if(userjson!=null){
          	   if(userjson.get("username").equals(userName)){
          		  userobj = new User();
    
          		  userobj.setFirstName(userjson.get("name").toString());
          		  userobj.setUsername(userjson.get("username").toString());
          		  userobj.setDefaultDashboard(userjson.get("default_dashboard").toString());
          		  userobj.setRole(userjson.get("role").toString());
          		  userobj.setPrivilege(userjson.get("privilege").toString());
          		  userobj.setLoccode(userjson.get("loccode").toString());
          		  System.out.println("user "+userobj.getFirstName());
          		  break;
          	   }
             }else{
            	 System.out.println("user is null in json");
             }
             }
          }
		return userobj;
		
	}
	

}
