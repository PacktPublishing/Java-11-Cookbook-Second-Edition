package com.packt.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class User{
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private List<UserPrivilege> privileges = new ArrayList<>();

	public User(String username, String email, String firstName, String lastName){
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(){}

	public String getUsername(){ return username; }
	public void setUsername(String username) { this.username = username; }

	public String getEmail(){ return email; }
	public void setEmail(){ this.email = email; }

	public String getFirstName(){ return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }
	public void setLastName(){ this.lastName = lastName; }

	public List<UserPrivilege> getPrivileges(){ 
		return Collections.unmodifiableList(privileges); 
	}

	public void addPrivilege(UserPrivilege privilege){
		privileges.add(privilege);
	}

}