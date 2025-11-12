package com.cdad.shareloc.service;

import com.cdad.shareloc.dao.Vote;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

public interface VoteService {

	//Permet de voter pour ajouter un service
	Vote votingForAddService(Vote vote, Long colocationId, Long serviceId);
	
	
}
