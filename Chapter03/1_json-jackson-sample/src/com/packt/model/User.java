package com.packt.model;

public class User{
	public Integer id;
	public String name;
	public String username;
	public String email;
	public Address address;
	public String phone;
	public String website;
	public Company company;

	@Override
	public String toString(){
		return "Name: " + this.name + ", Company: " + this.company.name;
	}
}