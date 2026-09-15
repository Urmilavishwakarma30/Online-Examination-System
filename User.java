package com.onlineexam;

public class User {

    private String username;
    private String password;
    private String displayName;
    
    public User() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
	public User(String username, String password, String displayName) {
		super();
		this.username = username;
		this.password = password;
		this.displayName = displayName;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", displayName=" + displayName + "]";
	}
	
    
    
}
