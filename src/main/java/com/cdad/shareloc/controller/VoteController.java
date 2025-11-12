package com.cdad.shareloc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdad.shareloc.dao.Vote;
import com.cdad.shareloc.repository.ColocationRepository;
import com.cdad.shareloc.repository.ServiceRepository;
import com.cdad.shareloc.repository.UserRepository;
import com.cdad.shareloc.repository.VoteRepository;
import com.cdad.shareloc.service.VoteService;


/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */
@CrossOrigin("*")
@RestController
public class VoteController {
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ColocationRepository colocationRepository;
	
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	VoteRepository voteRepository;
	
	@Autowired
	VoteService voteService;
	
	
	/*
	 * Permet de voter pour l'ajout de services
	 * WORK
	 */
	@PostMapping("colocation/{colocationId}/me/vote/add-service/{serviceId}")
	public Vote voteForService(@RequestBody Vote voteRequest, @PathVariable("colocationId") Long colocationId,
			@PathVariable("serviceId") Long serviceId)
	{
		return voteService.votingForAddService(voteRequest, colocationId, serviceId);
	}
	
}
