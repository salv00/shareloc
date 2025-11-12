package com.cdad.shareloc.status;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

public enum StatusService {
	
	INCONNU, WAITING_FOR_VALIDATION, VOTE_IN_PROGRESS_FOR_ADDING, VALIDATED, REJECTED, DELETED, REPROPOSE, ACHIEVED;
	
	public static StatusService find(String status)
	{
		if(status != null)
		{
			return StatusService.valueOf(status);
		}
		
		return INCONNU;
	}

}
