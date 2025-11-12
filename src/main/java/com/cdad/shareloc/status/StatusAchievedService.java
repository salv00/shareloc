package com.cdad.shareloc.status;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

public enum StatusAchievedService {

	INCONNU, WAITING_FOR_VALIDATION, YES, NOT;
	
	public static StatusAchievedService find(String status)
	{
		if(status != null)
		{
			return StatusAchievedService.valueOf(status);
		}
		
		return INCONNU;
	}
	
	
	
}
