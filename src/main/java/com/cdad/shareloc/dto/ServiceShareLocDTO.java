package com.cdad.shareloc.dto;

public class ServiceShareLocDTO {

	private Long id;
	private String title;
	private String description;
	private int cost;
	private String statusService;
	private Long colocationId;
	private String nameColocation;
	
	
	
	public ServiceShareLocDTO(Long id, String title, String description, int cost, String statusService ,Long colocationId, String nameColocation) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.cost = cost;
		this.colocationId = colocationId;
		this.nameColocation = nameColocation;
		this.statusService = statusService;
	}
	
	public ServiceShareLocDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
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

	public String getStatusService() {
		return statusService;
	}

	public void setStatusService(String statusService) {
		this.statusService = statusService;
	}	
	
	
	
	

}
