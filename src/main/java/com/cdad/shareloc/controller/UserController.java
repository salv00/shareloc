package com.cdad.shareloc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.repository.UserRepository;
import com.cdad.shareloc.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */
@CrossOrigin("*")
@RestController
@Api(value = "API de gestion des utilisateurs")
public class UserController {

	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	
	//Create new user
	@PostMapping("/new-user")
	public User saveUser(@Valid @RequestBody User user)
	{
		return userService.saveUser(user);
	}
	
	
	/*
	 * Permet de recuperer la liste de tous les users
	 * WORK
	 */
    @ApiOperation(value = "Récupère la liste des utilisateurs")
	@GetMapping("/list-user")
	public List<User> findUserList()
	{
		return userService.findUserList();
	}
	
	
	/*
	 * Permet de chercher un user
	 * WORK
	 */
	@GetMapping("/find-user/user/{id}")
	public User findUserById(@RequestBody User user, @PathVariable("id") long userId)
	{
		return userService.findUserById(user, userId);
	}
	
	
	/*
	 * Permet à l'user de changer ses informations
	 * WORK
	 */
	@PutMapping("/user/{id}/update-profile")
	public User updateUser(@RequestBody User user, @PathVariable("id") Long userId)
	{
		return userService.updateUser(user, userId);
	}
	
	/*
	 * Permet à l'user de supprimer sont compte
	 */
	@DeleteMapping("/user/{id}/delete-account")
	public String deleteUserById(@PathVariable("id") Long userId)
	{
		userService.deleteUserById(userId);
		
		return "User deleted Successfully";
	}
	
	
	/*
	 * Recupere l'user actuellemnt connecté
	 * WORK
	 */
	//@GetMapping("/user/me")
	@RequestMapping(value="/user/me", method={RequestMethod.OPTIONS,RequestMethod.GET})
	public User getCurrentUser()
	{
		return userService.findCurrentUserConnected();		
	}
	
}
