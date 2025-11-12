package com.cdad.shareloc.service;

import java.util.List;

import com.cdad.shareloc.dao.Colocation;
import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.dto.ColocationDto;
import com.cdad.shareloc.dto.UserDto;


/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */


public interface ColocationService {
	
	
	//Cree colocation
	Colocation createColocationByUser(Colocation colocation);
	
	//Rejoindre une colocation avec l'id user
	Colocation joinColocationByNameColocation(Colocation colocation);
	
	//Permet à l'user de quitter une colocation
	Colocation exitFromColocationByIdColocAndIdUser(Colocation colocation);
	
	//Recupere la liste de tous les colocations
	List<ColocationDto> findColocationList();
	
	//Recupere une colocation à partir de son Id
	Colocation findColocationById(Colocation colocation, long colocationId);
	
	//Recupere tous les users appartenant a une coloc à partir de l'id de la coloc
	List<UserDto> findUsersByColocationsId(long colocationId);
	
	//Recupere tous les colocations qui sont rattachés à un utilisateur avec l'id user
	List<ColocationDto> findColocationsByUserId();
	
	
	/*
	 * CODE RESERVE AU GESTIONAIRE DE LA COLOCATION
	 */
	//Update colocation
	Colocation updateColocation(Colocation colocation, Long colocationId);
	
	//Delete colocation
	void deleteColocationById(Long colocationId);

}
