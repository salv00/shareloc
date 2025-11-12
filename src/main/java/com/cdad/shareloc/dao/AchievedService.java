package com.cdad.shareloc.dao;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;


/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Entity
@Table(name = "achievedservice")
public class AchievedService {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@Type(type="org.hibernate.type.BinaryType")	
	@Column(name = "picture", unique = false, nullable = false, length = 100000)
	private byte[] picture;
	
	@Column(name = "valid")
	private String valid;	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "fromm_userId", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "to_colocationId", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Colocation colocation;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "service_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private ServiceShareloc serviceShareloc;
	

	public AchievedService()
	{}

	
	//GETTERS
	public long getId() {
		return id;
	}


	public User getUser() {
		return user;
	}


	public Colocation getColocation() {
		return colocation;
	}
	

	public ServiceShareloc getServiceShareloc() {
		return serviceShareloc;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public byte[] getPicture() {
		return picture;
	}

	public String getValid() {
		return valid;
	}


	//SETTERS
	public void setId(long id) {
		this.id = id;
	}

	
	public void setUser(User user) {
		this.user = user;
	}


	public void setColocation(Colocation colocation) {
		this.colocation = colocation;
	}

	public void setServiceShareloc(ServiceShareloc serviceShareloc) {
		this.serviceShareloc = serviceShareloc;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public void setPicture(byte[] picture) {
		this.picture = picture;
	}


	public void setValid(String valid) {
		this.valid = valid;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
