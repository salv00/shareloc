package com.cdad.shareloc.dto;

import java.time.LocalDateTime;

public class AchievedServiceDto {

	private long id;
	private String date;
	private byte[] picture;
	private String valid;	
	private Long serviceId;
	private String nameService;
	private int costService;
	private Long userId;
	private String email;
	private String firstName;
	private String lastName;
	private Long colocationId;
	private String nameColocation;

	
	public AchievedServiceDto(long id, String date, byte[] picture, String valid, Long serviceId, String nameService, int costService,Long userId,
			String email,String firstName, String lastName, Long colocationId, String nameColocation ) {
		this.id = id;
		this.date = date;
		this.picture = picture;
		this.valid = valid;
		this.serviceId = serviceId;
		this.nameService = nameService;
		this.costService = costService;
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.colocationId = colocationId;
		this.nameColocation = nameColocation;
		
		
	}
	

	public AchievedServiceDto() {

	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public byte[] getPicture() {
		return picture;
	}


	public void setPicture(byte[] picture) {
		this.picture = picture;
	}


	public String getValid() {
		return valid;
	}


	public void setValid(String valid) {
		this.valid = valid;
	}


	public Long getServiceId() {
		return serviceId;
	}


	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Long getColocationId() {
		return colocationId;
	}


	public void setColocationId(Long colocationId) {
		this.colocationId = colocationId;
	}


	public String getNameColocation() {
		return nameColocation;
	}


	public void setNameColocation(String nameColocation) {
		this.nameColocation = nameColocation;
	}


	public String getNameService() {
		return nameService;
	}


	public void setNameService(String nameService) {
		this.nameService = nameService;
	}


	public int getCostService() {
		return costService;
	}


	public void setCostService(int costService) {
		this.costService = costService;
	}



	
	
	
	
}
