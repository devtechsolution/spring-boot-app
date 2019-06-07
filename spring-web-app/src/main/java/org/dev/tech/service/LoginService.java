package org.dev.tech.service;

import org.springframework.stereotype.Component;

@Component
public class LoginService {
	
	public boolean validateUser(String user, String password) {
        return user.equalsIgnoreCase("aditya") && password.equals("srivastva");
    }

}
