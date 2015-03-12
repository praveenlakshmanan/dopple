package com.dopple.model;

public class User {
	private String username;
	private String firstName;
	private String role;
	private String site;
	private String privilege;
	private String defaultDashboard;
	private String loccode;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getDefaultDashboard() {
		return defaultDashboard;
	}
	public void setDefaultDashboard(String defaultDashboard) {
		this.defaultDashboard = defaultDashboard;
	}
	public String getLoccode() {
		return loccode;
	}
	public void setLoccode(String loccode) {
		this.loccode = loccode;
	}
	
}
