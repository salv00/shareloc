package com.cdad.shareloc.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Entity
@Table(name = "chatcolocation")
public class ChatColocation {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "sendername")
	private String senderName;
	
	@Column(name = "receivername")
	private String receiverName;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "status")
	private String status;

	
	public ChatColocation(long id, String senderName, String receiverName, String message, String date, String status) {
		super();
		this.id = id;
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.message = message;
		this.date = date;
		this.status = status;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public String getReceiverName() {
		return receiverName;
	}


	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
}
