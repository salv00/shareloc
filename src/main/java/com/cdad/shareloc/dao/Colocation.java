package com.cdad.shareloc.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Entity(name = "Colocation")
@Table(name  = "colocation")
public class Colocation {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	

	@Column(name = "name")
	private String name;
	

	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      },
		      mappedBy = "colocations")
	private Set<User> users = new HashSet<>();
	
	
	@OneToMany(mappedBy = "colocation", cascade = CascadeType.ALL)
	private List<ServiceShareloc> services;
	
	
//	@ManyToMany(mappedBy = "colocations",fetch = FetchType.LAZY)
//	private List<User> listUsers;
	
	
	public Colocation() 
	{}
	
	
	//GETTERS
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	
	
	//SETTERS
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}


	public void setUsers(Set<User> users) {
		this.users = users;
	}


//	public List<User> getListUsers() {
//		return listUsers;
//	}
//
//
//	public void setListUsers(List<User> listUsers) {
//		this.listUsers = listUsers;
//	}
	
}
