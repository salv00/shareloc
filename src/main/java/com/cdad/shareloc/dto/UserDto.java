package com.cdad.shareloc.dto;

public class UserDto {
	
	private Long id;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private Long cost;
	
	
	public UserDto(Long id, String email, String password, String firstname, String lastname, Long cost) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.cost = cost;
	}

	
	
	public UserDto() {
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Long getCost() {
		return cost;
	}



	public void setCost(Long cost) {
		this.cost = cost;
	}



	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
