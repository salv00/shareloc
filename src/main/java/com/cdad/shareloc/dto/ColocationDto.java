package com.cdad.shareloc.dto;

public class ColocationDto {
	
	private Long colocationId;
	private String nameColocation;
	private Long userId;
	private String firstName;
	private String lastName;
	
	
	public ColocationDto(Long colocationId, String nameColocation, Long userId, String firstName, String lastName) {
		this.colocationId = colocationId;
		this.nameColocation = nameColocation;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public ColocationDto()
	{}

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
	
	
	
	
	

}
