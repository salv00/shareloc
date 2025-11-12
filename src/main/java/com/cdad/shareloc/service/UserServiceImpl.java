package com.cdad.shareloc.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.exception.ResourceNotFoundException;
import com.cdad.shareloc.repository.UserRepository;


/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//Save User
	@Override
	public User saveUser(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	//Find List de tous les users
	@Override
	public List<User> findUserList() {

		return (List<User>) userRepository.findAll();
	}

	//Find user by Id
	@Override
	public User findUserById(User user, long userId) {
		
		User userBDD = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found user with id : " + userId));
		
		return userBDD;
	}
	
	
	//Update(Edit) User
	@Override
	public User updateUser(User user, Long userId) {

		User userBDD = userRepository.findById(userId).get();
		
		if(Objects.nonNull(user.getFirstname()) && !"".equalsIgnoreCase(user.getFirstname()))
		{
			userBDD.setFirstname(user.getFirstname());
		}
		
		if(Objects.nonNull(user.getLastname()) && !"".equalsIgnoreCase(user.getLastname()))
		{
			userBDD.setLastname(user.getLastname());
		}
		
		if(Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail()))
		{
			userBDD.setEmail(user.getEmail());
		}
		
		if(Objects.nonNull(user.getPassword()) && !"".equalsIgnoreCase(user.getPassword()))
		{
			userBDD.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		return userRepository.save(userBDD);
	}

	//Delete User
	@Override
	public void deleteUserById(Long userId) {
		userRepository.deleteById(userId);
		
	}

	//Find current user connected
	@Override
	public User findCurrentUserConnected() {
		
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		User user = userRepository.findByEmail(currentUser);
		
		
		return user;
		
//		Optional<User> oUser = userRepository.findByEmail(currentUser);
//		
//		return "Welcome" 
//			   + " id : " + oUser.get().getId() 
//			   + " first name : " + oUser.get().getFirstname() 
//			   + " last name : " + oUser.get().getLastname()
//			   + " email : " + oUser.get().getEmail();
	}



}
