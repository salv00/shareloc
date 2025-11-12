package com.cdad.shareloc.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class CustomUserDetailsService implements UserDetailsService{

	
	@Autowired
	UserRepository userRepository;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		
		User existingUser = userRepository.findByEmail(email);

		return new org.springframework.security.core.userdetails.User(
				existingUser.getEmail(), existingUser.getPassword(), new ArrayList<>());
	}

	
}
