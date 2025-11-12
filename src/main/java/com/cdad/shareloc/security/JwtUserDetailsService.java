package com.cdad.shareloc.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.dto.UserDto;
import com.cdad.shareloc.repository.UserRepository;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Service
public class JwtUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		
		User existingUser = userRepository.findByEmail(email);
//				.orElseThrow(() -> 
//		new UsernameNotFoundException("User not found : " + email));
		
		
		return new org.springframework.security.core.userdetails.User(
				existingUser.getEmail(), existingUser.getPassword(), new ArrayList<>());
	}

	public User save(UserDto userDto)
	{
		User user = new User();
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		return userRepository.save(user);
		
	}

	
	
}
