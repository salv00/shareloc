package com.cdad.shareloc.status;

public enum ChoixVote {

	
	/*
	 * Author : salvatore COVALEA
	 * Project name : ShareLoc 
	 * Version : Pre-release
	 * Date : 18/01/2022
	 */
	
	INCONNU, POUR, CONTRE;
	
	public static ChoixVote find(String choixVote)
	{
		if(choixVote != null)
		{
			return ChoixVote.valueOf(choixVote);
		}
		
		return INCONNU;
	}
	
}
