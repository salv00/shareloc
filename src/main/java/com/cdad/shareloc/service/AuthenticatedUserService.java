package com.cdad.shareloc.service;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.repository.UserRepository;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Service
public class AuthenticatedUserService {
	
	private UserRepository userRepository;
	
	
	public long hasId(UUID ID){          
	      String email =  SecurityContextHolder.getContext().getAuthentication().getName();
	      User user = userRepository.findByEmail(email);

	      return user.getId();

	   }

}
