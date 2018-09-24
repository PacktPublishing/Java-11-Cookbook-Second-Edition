package com.packt;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class UsernamePasswordAuthenticator extends Authenticator{

    private String username;
    private String password;

    public UsernamePasswordAuthenticator(){}

    public UsernamePasswordAuthenticator ( String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(username, password.toCharArray());
    }
}