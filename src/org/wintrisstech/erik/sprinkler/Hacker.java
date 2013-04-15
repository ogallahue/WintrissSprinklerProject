package org.wintrisstech.erik.sprinkler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;

public class Hacker {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
		 String link = PasswordResetServlet.getPasswordResetLink(12001);
		System.out.println(link);
	}

}
