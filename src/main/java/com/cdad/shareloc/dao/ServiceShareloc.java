package com.cdad.shareloc.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */


@Entity
@Table(name = "service")
public class ServiceShareloc {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "cost")
	private int cost;
	
	@Column(name = "status")
	private String statusService;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "colocation_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Colocation colocation;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      },
		      mappedBy = "services")
		  @JsonIgnore
		  private Set<User> users = new HashSet<>();
	
	
	public ServiceShareloc()
	{
		
	}

	//GETTERS
	public Long getId() {
		return id;
	}


	public String getTitle() {
		return title;
	}


	public String getDescription() {
		return description;
	}

	public int getCost() {
		return cost;
	}
	
	public String getStatusService() {
		return statusService;
	}

	public Colocation getColocation()
	{
		return colocation;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public Set<User> getUsers() {
		return users;
	}

	
	//SETTERS
	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public void setStatusService(String statusService) {
		this.statusService = statusService;
	}
	
	
	public void setColocation(Colocation colocation)
	{
		this.colocation = colocation;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
}
