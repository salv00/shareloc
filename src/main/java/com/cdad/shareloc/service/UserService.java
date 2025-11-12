package com.cdad.shareloc.service;

import java.util.List;

import com.cdad.shareloc.dao.User;


/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */


public interface UserService {

	
	//Save User
	User saveUser(User user);
	
	//Read List User
	List<User> findUserList();
	
	//Find user By Id
	User findUserById(User user, long userId);
	
	//Find current user connected
	User findCurrentUserConnected();
	
	
	//Update user informations
	User updateUser(User user, Long userId);
	
	//Delete User
	void deleteUserById(Long userId);
	
	
}
 