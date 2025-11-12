package com.cdad.shareloc.dao;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "vote")
public class Vote {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "vote")
	private String vote;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "service_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private ServiceShareloc service;
	
	
	//GETTERS
	public Long getId() {
		return id;
	}

	public String getVote() {
		return vote;
	}
	
	public User getUser() {
		return user;
	}
	
	public ServiceShareloc getService() {
		return service;
	}
	
	
	//SETTERS
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setVote(String vote) {
		this.vote = vote;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setService(ServiceShareloc service) {
		this.service = service;
	}
	
}
