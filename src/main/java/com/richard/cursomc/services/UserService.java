/**
 * 
 */
package com.richard.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.richard.cursomc.security.UserSS;

/**
 * Created by Richard Rossetto on 16 de set de 2018
 */
public class UserService {
	
	public static UserSS authenticated() {
		try {
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}

}
